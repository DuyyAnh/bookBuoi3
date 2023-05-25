package com.example.buoi6knSQL.service;

import com.example.buoi6knSQL.entity.Product;
import com.example.buoi6knSQL.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repo;
    public List<Product> GetAll() {
        return (List<Product>) repo.findAll();
    }
    public Product getBookById(Integer id) {
        Optional<Product> optional = repo.findById(id);
        return optional.orElse(null);
    }
    public void add(Product newProduct){
        repo.save(newProduct);
    }
    public void updateproduct(Product product){
        repo.save(product);
    }
    public void deleteproduct(Integer id){
        repo.deleteById(id);
    }
}
