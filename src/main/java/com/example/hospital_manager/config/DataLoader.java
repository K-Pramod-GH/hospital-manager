package com.example.hospital_manager.config;

import com.example.hospital_manager.model.AppUser;
import com.example.hospital_manager.model.Role;
import com.example.hospital_manager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if default admin user exists, if not create one
            if (userRepository.findByUsername("admin").isEmpty()) {
                AppUser admin = new AppUser();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
                System.out.println("Default Admin user created successfully!");
            }

            // Check if default doctor user exists, if not create one
            if (userRepository.findByUsername("doctor_john").isEmpty()) {
                AppUser doctor = new AppUser();
                doctor.setUsername("doctor_john");
                doctor.setPassword(passwordEncoder.encode("doctor123"));
                doctor.setRole(Role.DOCTOR);
                userRepository.save(doctor);
                System.out.println("Default Doctor user created successfully!");
            }

            // Check if default patient user exists, if not create one
            if (userRepository.findByUsername("patient_smith").isEmpty()) {
                AppUser patient = new AppUser();
                patient.setUsername("patient_smith");
                patient.setPassword(passwordEncoder.encode("patient123"));
                patient.setRole(Role.PATIENT);
                userRepository.save(patient);
                System.out.println("Default Patient user created successfully!");
            }
        };
    }
}