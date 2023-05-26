package com.ty.ty;


import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.ty.ty.Entity.patient;
import com.ty.ty.repository.patientRepository;
import com.ty.ty.security.Service.AccountService;

@SpringBootApplication
public class TyApplication implements CommandLineRunner{

	private patientRepository patientrepository;

	public TyApplication(patientRepository patientrepository) {
		this.patientrepository = patientrepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(TyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		patientrepository.save(new patient(null,"jean", new Date(), false, 1221));
		patientrepository.save(new patient(null,"paul", new Date(), true, 421));
		patientrepository.save(new patient(null,"jouel", new Date(), false, 221));
		patientrepository.save(new patient(null,"yannick", new Date(), true, 999));

		patient patient1 = patient.builder()
			.nom("yannick")
			.dateNaissance(new Date())	
			.malade(true)
			.score(12121)
			.build();
			patientrepository.save(patient1);
	}

	// @Bean
	CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager){
		PasswordEncoder passwordEncoder=passwordEncoder();
		return args ->{

			// UserDetails u1= jdbcUserDetailsManager.loadUserByUsername("user11");
			// if(u1 == null)
			jdbcUserDetailsManager.createUser(
				User.withUsername("user11").password(passwordEncoder.encode("1234")).authorities("USER").build()
			);

			// UserDetails u2= jdbcUserDetailsManager.loadUserByUsername("user22");
			// if(u2 == null)
			jdbcUserDetailsManager.createUser(
				User.withUsername("user22").password(passwordEncoder.encode("1234")).authorities("USER").build()
			);

			// UserDetails u3= jdbcUserDetailsManager.loadUserByUsername("admin2");
			// if(u3 == null)
			jdbcUserDetailsManager.createUser(
				User.withUsername("admin2").password(passwordEncoder.encode("1234")).authorities("USER","ADMIN").build()
			);
		};

	}
	@Bean
	CommandLineRunner createUser(AccountService accountService){
		return args ->{
			accountService.addNewRole("USER");
			accountService.addNewRole("ADMIN");
			accountService.addNewUser("user1", "1234", "user1@gmail.com", "1234");
			accountService.addNewUser("user2", "1234", "user2@gmail.com", "1234");
			accountService.addNewUser("admin", "1234", "admin@gmail.com", "1234");

			accountService.addRoleToUser("user1", "USER");
			accountService.addRoleToUser("user2", "USER");
			accountService.addRoleToUser("admin", "ADMIN");
			accountService.addRoleToUser("admin", "USER");

		};
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();//alagorithme de hachage supperieur a MD5 
	}
	

}
