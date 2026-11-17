package com.example.hospital_manager.controller;

import com.example.hospital_manager.model.Appointment;
import com.example.hospital_manager.repository.AppointmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;

    public AppointmentController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        return ResponseEntity.ok(appointment);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('PATIENT', 'ADMIN', 'DOCTOR')")
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        if (appointment.getAppointmentDate() == null) {
            appointment.setAppointmentDate(LocalDateTime.now());
        }
        if (appointment.getStatus() == null) {
            appointment.setStatus("PENDING");
        }
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return ResponseEntity.ok(savedAppointment);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointmentDetails) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        appointment.setAppointmentDate(appointmentDetails.getAppointmentDate());
        appointment.setStatus(appointmentDetails.getStatus());
        appointment.setReason(appointmentDetails.getReason());

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return ResponseEntity.ok(updatedAppointment);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PATIENT')")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        if (!appointmentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        appointmentRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Appointment cancelled successfully"));
    }
}