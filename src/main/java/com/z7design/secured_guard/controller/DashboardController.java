package com.z7design.secured_guard.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.z7design.secured_guard.dto.DashboardStatsDTO;
import com.z7design.secured_guard.dto.ChartDataDTO;
import com.z7design.secured_guard.model.Notification;
import com.z7design.secured_guard.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {
    
    @Autowired
    private DashboardService dashboardService;
    
    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        return ResponseEntity.ok(dashboardService.getDashboardStats());
    }
    
    @GetMapping("/alerts")
    public ResponseEntity<List<Notification>> getDashboardAlerts() {
        return ResponseEntity.ok(dashboardService.getRecentAlerts());
    }
    
    @PutMapping("/alerts/{alertId}/read")
    public ResponseEntity<Void> markAlertAsRead(@PathVariable String alertId) {
        dashboardService.markAlertAsRead(alertId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/chart")
    public ResponseEntity<List<ChartDataDTO>> getChartData(
            @RequestParam(defaultValue = "7d") String period) {
        return ResponseEntity.ok(dashboardService.getChartData(period));
    }
    
    @GetMapping("/realtime")
    public ResponseEntity<Map<String, Object>> getRealtimeStats() {
        return ResponseEntity.ok(dashboardService.getRealtimeStats());
    }
}