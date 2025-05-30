package com.z7design.secured_guard.service;

import com.z7design.secured_guard.model.UserActivityLog;
import com.z7design.secured_guard.model.ErrorLog;
import com.z7design.secured_guard.repository.UserActivityLogRepository;
import com.z7design.secured_guard.repository.ErrorLogRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LogAnalyticsService {

    private final UserActivityLogRepository userActivityLogRepository;
    private final ErrorLogRepository errorLogRepository;

    @Autowired
    public LogAnalyticsService(UserActivityLogRepository userActivityLogRepository,
                             ErrorLogRepository errorLogRepository) {
        this.userActivityLogRepository = userActivityLogRepository;
        this.errorLogRepository = errorLogRepository;
    }

    // Métodos de agregação
    @Cacheable(value = "activityCountByAction")
    public Map<String, Long> getActivityCountByAction() {
        return userActivityLogRepository.findAll().stream()
            .collect(Collectors.groupingBy(
                UserActivityLog::getAction,
                Collectors.counting()
            ));
    }

    @Cacheable(value = "errorCountByEndpoint")
    public Map<String, Long> getErrorCountByEndpoint() {
        return errorLogRepository.findAll().stream()
            .collect(Collectors.groupingBy(
                ErrorLog::getEndpoint,
                Collectors.counting()
            ));
    }

    @Cacheable(value = "activityCountByUser")
    public Map<String, Long> getActivityCountByUser() {
        return userActivityLogRepository.findAll().stream()
            .collect(Collectors.groupingBy(
                UserActivityLog::getUsername,
                Collectors.counting()
            ));
    }

    // Métodos de estatísticas avançadas
    @Cacheable(value = "advancedActivityStats")
    public Map<String, Object> getAdvancedActivityStats() {
        List<UserActivityLog> allLogs = userActivityLogRepository.findAll();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalCount", allLogs.size());
        
        // Média de atividades por hora
        Map<Integer, Long> activitiesByHour = allLogs.stream()
            .collect(Collectors.groupingBy(
                log -> log.getTimestamp().getHour(),
                Collectors.counting()
            ));
        stats.put("activitiesByHour", activitiesByHour);
        
        // Top 5 ações mais comuns
        Map<String, Long> topActions = allLogs.stream()
            .collect(Collectors.groupingBy(
                UserActivityLog::getAction,
                Collectors.counting()
            ))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
        stats.put("topActions", topActions);
        
        return stats;
    }

    @Cacheable(value = "advancedErrorStats")
    public Map<String, Object> getAdvancedErrorStats() {
        List<ErrorLog> allErrors = errorLogRepository.findAll();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalCount", allErrors.size());
        
        // Erros por endpoint
        Map<String, Long> errorsByEndpoint = allErrors.stream()
            .collect(Collectors.groupingBy(
                ErrorLog::getEndpoint,
                Collectors.counting()
            ));
        stats.put("errorsByEndpoint", errorsByEndpoint);
        
        // Erros por hora do dia
        Map<Integer, Long> errorsByHour = allErrors.stream()
            .collect(Collectors.groupingBy(
                log -> log.getTimestamp().getHour(),
                Collectors.counting()
            ));
        stats.put("errorsByHour", errorsByHour);
        
        return stats;
    }

    // Métodos de exportação
    public String exportActivitiesToCSV() {
        List<UserActivityLog> logs = userActivityLogRepository.findAll();
        StringBuilder csv = new StringBuilder();
        
        // Cabeçalho
        csv.append("ID,Username,Action,Details,Timestamp\n");
        
        // Dados
        logs.forEach(log -> csv.append(String.format("%d,%s,%s,%s,%s\n",
            log.getId(),
            log.getUsername(),
            log.getAction(),
            log.getDetails().replace(",", ";"),
            log.getTimestamp()
        )));
        
        return csv.toString();
    }

    public String exportErrorsToCSV() {
        List<ErrorLog> logs = errorLogRepository.findAll();
        StringBuilder csv = new StringBuilder();
        
        // Cabeçalho
        csv.append("ID,Message,Endpoint,StackTrace,Timestamp\n");
        
        // Dados
        logs.forEach(log -> csv.append(String.format("%s,%s,%s,%s,%s\n",
            log.getId().toString(),
            log.getMessage().replace(",", ";"),
            log.getEndpoint(),
            log.getStackTrace().replace(",", ";"),
            log.getTimestamp()
        )));
        
        return csv.toString();
    }

    public String exportActivitiesToJSON() {
        List<UserActivityLog> logs = userActivityLogRepository.findAll();
        StringBuilder json = new StringBuilder();
        json.append("{\"activities\":[");
        
        for (int i = 0; i < logs.size(); i++) {
            UserActivityLog log = logs.get(i);
            json.append(String.format(
                "{\"id\":%d,\"username\":\"%s\",\"action\":\"%s\",\"details\":\"%s\",\"timestamp\":\"%s\"}",
                log.getId(), log.getUsername(), log.getAction(),
                log.getDetails(), log.getTimestamp()));
            
            if (i < logs.size() - 1) {
                json.append(",");
            }
        }
        
        json.append("]}");
        return json.toString();
    }

    public byte[] exportActivitiesToExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Activities");
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Username", "Action", "Details", "Timestamp"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }
            
            // Create data rows
            List<UserActivityLog> logs = userActivityLogRepository.findAll();
            for (int i = 0; i < logs.size(); i++) {
                UserActivityLog log = logs.get(i);
                Row row = sheet.createRow(i + 1);
                
                row.createCell(0).setCellValue(log.getId());
                row.createCell(1).setCellValue(log.getUsername());
                row.createCell(2).setCellValue(log.getAction());
                row.createCell(3).setCellValue(log.getDetails());
                row.createCell(4).setCellValue(log.getTimestamp().toString());
            }
            
            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error exporting to Excel", e);
        }
    }

    public String exportErrorsToJSON() {
        List<ErrorLog> logs = errorLogRepository.findAll();
        StringBuilder json = new StringBuilder();
        json.append("{\"errors\":[");
        
        for (int i = 0; i < logs.size(); i++) {
            ErrorLog log = logs.get(i);
            json.append(String.format(
                "{\"id\":\"%s\",\"message\":\"%s\",\"endpoint\":\"%s\",\"stackTrace\":\"%s\",\"timestamp\":\"%s\"}",
                log.getId().toString(), log.getMessage(), log.getEndpoint(),
                log.getStackTrace(), log.getTimestamp()));
            
            if (i < logs.size() - 1) {
                json.append(",");
            }
        }
        
        json.append("]}");
        return json.toString();
    }

    public byte[] exportErrorsToExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Errors");
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Message", "Endpoint", "StackTrace", "Timestamp"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }
            
            // Create data rows
            List<ErrorLog> logs = errorLogRepository.findAll();
            for (int i = 0; i < logs.size(); i++) {
                ErrorLog log = logs.get(i);
                Row row = sheet.createRow(i + 1);
                
                row.createCell(0).setCellValue(log.getId().toString());
                row.createCell(1).setCellValue(log.getMessage());
                row.createCell(2).setCellValue(log.getEndpoint());
                row.createCell(3).setCellValue(log.getStackTrace());
                row.createCell(4).setCellValue(log.getTimestamp().toString());
            }
            
            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error exporting to Excel", e);
        }
    }

    // Métodos de filtro avançado
    public List<UserActivityLog> getActivitiesWithAdvancedFilters(
            String username, String action, LocalDateTime startDate,
            LocalDateTime endDate, String details, Long minId, Long maxId) {
        return userActivityLogRepository.findAll().stream()
            .filter(log -> username == null || log.getUsername().equals(username))
            .filter(log -> action == null || log.getAction().equals(action))
            .filter(log -> startDate == null || !log.getTimestamp().isBefore(startDate))
            .filter(log -> endDate == null || !log.getTimestamp().isAfter(endDate))
            .filter(log -> details == null || log.getDetails().contains(details))
            .filter(log -> minId == null || log.getId() >= minId)
            .filter(log -> maxId == null || log.getId() <= maxId)
            .collect(Collectors.toList());
    }

    public List<ErrorLog> getErrorsWithAdvancedFilters(
            String endpoint, LocalDateTime startDate, LocalDateTime endDate,
            String message) {
        return errorLogRepository.findAll().stream()
            .filter(log -> endpoint == null || log.getEndpoint().equals(endpoint))
            .filter(log -> startDate == null || !log.getTimestamp().isBefore(startDate))
            .filter(log -> endDate == null || !log.getTimestamp().isAfter(endDate))
            .filter(log -> message == null || log.getMessage().contains(message))
            .collect(Collectors.toList());
    }
} 