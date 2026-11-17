package com.example.hospital_manager.dto;

import com.example.hospital_manager.model.Role;

public record RegisterRequest(
    String username,
    String password,
    String fullName,
    Role role
) {}