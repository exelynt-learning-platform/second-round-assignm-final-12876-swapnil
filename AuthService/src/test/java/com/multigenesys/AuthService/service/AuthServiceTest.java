package com.multigenesys.AuthService.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.multigenesys.AuthService.dto.AuthResponse;
import com.multigenesys.AuthService.dto.LoginRequest;
import com.multigenesys.AuthService.entity.User;
import com.multigenesys.AuthService.repository.UserLoginHistoryRepository;
import com.multigenesys.AuthService.repository.UserRepository;
import com.multigenesys.AuthService.util.JwtUtil;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserLoginHistoryRepository loginHistoryRepository;

    @InjectMocks
    private AuthService authService;

    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setUp() {

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@gmail.com");
        loginRequest.setPassword("123456");

        user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setPassword("encodedPassword");
    }

    @Test
    void testLoginSuccess() {

        String ipAddress = "192.168.1.10";
        String userAgent = "Chrome Browser";

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("123456", "encodedPassword"))
                .thenReturn(true);

        when(jwtUtil.generateToken(user.getEmail(), user.getId()))
                .thenReturn("mock-jwt-token");

        AuthResponse response = authService.login(loginRequest, ipAddress, userAgent);

        assertNotNull(response);
        assertEquals("mock-jwt-token", response.getToken());

        verify(loginHistoryRepository, times(1)).save(any());
    }

    @Test
    void testLoginInvalidPassword() {

        String ipAddress = "192.168.1.10";
        String userAgent = "Chrome Browser";

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("123456", "encodedPassword"))
                .thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(loginRequest, ipAddress, userAgent);
        });

        assertEquals("Invalid credentials", exception.getMessage());

        verify(loginHistoryRepository, times(1)).save(any());
    }

    @Test
    void testUserNotFound() {

        String ipAddress = "192.168.1.10";
        String userAgent = "Chrome Browser";

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(loginRequest, ipAddress, userAgent);
        });

        assertEquals("User not found", exception.getMessage());
    }
}