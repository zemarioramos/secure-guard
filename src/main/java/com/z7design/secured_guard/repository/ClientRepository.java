package com.z7design.secured_guard.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.z7design.secured_guard.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByCnpj(String cnpj);
    Optional<Client> findByCompanyName(String companyName);
    boolean existsByCnpj(String cnpj);
    boolean existsByCompanyName(String companyName);
} 