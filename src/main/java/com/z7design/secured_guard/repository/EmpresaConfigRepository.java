package com.z7design.secured_guard.repository;

import com.z7design.secured_guard.model.EmpresaConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmpresaConfigRepository extends JpaRepository<EmpresaConfig, UUID> {
    
    Optional<EmpresaConfig> findByCnpj(String cnpj);
    
    @Query("SELECT e FROM EmpresaConfig e ORDER BY e.createdAt ASC LIMIT 1")
    Optional<EmpresaConfig> findFirstConfig();
    
    boolean existsByCnpj(String cnpj);
}