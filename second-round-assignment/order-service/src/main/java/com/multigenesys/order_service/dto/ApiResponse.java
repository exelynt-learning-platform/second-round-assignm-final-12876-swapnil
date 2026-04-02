package com.multigenesys.order_service.dto;

import java.time.LocalDateTime;

public class ApiResponse<T> {

	private String message;
	private String status;
	private int statusCode;
	private LocalDateTime timestamp;
	private T data;

	public ApiResponse() {
	}

	public ApiResponse(String message, String status, int statusCode, T data) {
		this.message = message;
		this.status = status;
		this.statusCode = statusCode;
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

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
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