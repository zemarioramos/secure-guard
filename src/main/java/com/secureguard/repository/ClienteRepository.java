package com.secureguard.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.z7design.secured_guard.model.Client;

@Repository
public interface ClienteRepository extends JpaRepository<Client, Long> {
    
    @Cacheable(value = "clientes", key = "#root.methodName")
    @Query("SELECT c FROM Client c ORDER BY c.nome")
    List<Client> findAllSorted();
}