# MultiGenesys E-Commerce Backend System

## Overview
This project is a microservices-based backend for an E-commerce platform developed using Spring Boot.

The system provides authentication, product management, cart management, order processing, and payment integration using PayPal.

---

## Tech Stack

- Java
- Spring Boot
- Spring Security
- JWT Authentication
- MySQL
- Spring Data JPA
- PayPal Payment Gateway
- Maven

---

## Microservices

### 1. Auth Service
Handles user authentication and JWT token generation.

Features:
- User Registration
- User Login
- JWT Token Generation
- Password Encryption (BCrypt)
- Login Activity Tracking

Runs on:

http://localhost:9095

---

### 2. Ecommerce Service
Handles core e-commerce operations.

Features:
- Product Management (CRUD)
- Cart Management
- Order Processing
- Payment Integration (PayPal)

Runs on:

http://localhost:9096

---

## API Testing

Postman collection is provided in the repository.


