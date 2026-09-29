package com.example.ClinicQ.repository;

import com.example.ClinicQ.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotRepository extends JpaRepository<Slot, Long> {
}