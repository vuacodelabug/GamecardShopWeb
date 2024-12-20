package com.ganecardshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ganecardshop.model.User;
import com.ganecardshop.service.UserService;

@Controller
@RequestMapping("admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
public String postSearch(@RequestParam(required = false) String search_profilename,
                          @RequestParam(required = false) String search_userrole,
                          Model model) {

    // Đảm bảo giá trị mặc định nếu không có giá trị từ người dùng
    if (search_profilename == null || search_profilename.trim().isEmpty()) {
        search_profilename = "";
    }
    if (search_userrole == null || search_userrole.trim().isEmpty()) {
        search_userrole = "";
    }

    // Tìm kiếm theo tiêu chí
    List<User> users = userService.findUsersBySearchCriteria(search_profilename, search_userrole);

    // Truyền kết quả tìm kiếm và các tham số vào model
    model.addAttribute("users", users);
    model.addAttribute("search_profilename", search_profilename); // Giữ lại giá trị tìm kiếm cho form
    model.addAttribute("search_userrole", search_userrole); // Giữ lại giá trị role cho form

    return "admin/page/user-list";
}

    @PostMapping("/add")
    public String addUser(
            @RequestParam String user_email,
            @RequestParam String user_role,
            @RequestParam String profile_name,
            @RequestParam(required = false) String profile_phone,
            @RequestParam(required = false) String profile_gender,
            @RequestParam(required = false) String profile_dateOfBirth,
            RedirectAttributes redirectAttributes) {

        java.sql.Date dob = null;
        if (profile_dateOfBirth != null && !profile_dateOfBirth.isEmpty()) {
            dob = java.sql.Date.valueOf(profile_dateOfBirth);
        } else {
            dob = java.sql.Date.valueOf("1900-01-01");
        }

        // Gọi service để thêm User và Profile
        boolean isSuccess;
        try {
            userService.addUserAndProfile(user_email, user_role, profile_name, profile_phone, profile_gender, dob);
            isSuccess = true;
        } catch (Exception e) {
            isSuccess = false;
        }

        // Thêm thông báo vào RedirectAttributes
        if (isSuccess) {
            redirectAttributes.addFlashAttribute("messager", "User đã được thêm!");
            redirectAttributes.addFlashAttribute("isSuccess", true);
        } else {
            redirectAttributes.addFlashAttribute("messager", "Đã xảy ra lỗi! Vui lòng thử lại.");
            redirectAttributes.addFlashAttribute("isSuccess", false);
        }

        return "redirect:/admin/user";
    }

    // @RestController - API lấy chi tiết người dùng dưới dạng JSON
    @GetMapping("/api/detail/{id}")
    @ResponseBody
    public ResponseEntity<User> getUserWithProfile(@PathVariable int id) {
        User user = userService.getUserWithProfile(id);
        return ResponseEntity.ok(user);
    }

    // @RestController - API update role người dùng
    @PostMapping("/api/update-role/{id}")
    @ResponseBody
    public ResponseEntity<String> updateRole(@PathVariable int id, @RequestParam String role) {
        userService.updateRole(id, role);
        return ResponseEntity.ok("Cập nhật role thành công!");
    }

    // @RestController - API xóa người dùng
    @PostMapping("/api/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        System.out.println("Delete user with id: " + id);
        userService.deleteUser(id); // Chỉ đổi status sang 0
        return ResponseEntity.ok("Xóa người dùng thành công!");
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/user";
    }

}
