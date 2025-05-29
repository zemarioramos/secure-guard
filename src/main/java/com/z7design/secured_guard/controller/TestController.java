package com.z7design.secured_guard.controller;

import com.z7design.secured_guard.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final LogService logService;

    @Autowired
    public TestController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/activity")
    public ResponseEntity<String> performAction(HttpServletRequest request) {
        String username = request.getUserPrincipal() != null
            ? request.getUserPrincipal().getName()
            : "ANONYMOUS";

        logService.logUserActivity(username, "ACCESS", "Accessed the /activity endpoint");

        return ResponseEntity.ok("Activity logged successfully!");
    }

    @GetMapping("/error")
    public ResponseEntity<String> generateError() {
        throw new RuntimeException("Simulated error for logging purposes");
    }
} 