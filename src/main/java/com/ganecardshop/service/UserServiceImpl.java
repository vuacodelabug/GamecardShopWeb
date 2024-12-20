package com.ganecardshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ganecardshop.model.Profile;
import com.ganecardshop.model.User;
import com.ganecardshop.repository.ProfileRepository;
import com.ganecardshop.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private HttpServletRequest request; // Tiêm HttpServletRequest qua constructor

    // Lấy thông tin người dùng theo email
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng email: " + email));
    }

    @Override
    public List<User> findUsersBySearchCriteria(String profileName, String userRole) {
        if (profileName != null && !profileName.isEmpty() && userRole != null && !userRole.isEmpty()) {
            return userRepository.findByProfileNameContainingAndRole(profileName, userRole);
        } else if (profileName != null && !profileName.isEmpty()) {
            return userRepository.findByProfileNameContaining(profileName);
        } else if (userRole != null && !userRole.isEmpty()) {
            return userRepository.findByRole(userRole);
        } else {
            return userRepository.findAll(); // Trả về tất cả người dùng nếu không có điều kiện
        }
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAllActiveUsers();
    }

    @Override
    public Optional<User> findById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    public User getUserWithProfile(int id) {
        return userRepository.findByIdWithProfile(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng id" + id));
    }

    @Override
    public void updateRole(int id, String role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người dùng id" + id));
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deactivateUser(id); // Soft delete
    }

    // chức năng đăng nhập
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        User user = optionalUser.get();
        // Lưu user_id vào session
        HttpSession session = request.getSession();
        session.setAttribute("user_id", user.getId());
        session.setAttribute("user_name", user.getProfile().getName());
        // Spring Security sẽ tự kiểm tra trạng thái qua isEnabled()
        if (!user.isEnabled()) {
            throw new DisabledException("Tài khoản đã bị vô hiệu hóa.");
        }

        // return org.springframework.security.core.userdetails.User
        // .withUsername(user.getUsername())
        // .password(user.getPassword())
        // .authorities(user.getRole()) // Gán role
        // .build();

        return user; // Now, user is already a UserDetails object
    }

    // đăng ký người dùng
    public void registerUserAccount(String user_email, String user_password, String profile_name, String profile_phone,
            String profile_gender, java.sql.Date profile_dateOfBirth) {
        String encodedPassword = passwordEncoder.encode(user_password);
        // kiểm tra xem email đã tồn tại chưa
        if (userRepository.findByEmail(user_email).isPresent()) {
            throw new RuntimeException("Email đã tồn tại.");
        }
        User newUser = new User(user_email, encodedPassword, true);
        userRepository.save(newUser);

        Profile newProfile = new Profile(newUser);
        newProfile.setName(profile_name);
        newProfile.setPhone(profile_phone);
        newProfile.setGender(profile_gender);
        newProfile.setDateOfBirth(profile_dateOfBirth);
        newUser.setProfile(newProfile);
        userRepository.save(newUser);
    }

    // Lấy thông tin người dùng theo id_user
    public Optional<Profile> findProfileByUserId(int userId) {
        return profileRepository.findByUserId(userId);
    }

    // Thêm người dùng và thông tin cá nhân admin
    public void addUserAndProfile(String user_email, String user_role, String profile_name, String profile_phone,
            String profile_gender, java.sql.Date profile_dateOfBirth) {
        User user;

        user = new User(user_email, user_role); // Mật khẩu mặc định
        user.setPassword(passwordEncoder.encode("123!@#"));
        // Tạo Profile mới và liên kết với User
        Profile profile = new Profile(user);
        profile.setName(profile_name);
        profile.setPhone(profile_phone);
        profile.setGender(profile_gender);
        profile.setDateOfBirth(profile_dateOfBirth);
        user.setProfile(profile);
        userRepository.save(user);
    }
}
