package com.ganecardshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ganecardshop.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private UserServiceImpl userService;

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userService) // Sử dụng service để load user
				.passwordEncoder(passwordEncoder()) // Sử dụng mã hóa mật khẩu
				.and()
				.build();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/*").permitAll()
						.requestMatchers("/payment/**").authenticated()
						.requestMatchers("/admin/**").hasAuthority("admin")
						.anyRequest().permitAll() // Các yêu cầu khác không yêu cầu đăng nhập
				)
				.formLogin(login -> login
						.loginPage("/home?login=false")
						.loginProcessingUrl("/process-login")
						.usernameParameter("email")
						.passwordParameter("password")
						.successHandler((request, response, authentication) -> {
							if (authentication.getAuthorities().stream()
									.anyMatch(authority -> authority.getAuthority().equals("admin"))) {
								response.sendRedirect("/admin/dashboard");
							} else {
								response.sendRedirect("/home");
							}
						})
						.failureHandler((request, response, exception) -> {
							String errorMessage = exception.getMessage();
							System.out.println("Exception class: " + exception.getClass().getName());
							if (exception instanceof BadCredentialsException) {
								errorMessage = "Sai mật khẩu, vui lòng thử lại.";
							} else if (exception instanceof AccountStatusException) {
								errorMessage = "Tài khoản không hợp lệ.";
							}
							request.getSession().setAttribute("error", errorMessage);
							response.sendRedirect("/login?error=true");
						}))
				.logout(logout -> logout
						.logoutUrl("/logout") // URL logout
						.logoutSuccessUrl("/home?login=false") // Redirect đến home sau khi logout
				);

		return http.build();
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/assets/**");
	}
}
