package com.z7design.secured_guard.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.z7design.secured_guard.validation.Cpf;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.z7design.secured_guard.model.enums.EmploymentStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "employees")
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;
    
    @Column(nullable = false)
    private String registrationNumber;
    
    @Column(nullable = false)
    private LocalDate hireDate;
    
    private LocalDate terminationDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmploymentStatus status;
    
    @Column(length = 500)
    private String notes;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String document;
    
    @Column(name = "birth_date")
    private LocalDate birthDate;
    
    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;
    
    @Column
    private String address;
    
    @Column
    private String phone;
    
    @Column
    private String email;
    
    // Dados Pessoais
    @NotBlank(message = "O CPF é obrigatório")
    @Cpf(message = "CPF inválido")
    @Column(unique = true, nullable = false)
    private String cpf;
    
    @NotBlank(message = "O RG é obrigatório")
    @Column(unique = true, nullable = false)
    private String rg;
    
    @NotBlank(message = "O estado civil é obrigatório")
    @Column(name = "marital_status")
    private String maritalStatus;
    
    @NotBlank(message = "A nacionalidade é obrigatória")
    @Column
    private String nationality;
    
    @Column(name = "photo_url")
    private String photoUrl;

    // Dados Profissionais
    @Column(name = "current_scale")
    private String currentScale;

    // Documentos
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Document> documents;

    // Benefícios
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Benefit> benefits;

    // Histórico de Escalas
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Schedule> schedules;

    // Ocorrências
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Occurrence> occurrences;

    // Holerites
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference("employee-payrolls")
    private List<Payroll> payrolls;

    // EPIs
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<EPI> epis;
} 