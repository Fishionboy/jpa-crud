package com.apicrud.jpa.services;

import java.util.List;

import com.apicrud.jpa.entities.User;

public interface UserService {
    List<User> findAll();

    User save(User user);
    
    boolean existsByUsername(String username);
}