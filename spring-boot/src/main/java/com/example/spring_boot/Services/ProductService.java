package com.example.spring_boot.Services;


import com.example.spring_boot.Entity.Product;
import com.example.spring_boot.Repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product getProduct(Long id){
        return productRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Product not found"));
    }
}
