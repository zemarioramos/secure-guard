package com.z7design.secured_guard.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.z7design.secured_guard.dto.*;
import com.z7design.secured_guard.model.Notification;
import com.z7design.secured_guard.model.enums.NotificationStatus;
import com.z7design.secured_guard.repository.*;

@Service
public class DashboardService {
    
    @Autowired
    private ContractRepository contractRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private PayrollRepository payrollRepository;
    
    @Autowired
    private ScheduleRepository scheduleRepository;
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    public DashboardStatsDTO getDashboardStats() {
        List<DashboardCardDTO> cards = Arrays.asList(
            DashboardCardDTO.builder()
                .title("Total de contratos ativos")
                .value(contractRepository.countActiveContracts())
                .icon("<FileText size={20} color=\"white\" />")
                .color("red")
                .change(ChangeDTO.builder().value(8).isPositive(true).build())
                .build(),
            DashboardCardDTO.builder()
                .title("Funcionários em serviço")
                .value(employeeRepository.countActiveEmployees())
                .icon("<Users size={20} color=\"black\" />")
                .color("yellow")
                .change(ChangeDTO.builder().value(12).isPositive(true).build())
                .build(),
            DashboardCardDTO.builder()
                .title("Pagamentos pendentes")
                .value("R$ " + String.format("%.2f", payrollRepository.sumPendingPayments()))
                .icon("<DollarSign size={20} color=\"white\" />")
                .color("darkred")
                .change(ChangeDTO.builder().value(5).isPositive(false).build())
                .build(),
            DashboardCardDTO.builder()
                .title("Serviços em andamento")
                .value(scheduleRepository.countActiveSchedules())
                .icon("<ClipboardList size={20} color=\"#333\" />")
                .color("light")
                .change(ChangeDTO.builder().value(3).isPositive(true).build())
                .build()
        );
        
        List<ChartDataDTO> chartData = getChartData("7d");
        
        List<DashboardAlertDTO> alerts = getRecentAlerts().stream()
            .map(this::convertToAlertDTO)
            .collect(Collectors.toList());
        
        return DashboardStatsDTO.builder()
            .cards(cards)
            .chartData(chartData)
            .alerts(alerts)
            .lastUpdated(LocalDateTime.now())
            .build();
    }
    
    public List<Notification> getRecentAlerts() {
        return notificationRepository.findTop10ByOrderByCreatedAtDesc();
    }
    
    public void markAlertAsRead(String alertId) {
        UUID id = UUID.fromString(alertId);
        Notification notification = notificationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setStatus(NotificationStatus.READ);
        notificationRepository.save(notification);
    }
    
    public List<ChartDataDTO> getChartData(String period) {
        // Implementar lógica para buscar dados do gráfico baseado no período
        List<ChartDataDTO> chartData = new ArrayList<>();
        
        String[] days = {"Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom"};
        
        for (String day : days) {
            Map<String, Object> data = new HashMap<>();
            data.put("Diurno", scheduleRepository.countDayShiftByDay(day));
            data.put("Noturno", scheduleRepository.countNightShiftByDay(day));
            
            chartData.add(ChartDataDTO.builder()
                .name(day)
                .data(data)
                .build());
        }
        
        return chartData;
    }
    
    public Map<String, Object> getRealtimeStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("activeEmployees", employeeRepository.countActiveEmployees());
        stats.put("pendingPayments", payrollRepository.sumPendingPayments());
        stats.put("activeContracts", contractRepository.countActiveContracts());
        stats.put("lastUpdated", LocalDateTime.now());
        return stats;
    }
    
    private DashboardAlertDTO convertToAlertDTO(Notification notification) {
        return DashboardAlertDTO.builder()
            .id(notification.getId().toString())
            .title(notification.getTitle())
            .message(notification.getMessage())
            .time(formatTime(notification.getCreatedAt()))
            .type(notification.getType().name().toLowerCase())
            .read(notification.getStatus() == NotificationStatus.READ)
            .build();
    }
    
    private String formatTime(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        long hours = java.time.Duration.between(dateTime, now).toHours();
        
        if (hours < 1) {
            return "Agora mesmo";
        } else if (hours == 1) {
            return "1 hora atrás";
        } else {
            return hours + " horas atrás";
        }
    }
}