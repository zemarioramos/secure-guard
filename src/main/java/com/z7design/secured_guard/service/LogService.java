package com.z7design.secured_guard.service;

import com.z7design.secured_guard.model.UserActivityLog;
import com.z7design.secured_guard.model.ErrorLog;
import com.z7design.secured_guard.repository.UserActivityLogRepository;
import com.z7design.secured_guard.repository.ErrorLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogService {

    private final UserActivityLogRepository userActivityLogRepository;
    private final ErrorLogRepository errorLogRepository;

    @Autowired
    public LogService(UserActivityLogRepository userActivityLogRepository, 
                     ErrorLogRepository errorLogRepository) {
        this.userActivityLogRepository = userActivityLogRepository;
        this.errorLogRepository = errorLogRepository;
    }

    @Transactional
    public void logUserActivity(String username, String action, String details) {
        UserActivityLog log = new UserActivityLog();
        log.setUsername(username);
        log.setAction(action);
        log.setDetails(details);
        userActivityLogRepository.save(log);
    }

    @Transactional
    public void logError(String message, String stackTrace, String endpoint) {
        ErrorLog log = new ErrorLog();
        log.setMessage(message);
        log.setStackTrace(stackTrace);
        log.setEndpoint(endpoint);
        errorLogRepository.save(log);
    }
} 