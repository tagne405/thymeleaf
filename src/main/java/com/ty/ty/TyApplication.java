package com.ty.ty;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ty.ty.Entity.patient;
import com.ty.ty.repository.patientRepository;

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

	

}
