package com.ty.ty.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ty.ty.Entity.patient;
@Repository
public interface patientRepository extends JpaRepository <patient,Long> {
    Page<patient> findByNomContains(String keyword,Pageable pageable);

    // @Query("select p from patient p where p.nom like :x")
    // Page<patient> chercher(@Param("X") String keyword,Pageable pageable);
}
