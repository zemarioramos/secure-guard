package com.z7design.secured_guard.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.z7design.secured_guard.model.enums.ContractType;
import com.z7design.secured_guard.model.enums.ContractStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contracts")
public class Contract {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "contract_number", nullable = false, unique = true)
    private String contractNumber;
    
    @Column(name = "client_id", nullable = false)
    private UUID clientId;
    
    @Column(name = "unit_id", nullable = false)
    private UUID unitId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ContractType type;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ContractStatus status;
    
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    
    @Column(name = "end_date")
    private LocalDateTime endDate;
    
    @Column(nullable = false)
    private BigDecimal value;
    
    @Column(length = 1000)
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    @JsonBackReference
    private Client client;
    
    @ManyToOne
    @JoinColumn(name = "unit_id", insertable = false, updatable = false)
    @JsonBackReference
    private Unit unit;
    
    @OneToOne(mappedBy = "contract", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Proposal proposal;
    
    @Column(name = "infrastructure_demand")
    private String infrastructureDemand;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 