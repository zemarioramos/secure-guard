package com.z7design.secured_guard.repository;

import com.z7design.secured_guard.model.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, UUID> {
    
    Optional<SystemConfig> findByConfigKey(String configKey);
    
    List<SystemConfig> findByIsPublicTrue();
    
    @Query("SELECT s FROM SystemConfig s WHERE s.configKey LIKE :prefix%")
    List<SystemConfig> findByConfigKeyPrefix(@Param("prefix") String prefix);
    
    boolean existsByConfigKey(String configKey);
}