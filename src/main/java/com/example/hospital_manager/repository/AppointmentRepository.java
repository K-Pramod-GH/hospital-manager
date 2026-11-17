package com.example.hospital_manager.repository;

import com.example.hospital_manager.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // විශේෂිත Doctor කෙනෙකුට අදාළ appointments ලබා ගැනීමට
    List<Appointment> findByDoctorId(Long doctorId);

    // විශේෂිත Patient කෙනෙකුට අදාළ appointments ලබා ගැනීමට
    List<Appointment> findByPatientId(Long patientId);

    // Patient කෙනෙකුට කිසියම් Appointment එකක් තිබේදැයි පරීක්ෂා කිරීමට (Patient delete කිරීමට පෙර data check කිරීමට උදව් වේ)
    boolean existsByPatientId(Long patientId);
}