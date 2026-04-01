package com.multigenesys.AuthService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.multigenesys.AuthService.entity.UserLoginHistory;

public interface UserLoginHistoryRepository 
       extends JpaRepository<UserLoginHistory, Long> {
}