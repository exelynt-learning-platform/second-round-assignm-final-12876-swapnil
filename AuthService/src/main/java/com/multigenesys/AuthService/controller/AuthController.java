package com.multigenesys.AuthService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multigenesys.AuthService.dto.ApiResponse;
import com.multigenesys.AuthService.dto.AuthResponse;
import com.multigenesys.AuthService.dto.LoginRequest;
import com.multigenesys.AuthService.dto.RegisterRequest;
import com.multigenesys.AuthService.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<Void>> register(@RequestBody RegisterRequest request) {

		try {
			authService.register(request);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ApiResponse<>("User registered successfully", "SUCCESS", null));

		} catch (RuntimeException ex) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse<>(ex.getMessage(), "ERROR", null));

		} catch (Exception ex) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>("Something went wrong", "ERROR", null));
		}
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request,
			HttpServletRequest httpRequest) {

		try {

			String ipAddress = httpRequest.getRemoteAddr();
			String userAgent = httpRequest.getHeader("User-Agent");

			AuthResponse token = authService.login(request, ipAddress, userAgent);

			return ResponseEntity.ok(new ApiResponse<>("Login successful", "SUCCESS", token));

		} catch (RuntimeException ex) {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ApiResponse<>(ex.getMessage(), "ERROR", null));

		} catch (Exception ex) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>("Something went wrong", "ERROR", null));
		}
	}

//	@PostMapping("/login")
//	public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
//
//	    try {
//	        AuthResponse token = authService.login(request);
//
//	        return ResponseEntity.ok(
//	                new ApiResponse<>("Login successful", "SUCCESS", token)
//	        );
//
//	    } catch (RuntimeException ex) {
//
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//	                .body(new ApiResponse<>(ex.getMessage(), "ERROR", null));
//
//	    } catch (Exception ex) {
//
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                .body(new ApiResponse<>("Something went wrong", "ERROR", null));
//	    }
//	}
}
