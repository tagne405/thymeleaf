package com.ty.ty.security.Service;

import com.ty.ty.security.entities.AppRole;
import com.ty.ty.security.entities.AppUser;

public interface AccountService  {
    AppUser addNewUser(String username, String password, String email, String confirmPassword);

    AppRole addNewRole(String role);

    void addRoleToUser(String username, String role);

    void removeRoleToUser(String username, String role);

    AppUser loadUserByUserName (String username); 
}
