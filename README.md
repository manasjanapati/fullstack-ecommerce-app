# Fullstack E-Commerce Application

A modern, production-style fullstack e-commerce web application built using React, Spring Boot, PostgreSQL, JWT Authentication, and Cloud Deployment.

---

# Live Demo

## Frontend

Vercel URL

https://manasjanapati-ecommerce.vercel.app/

---

## Backend API

Render URL

https://fullstack-ecommerce-app-n988.onrender.com/

---

# Tech Stack

## Frontend

- React
- Vite
- React Router DOM
- Axios
- CSS3

---

## Backend

- Spring Boot
- Spring Security
- JWT Authentication
- Hibernate / JPA
- Maven

---

## Database

- PostgreSQL
- Neon PostgreSQL Cloud Database

---

## Cloud Services

- Cloudinary (Image Uploads)
- Render (Backend Deployment)
- Vercel (Frontend Deployment)

---

# Features

## Authentication & Authorization

- User Registration
- User Login
- JWT Authentication
- Role-Based Authorization
- Admin Protected Routes

---

## Customer Features

- Browse Products
- Product Details Page
- Add To Cart
- Cart Management
- Checkout & Place Orders
- View Orders
- Product Ratings
- Search Products
- Category Filtering

---

## Admin Features

- Admin Dashboard
- Product Management
- Product Image Upload
- Category Management
- Hierarchical Categories
- Order Management
- Soft Delete System

---

# Advanced Features

## Product Rating System

- Verified purchase ratings
- One-time product rating
- Rating count tracking
- Average rating calculation

---

## Image Upload System

- Cloudinary Integration
- Secure image hosting
- Product image management

---

## Soft Delete

Products are not permanently removed from the database.

This protects:

- order history
- product analytics
- historical data consistency

---

# Project Architecture

```text
Frontend (React + Vite)
        ↓
REST API (Spring Boot)
        ↓
PostgreSQL Database (Neon)
        ↓
Cloudinary Image Hosting
```

---

# Folder Structure

```text
fullstack-ecommerce-app/
│
├── backend/
│   ├── src/
│   ├── pom.xml
│   ├── Dockerfile
│   └── ...
│
├── frontend/
│   ├── src/
│   ├── package.json
│   └── ...
│
└── README.md
```

---

# Backend Setup

## Clone Repository

```bash
git clone https://github.com/manasjanapati/fullstack-ecommerce-app.git
```

---

## Navigate to Backend

```bash
cd backend
```

---

## Configure Environment Variables

Create:

```text
.env
```

Example:

```env
SPRING_DATASOURCE_URL=your_database_url

SPRING_DATASOURCE_USERNAME=your_username

SPRING_DATASOURCE_PASSWORD=your_password

JWT_SECRET=your_jwt_secret

CLOUDINARY_CLOUD_NAME=your_cloud_name

CLOUDINARY_API_KEY=your_api_key

CLOUDINARY_API_SECRET=your_api_secret
```

---

## Run Backend

```bash
./mvnw spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

---

# Frontend Setup

## Navigate to Frontend

```bash
cd frontend
```

---

## Install Dependencies

```bash
npm install
```

---

## Configure Frontend Environment Variable

Create:

```text
.env
```

Add:

```env
VITE_API_BASE_URL=http://localhost:8080/api
```

---

## Run Frontend

```bash
npm run dev
```

Frontend runs on:

```text
http://localhost:5173
```

---

# API Features

## Authentication

- JWT-based authentication
- Stateless sessions
- Secure endpoints

---

## Products

- CRUD operations
- Pagination
- Filtering
- Search
- Ratings

---

## Orders

- Place orders
- Track orders
- Order history

---

# Deployment

## Frontend Deployment

Deployed using:

- Vercel  
  https://vercel.com

---

## Backend Deployment

Deployed using:

- Render  
  https://render.com

---

## Database Hosting

Hosted on:

- Neon PostgreSQL  
  https://neon.tech

---

## Image Hosting

Powered by:

- Cloudinary  
  https://cloudinary.com

---

# Security Features

- JWT Authentication
- Environment Variable Configuration
- Role-Based Access Control
- Secure Cloudinary Integration
- Protected Admin APIs

---

# Future Improvements

- Product Reviews with Comments
- Payment Gateway Integration
- Wishlist System
- Coupon System
- Inventory Analytics
- Admin Sales Dashboard
- Recommendation Engine
- Email Notifications
- Order Tracking Timeline
- Multi-Vendor Marketplace

---

# Author

## Manas Janapati

GitHub:  
https://github.com/manasjanapati

---

# License

This project is built for educational and portfolio purposes.
