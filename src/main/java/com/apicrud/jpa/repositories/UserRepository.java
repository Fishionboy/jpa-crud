package com.apicrud.jpa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.apicrud.jpa.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{

    
}