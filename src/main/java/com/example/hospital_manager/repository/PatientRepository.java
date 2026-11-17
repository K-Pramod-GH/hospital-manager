package com.example.hospital_manager.repository;

import com.example.hospital_manager.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Patient ගේ නම (full name) මත පදනම්ව search කිරීමට (case-insensitive ලෙස partial search කිරීමට ද පුළුවන)
    List<Patient> findByFullNameContainingIgnoreCase(String name);

    // Email එක හෝ phone එක මඟින් duplicate පරීක්ෂා කිරීමට අවශ්‍ය නම්
    boolean existsByEmail(String email);
}