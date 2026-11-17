package com.example.hospital_manager.repository;

import com.example.hospital_manager.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByPatientId(Long patientId);

    @Query("SELECT COUNT(m) > 0 FROM MedicalRecord m WHERE m.patient.id = :patientId")
    boolean existsByPatientId(@Param("patientId") Long patientId);

}
