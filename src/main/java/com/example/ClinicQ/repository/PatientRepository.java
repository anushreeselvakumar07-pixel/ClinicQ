package com.example.ClinicQ.repository;

import com.example.ClinicQ.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}