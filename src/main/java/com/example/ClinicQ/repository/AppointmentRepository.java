package com.example.ClinicQ.repository;

import com.example.ClinicQ.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findBySlotDoctorIdAndSlotDate(
            Long doctorId,
            LocalDate date
    );

    List<Appointment> findByPatientId(Long patientId);
}