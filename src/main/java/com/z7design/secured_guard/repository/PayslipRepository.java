package com.z7design.secured_guard.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.z7design.secured_guard.model.Payslip;

@Repository
public interface PayslipRepository extends JpaRepository<Payslip, UUID> {
} 