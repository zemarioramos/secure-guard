package com.z7design.secured_guard.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private UUID id;
    private String companyName;
    private String cnpj;
    private String address;
    private String phone;
    private String whatsapp;
    private String email;
    private String responsiblePerson;
    private String contact;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 