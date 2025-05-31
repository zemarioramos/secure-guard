package com.z7design.secured_guard.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    private List<DashboardCardDTO> cards;
    private List<ChartDataDTO> chartData;
    private List<DashboardAlertDTO> alerts;
    private LocalDateTime lastUpdated;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DashboardCardDTO {
    private String title;
    private Object value;
    private ChangeDTO change;
    private String icon;
    private String color;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ChangeDTO {
    private Integer value;
    private Boolean isPositive;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DashboardAlertDTO {
    private String id;
    private String title;
    private String message;
    private String time;
    private String type;
    private Boolean read;
}