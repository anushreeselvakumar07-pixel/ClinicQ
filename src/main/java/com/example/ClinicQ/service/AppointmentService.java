package com.example.ClinicQ.service;

import com.example.ClinicQ.entity.Appointment;
import com.example.ClinicQ.entity.AppointmentStatus;
import com.example.ClinicQ.entity.Slot;
import com.example.ClinicQ.entity.SlotStatus;
import com.example.ClinicQ.entity.Patient;
import com.example.ClinicQ.exception.SlotAlreadyBookedException;
import com.example.ClinicQ.repository.AppointmentRepository;
import com.example.ClinicQ.repository.PatientRepository;
import com.example.ClinicQ.repository.SlotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final SlotRepository slotRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            SlotRepository slotRepository) {

        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.slotRepository = slotRepository;
    }

    public Appointment bookAppointment(Long patientId, Long slotId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.getStatus() == SlotStatus.BOOKED) {
            throw new SlotAlreadyBookedException(
                    "Slot is already booked");
        }

        Appointment appointment = new Appointment(patient, slot);

        slot.setStatus(SlotStatus.BOOKED);
        slotRepository.save(slot);

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Appointment not found"));
    }

    public Appointment cancelAppointment(Long id) {

        Appointment appointment = getAppointmentById(id);

        appointment.setStatus(AppointmentStatus.CANCELLED);

        Slot slot = appointment.getSlot();
        slot.setStatus(SlotStatus.AVAILABLE);

        slotRepository.save(slot);

        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long id) {

        Appointment appointment = getAppointmentById(id);

        Slot slot = appointment.getSlot();
        slot.setStatus(SlotStatus.AVAILABLE);

        slotRepository.save(slot);

        appointmentRepository.deleteById(id);
    }

    // Get appointments for a specific doctor and selected date
    public List<Appointment> getAppointmentsForDoctorAndDate(
            Long doctorId,
            LocalDate date) {

        return appointmentRepository
                .findBySlotDoctorIdAndSlotDate(
                        doctorId,
                        date
                );
    }

    // Get appointments belonging to a specific patient
    public List<Appointment> getAppointmentsForPatient(
            Long patientId) {

        if (!patientRepository.existsById(patientId)) {
            throw new RuntimeException("Patient not found");
        }

        return appointmentRepository.findByPatientId(patientId);
    }
}