package com.nominas.empresa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nominas.empresa.models.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
    AppUser findByEmail(String email);
    
}