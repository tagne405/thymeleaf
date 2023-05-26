package com.ty.ty.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.ty.security.entities.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser,String> {
    AppUser findByUsername(String username);
    
}
