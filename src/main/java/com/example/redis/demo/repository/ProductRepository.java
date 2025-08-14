package com.example.redis.demo.repository;

import com.example.redis.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findTop5ByOrderByStockDesc();
    List<Product> findByCategory(String category);
}

