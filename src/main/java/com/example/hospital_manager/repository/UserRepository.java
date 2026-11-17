package com.example.hospital_manager.repository;

import com.example.hospital_manager.model.AppUser;
import com.example.hospital_manager.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    // Username එක මඟින් user කෙනෙක් සොයා ගැනීමට
    Optional<AppUser> findByUsername(String username);

    // Username එක system එකේ දැනටමත් තිබේදැයි පරීක්ෂා කිරීමට
    boolean existsByUsername(String username);

    // Doctor role එක ඇති සියලුම දෙනා ලැයිස්තුගත කිරීමට
    List<AppUser> findByRole(Role role);
}