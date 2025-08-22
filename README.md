# 🛒 Spring Boot + Redis Cache Example

## 📌 Overview
This project demonstrates **how to integrate Redis caching** into a Spring Boot application using **Jedis** as the connection factory.  
The application manages **Product** data, caching frequently accessed records to improve performance and reduce database load.
 
It supports:
- Fetching products by ID, category, or top-selling list
- Updating and deleting products
- Storing results in Redis for 10 minutes
- Automatic retrieval from cache if available

---

## 🚀 Features
- **Spring Boot 3.x + Java 19**
- **Jedis Connection Factory** for Redis
- **RedisTemplate** with `GenericJackson2JsonRedisSerializer`
- Caching CRUD operations for products
- Custom Redis key structure (`PRODUCT_<id>`, `PRODUCT_topProducts`, etc.)

---

## 🛠 Tech Stack
- **Java 19**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Data Redis**
- **Jedis**
- **Maven**
- **MySql Database** (or your DB of choice)


## ⚙️ Redis Configuration
The app uses a **JedisConnectionFactory** to connect to Redis.
Make sure Redis is running locally on port 6379.

✨ Author
Neelu Sahai
