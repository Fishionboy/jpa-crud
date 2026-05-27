package com.apicrud.jpa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.apicrud.jpa.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

    
} 
