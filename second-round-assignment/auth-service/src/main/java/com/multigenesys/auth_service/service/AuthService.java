package com.multigenesys.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.multigenesys.auth_service.dto.AuthResponse;
import com.multigenesys.auth_service.dto.LoginRequest;
import com.multigenesys.auth_service.dto.RegisterRequest;
import com.multigenesys.auth_service.entity.Role;
import com.multigenesys.auth_service.entity.User;
import com.multigenesys.auth_service.entity.UserLoginHistory;
import com.multigenesys.auth_service.repository.UserLoginHistoryRepository;
import com.multigenesys.auth_service.repository.UserRepository;
import com.multigenesys.auth_service.util.JwtUtil;

@Service
public class AuthService {

	@Autowired
    private  UserRepository userRepository;
	
	@Autowired
    private  PasswordEncoder passwordEncoder;
	
	@Autowired
    private  JwtUtil jwtUtil;
	
	@Autowired
	private UserLoginHistoryRepository loginHistoryRepository;


	public void register(RegisterRequest request) {

	    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
	        throw new RuntimeException("Email already exists");
	    }

	    String password = request.getPassword();

	    if (password == null ||
	        password.length() < 8 ||
	        !password.matches(".*[A-Z].*") ||
	        !password.matches(".*[0-9].*")) {

	        throw new RuntimeException("Password must be at least 8 characters long and include one uppercase letter and one number");
	    }

	    User user = new User();
	    user.setName(request.getName());
	    user.setEmail(request.getEmail());
	    user.setPassword(passwordEncoder.encode(password));
	    user.setRole(Role.USER);
	    user.setActive(true);
	    user.setEmailVerified(false);

	    userRepository.save(user);
	}
	

	public AuthResponse login(LoginRequest request, String ipAddress, String userAgent) {

	    User user = userRepository.findByEmail(request.getEmail())
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

	        UserLoginHistory history = new UserLoginHistory();
	        history.setUserId(user.getId());
	        history.setEmail(user.getEmail());
	        history.setIpAddress(ipAddress);
	        history.setDeviceType(userAgent);
	        history.setStatus("FAILED");

	        loginHistoryRepository.save(history);

	        throw new RuntimeException("Invalid credentials");
	    }

	    String token = jwtUtil.generateToken(user.getEmail(), user.getId());

	    UserLoginHistory history = new UserLoginHistory();
	    history.setUserId(user.getId());
	    history.setEmail(user.getEmail());
	    history.setIpAddress(ipAddress);
	    history.setDeviceType(userAgent);
	    history.setStatus("SUCCESS");

	    loginHistoryRepository.save(history);

	    return new AuthResponse(token);
	}
	
}
