package com.example.hospital_manager.controller;

import com.example.hospital_manager.model.MedicalRecord;
import com.example.hospital_manager.repository.MedicalRecordRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordController(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    // සියලුම වෛද්‍ය වාර්තා ලබා ගැනීම (Doctor හෝ ADMIN සඳහා පමණි)
    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {
        List<MedicalRecord> records = medicalRecordRepository.findAll();
        return ResponseEntity.ok(records);
    }

    // ID එක මඟින් නිශ්චිත වාර්තාවක් ලබා ගැනීම
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN', 'PATIENT')")
    public ResponseEntity<MedicalRecord> getMedicalRecordById(@PathVariable Long id) {
        MedicalRecord record = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical Record not found with id: " + id));
        return ResponseEntity.ok(record);
    }

    // නව වෛද්‍ය වාර්තාවක් ඇතුළත් කිරීම (Doctor පමණි)
    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        if (medicalRecord.getRecordDate() == null) {
            medicalRecord.setRecordDate(LocalDateTime.now());
        }
        MedicalRecord savedRecord = medicalRecordRepository.save(medicalRecord);
        return ResponseEntity.ok(savedRecord);
    }

    // වාර්තාවක් මකා දැමීම (ADMIN පමණි)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteMedicalRecord(@PathVariable Long id) {
        if (!medicalRecordRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        medicalRecordRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Medical record deleted successfully"));
    }
}