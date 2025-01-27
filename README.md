
## Overview

The Calendar Log Backend is a Spring Boot-based application designed to handle all backend operations for the Calendar Log project. It serves as the API layer for handling user authentication, managing companies, and storing communication logs. The backend integrates with MongoDB for persistent storage and provides services for users and administrators to interact with the system. It uses token-based authentication and BCrypt for password encryption.

## Features

- **User Authentication**: Manages user registration, login, and token-based authentication. Passwords are encrypted using **BCrypt**.
- **Company Management**: Admins can manage companies and communication methods.
- **Communication Logs**: Stores and manages communication logs for each company.
- **API Endpoints**: Exposes RESTful APIs for the frontend to interact with.
- **Service Layer**: Handles business logic for communication methods, companies, and users.
- **Token-Based Authentication**: JWT tokens are used for secure API access.
- **Database Integration**: Uses **MongoDB** as the database to store company data, communication logs, and user details.
- **Deployed on Render.com**: The backend is deployed on Render for easy access and scalability.

## Technologies Used

- **Spring Boot**: For building the backend API.
- **Spring Data MongoDB**: For database integration with MongoDB.
- **Spring Security**: For managing authentication and authorization with token-based authentication.
- **BCrypt**: For password encryption and secure storage.
- **JWT**: For generating and validating JSON Web Tokens for user authentication.
- **Render.com**: For deploying the backend application.
