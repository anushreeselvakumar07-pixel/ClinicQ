package com.example.ClinicQ.service;

import com.example.ClinicQ.entity.Slot;
import com.example.ClinicQ.entity.SlotStatus;
import com.example.ClinicQ.repository.SlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SlotService {

    private final SlotRepository slotRepository;

    public SlotService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    public Slot addSlot(Slot slot) {
        slot.setStatus(SlotStatus.AVAILABLE);
        return slotRepository.save(slot);
    }

    public List<Slot> getAllSlots() {
        return slotRepository.findAll();
    }

    public Optional<Slot> getSlotById(Long id) {
        return slotRepository.findById(id);
    }

    public Slot updateSlot(Long id, Slot slot) {
        Slot existingSlot = slotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        existingSlot.setDate(slot.getDate());
        existingSlot.setStartTime(slot.getStartTime());
        existingSlot.setEndTime(slot.getEndTime());
        existingSlot.setDoctor(slot.getDoctor());

        return slotRepository.save(existingSlot);
    }

    public void deleteSlot(Long id) {
        if (!slotRepository.existsById(id)) {
            throw new RuntimeException("Slot not found");
        }

        slotRepository.deleteById(id);
    }
}