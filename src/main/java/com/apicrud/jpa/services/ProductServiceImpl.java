package com.apicrud.jpa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apicrud.jpa.entities.Product;
import com.apicrud.jpa.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {

        return (List<Product>) repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) {

        return repository.findById(id);
    }

    @Transactional
    @Override
    public Product save(Product product) {

        return repository.save(product);
    }

    @Transactional
    @Override
    public Optional<Product> update(Long id, Product product) {
        Optional<Product> productOptional = repository.findById(id);

        if(productOptional.isPresent()) {
            Product prod = productOptional.orElseThrow();
            prod.setName(product.getName());
            prod.setPrice(product.getPrice());
            prod.setDescription(product.getDescription());
            return Optional.of(repository.save(prod));
        };
        return productOptional;

    }

    @Override
    public Optional<Product> delete(Long id) {
        Optional<Product> productOptional = repository.findById(id);

        productOptional.ifPresent(prod -> {
            repository.delete(prod);
        });
        return productOptional;
    }
}
