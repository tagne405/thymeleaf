package com.ty.ty.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.ty.security.entities.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole,String>{
    
}
