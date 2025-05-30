package com.z7design.secured_guard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.z7design.secured_guard.config.JwtConfig;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class SecuredGuardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecuredGuardApplication.class, args);
    }
} 