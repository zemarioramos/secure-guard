package com.z7design.secured_guard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardAlertDTO {
    private Long id;
    private String title;
    private String message;
    private String type;
    private LocalDateTime createdAt;
    private boolean read;
} 