package com.multigenesys.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multigenesys.auth_service.entity.UserLoginHistory;

public interface UserLoginHistoryRepository 
       extends JpaRepository<UserLoginHistory, Long> {
}