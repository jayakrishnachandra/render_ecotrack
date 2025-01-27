
## Overview

The EcoTrack Backend is a Spring Boot-based application designed to manage backend operations for the EcoTrack project. It functions as the API layer for handling user authentication, managing water and electricity usage, and storing usage logs. The backend integrates with MongoDB for persistent storage and provides services for users and administrators to monitor and manage consumption. It employs token-based authentication and BCrypt for password encryption to ensure secure interactions.

## Features

- **User Authentication**: Manages user registration, login, and token-based authentication with BCrypt-encrypted passwords.
- **Usage Tracking**: Tracks daily water and electricity usage with real-time updates.
- **Personalized Goals**: Allows users to set and manage personalized consumption targets.
- **Usage Insights**: Provides detailed usage statistics and trends over daily, weekly, and monthly periods.
- **Notifications**: Sends email alerts for goal achievements and overuse warnings.
- **API Endpoints**: Offers RESTful APIs for seamless frontend integration.
- **Service Layer**: Manages business logic for usage tracking, goal setting, and notifications.
- **Token-Based Authentication**: Ensures secure API access with JWT tokens.
- **Database Integration**: Utilizes MongoDB to store user data, usage logs, and goal details.
- **Deployment on Render.com**: Deployed on Render for reliability, scalability, and easy access.

## Technologies Used

- **Spring Boot**: For building the backend API.
- **Spring Data MongoDB**: For database integration with MongoDB.
- **Spring Security**: For managing authentication and authorization with token-based authentication.
- **BCrypt**: For password encryption and secure storage.
- **JWT**: For generating and validating JSON Web Tokens for user authentication.
- **Render.com**: For deploying the backend application.
