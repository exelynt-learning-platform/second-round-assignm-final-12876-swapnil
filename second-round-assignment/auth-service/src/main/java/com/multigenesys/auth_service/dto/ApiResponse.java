package com.multigenesys.auth_service.dto;

import java.time.LocalDateTime;

public class ApiResponse<T> {

	private String message;
	private String status;
	private LocalDateTime timestamp = LocalDateTime.now();
	private T data;

	public ApiResponse() {
		this.timestamp = LocalDateTime.now();
	}

	public ApiResponse(String message, String status, T data) {
		super();
		this.message = message;
		this.status = status;
		this.timestamp = LocalDateTime.now();
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}


	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	
}