package com.ty.ty.security.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ty.ty.security.entities.AppUser;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class UserDetailServiceIml implements UserDetailsService{
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
       AppUser appuser = accountService.loadUserByUserName(username);
        if(appuser == null) throw new UsernameNotFoundException(String.format("user %s not Found", username));

        // String[] roles = appuser.getRoles().stream().map(u ->u.getRole()).toArray(String[]::new);
        List<SimpleGrantedAuthority> authorities = appuser.getRoles().stream().map(r-> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());

        UserDetails userDetails = User
                            .withUsername(appuser.getUsername())
                            .password(appuser.getPassword())
                            .authorities(authorities).build();
                            // .roles(roles).build();

       return userDetails;
    }
    
}
