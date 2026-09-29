package com.example.ClinicQ.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "slot_id")
    private Slot slot;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private LocalDateTime bookedAt;

    public Appointment() {
    }

    public Appointment(Patient patient, Slot slot) {
        this.patient = patient;
        this.slot = slot;
        this.status = AppointmentStatus.BOOKED;
        this.bookedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Slot getSlot() {
        return slot;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }
}
