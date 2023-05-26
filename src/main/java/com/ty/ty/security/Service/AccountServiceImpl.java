package com.ty.ty.security.Service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ty.ty.security.entities.AppRole;
import com.ty.ty.security.entities.AppUser;
import com.ty.ty.security.repo.AppRoleRepository;
import com.ty.ty.security.repo.AppUserRepository;

import lombok.AllArgsConstructor;


@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser appUser= appUserRepository.findByUsername(username);
        if(appUser != null)
        throw new RuntimeException("Cet utilisateur exist Deja");
        if(!password.equals(confirmPassword)) throw new RuntimeException("Le Mots de Passe Ne Correspond Pas");
        appUser = AppUser.builder()
            .userId(UUID.randomUUID().toString())
            .username(username)
            .email(email)
            .password(passwordEncoder.encode(confirmPassword))
            .build();

       AppUser savedAppUser= appUserRepository.save(appUser);
        return savedAppUser;
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole approle = appRoleRepository.findById(role).orElse(null);
        if(approle != null)throw new RuntimeException("This Role Exist Deja");
        approle=AppRole.builder()
            .role(role)
            .build();

        return appRoleRepository.save(approle);
        
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser=appUserRepository.findByUsername(username);
        AppRole appRole=appRoleRepository.findById(role).get();

        appUser.getRoles().add(appRole);
        appUserRepository.save(appUser);
    }

    @Override
    public void removeRoleToUser(String username, String role) {
        
        AppUser appUser=appUserRepository.findByUsername(username);
        AppRole appRole=appRoleRepository.findById(role).get();

        appUser.getRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUserName(String username) {
        
        return appUserRepository.findByUsername(username);
    }

    

}