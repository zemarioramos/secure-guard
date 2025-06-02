package com.z7design.secured_guard.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.z7design.secured_guard.validation.Cpf;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.z7design.secured_guard.model.enums.EmploymentStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
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

    // Relacionamentos
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("employee-time-records")
    private List<TimeRecord> timeRecords = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Schedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Vacation> vacations = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Leave> leaves = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Overtime> overtimes = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<EmployeeCertification> certifications = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Dependent> dependents = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<EPI> epis = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Occurrence> occurrences = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<PerformanceEvaluation> performanceEvaluations = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("employee-payrolls")
    private List<Payroll> payrolls = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Benefit> benefits = new ArrayList<>();

    // Getters para relacionamentos (garantindo Lazy Loading)
    public List<TimeRecord> getTimeRecords() {
        if (timeRecords == null) {
            timeRecords = new ArrayList<>();
        }
        return timeRecords;
    }

    public List<Schedule> getSchedules() {
        if (schedules == null) {
            schedules = new ArrayList<>();
        }
        return schedules;
    }

    public List<Vacation> getVacations() {
        if (vacations == null) {
            vacations = new ArrayList<>();
        }
        return vacations;
    }

    public List<Leave> getLeaves() {
        if (leaves == null) {
            leaves = new ArrayList<>();
        }
        return leaves;
    }

    public List<Overtime> getOvertimes() {
        if (overtimes == null) {
            overtimes = new ArrayList<>();
        }
        return overtimes;
    }

    public List<EmployeeCertification> getCertifications() {
        if (certifications == null) {
            certifications = new ArrayList<>();
        }
        return certifications;
    }

    public List<Dependent> getDependents() {
        if (dependents == null) {
            dependents = new ArrayList<>();
        }
        return dependents;
    }

    public List<Document> getDocuments() {
        if (documents == null) {
            documents = new ArrayList<>();
        }
        return documents;
    }

    public List<EPI> getEpis() {
        if (epis == null) {
            epis = new ArrayList<>();
        }
        return epis;
    }

    public List<Occurrence> getOccurrences() {
        if (occurrences == null) {
            occurrences = new ArrayList<>();
        }
        return occurrences;
    }

    public List<PerformanceEvaluation> getPerformanceEvaluations() {
        if (performanceEvaluations == null) {
            performanceEvaluations = new ArrayList<>();
        }
        return performanceEvaluations;
    }

    public List<Payroll> getPayrolls() {
        if (payrolls == null) {
            payrolls = new ArrayList<>();
        }
        return payrolls;
    }

    public List<Benefit> getBenefits() {
        if (benefits == null) {
            benefits = new ArrayList<>();
        }
        return benefits;
    }
} 