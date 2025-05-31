package com.z7design.secured_guard.repository;

import com.z7design.secured_guard.entity.Configuracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Long> {
    List<Configuracao> findByType(String type);
    Optional<Configuracao> findByKey(String key);
} 