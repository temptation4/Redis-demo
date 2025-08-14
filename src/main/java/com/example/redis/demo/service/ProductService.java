package com.example.redis.demo.service;

import com.example.redis.demo.entity.Product;
import com.example.redis.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private static final String PRODUCT_CACHE_PREFIX = "PRODUCT_";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long id) {
        String cacheKey = PRODUCT_CACHE_PREFIX + id;

        // 1. Check Redis first
        Product cachedProduct = (Product) redisTemplate.opsForValue().get(cacheKey);
        if (cachedProduct != null) {
            System.out.println("🔹 Fetched from Redis cache");
            return cachedProduct;
        }

        // 2. Simulate DB fetch (in real app, call repository)
        System.out.println("⚡ Fetching from Database");
        Product productFromDb = productRepository.findById(id).orElse(null);

        // 3. Store in Redis for 10 minutes
        redisTemplate.opsForValue().set(cacheKey, productFromDb, 10, TimeUnit.MINUTES);

        return productFromDb;
    }

    public List<Product> getTopSellingProducts() {
        String cacheKey = PRODUCT_CACHE_PREFIX + "topProducts";
        // 1. Check Redis first
        List<Product> cachedProduct = (List<Product>) redisTemplate.opsForValue().get(cacheKey);
        if (cachedProduct != null) {
            System.out.println("🔹 Fetched from Redis cache");
            return cachedProduct;
        }

        // 2. Simulate DB fetch (in real app, call repository)
        System.out.println("⚡ Fetching from Database");
        List<Product>  productFromDb = productRepository.findTop5ByOrderByStockDesc();

        // 3. Store in Redis for 10 minutes
        redisTemplate.opsForValue().set(cacheKey, productFromDb, 10, TimeUnit.MINUTES);

        return productFromDb;
    }

    public List<Product> getProductsByCategory(String category) {
        String cacheKey = PRODUCT_CACHE_PREFIX + category;
        // 1. Check Redis first
        List<Product> cachedProduct = (List<Product>) redisTemplate.opsForValue().get(cacheKey);
        if (cachedProduct != null) {
            System.out.println("🔹 Fetched from Redis cache");
            return cachedProduct;
        }

        // 2. Simulate DB fetch (in real app, call repository)
        System.out.println("⚡ Fetching from Database");
        List<Product>  productFromDb = productRepository.findByCategory(category);

        // 3. Store in Redis for 10 minutes
        redisTemplate.opsForValue().set(cacheKey, productFromDb, 10, TimeUnit.MINUTES);

        return productFromDb;
    }

    public Product updateProduct(Product product) {
        String cacheKey = PRODUCT_CACHE_PREFIX + product.getId();

        // Update DB (simulate)
        Product res = productRepository.save(product);
        System.out.println("✅ Product updated in DB");

        // Update Redis cache
        redisTemplate.opsForValue().set(cacheKey, product, 10, TimeUnit.MINUTES);
        return res;
    }
    
    public void deleteProduct(Long id) {
        String cacheKey = PRODUCT_CACHE_PREFIX + id;

        // Delete from DB (simulate)
        productRepository.deleteById(id);
        System.out.println("🗑️ User deleted from DB");

        // Remove from cache
        redisTemplate.delete(cacheKey);

    }
}

