package com.ty.ty.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.ty.ty.security.Service.UserDetailServiceIml;

@Configuration
@EnableWebSecurity
//authorisation des resource avec des annotation
@EnableMethodSecurity(prePostEnabled = true)
public class securityConfig {

    private PasswordEncoder passwordEncoder;
    private UserDetailServiceIml userDetailServiceIml;
    public securityConfig(PasswordEncoder passwordEncoder, UserDetailServiceIml userDetailServiceIml) {
        this.userDetailServiceIml = userDetailServiceIml;
        this.passwordEncoder = passwordEncoder;
    }


    // @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }


    //  @Bean
    // public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
    //     return new InMemoryUserDetailsManager(
    //         User.withUsername("tyan").password(passwordEncoder.encode("1234")).authorities("USER").build(),
    //         User.withUsername("yan").password(passwordEncoder.encode("1234")).authorities("USER").build(),
    //         User.withUsername("admin").password(passwordEncoder.encode("1234")).authorities("USER","ADMIN").build()
    //     );

    // }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.authorizeHttpRequests((auth) -> {
            try {
                auth
                        .requestMatchers("/admin/*").hasAuthority("ADMIN")
                        .requestMatchers("/user/index").permitAll()
                        .and()
                        .formLogin(login -> login.loginPage("/login").defaultSuccessUrl("/user/index").permitAll()); 
            } catch (Exception e) {
                
                e.printStackTrace();
            }
        }
        ).httpBasic(withDefaults());           

        // httpSecurity.formLogin(login -> login.loginPage("/login").permitAll());
        httpSecurity.rememberMe();
        
        //acceptation de la bibliotheque de bootstrap a accede au server et la base de donne H2
        httpSecurity.authorizeHttpRequests().requestMatchers("/webjars/**").permitAll();

        // httpSecurity.authorizeHttpRequests().anyRequest().permitAll();

        //definition des role des utilisateur
        // httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");
        //httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
        
        httpSecurity.exceptionHandling().accessDeniedPage("/notAuthorized");
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
        httpSecurity.userDetailsService(userDetailServiceIml);
        return httpSecurity.build();
        
    } 
}
