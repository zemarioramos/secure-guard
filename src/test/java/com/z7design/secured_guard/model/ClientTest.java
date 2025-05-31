package com.z7design.secured_guard.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientTest {

    private Client client;
    private UUID clientId;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        clientId = UUID.randomUUID();
        now = LocalDateTime.now();

        client = new Client();
        client.setId(clientId);
        client.setCompanyName("Test Company");
        client.setCnpj("12345678901234");
        client.setAddress("Test Address");
        client.setPhone("1234567890");
        client.setWhatsapp("9876543210");
        client.setEmail("test@company.com");
        client.setResponsiblePerson("John Doe");
        client.setContact("Jane Doe");
    }

    @Test
    void whenCreateClient_thenClientIsCreated() {
        assertNotNull(client);
        assertEquals(clientId, client.getId());
        assertEquals("Test Company", client.getCompanyName());
        assertEquals("12345678901234", client.getCnpj());
        assertEquals("Test Address", client.getAddress());
        assertEquals("1234567890", client.getPhone());
        assertEquals("9876543210", client.getWhatsapp());
        assertEquals("test@company.com", client.getEmail());
        assertEquals("John Doe", client.getResponsiblePerson());
        assertEquals("Jane Doe", client.getContact());
    }

    @Test
    void whenUpdateClient_thenClientIsUpdated() {
        client.setCompanyName("Updated Company");
        client.setCnpj("98765432109876");
        client.setAddress("Updated Address");
        client.setPhone("0987654321");
        client.setWhatsapp("1234567890");
        client.setEmail("updated@company.com");
        client.setResponsiblePerson("Jane Smith");
        client.setContact("John Smith");

        assertEquals("Updated Company", client.getCompanyName());
        assertEquals("98765432109876", client.getCnpj());
        assertEquals("Updated Address", client.getAddress());
        assertEquals("0987654321", client.getPhone());
        assertEquals("1234567890", client.getWhatsapp());
        assertEquals("updated@company.com", client.getEmail());
        assertEquals("Jane Smith", client.getResponsiblePerson());
        assertEquals("John Smith", client.getContact());
    }

    @Test
    void whenSetTimestamps_thenTimestampsAreSet() {
        LocalDateTime createdAt = now.minusDays(1);
        LocalDateTime updatedAt = now;

        client.setCreatedAt(createdAt);
        client.setUpdatedAt(updatedAt);

        assertEquals(createdAt, client.getCreatedAt());
        assertEquals(updatedAt, client.getUpdatedAt());
    }

    @Test
    void whenSetId_thenIdIsSet() {
        UUID newId = UUID.randomUUID();
        client.setId(newId);
        assertEquals(newId, client.getId());
    }
} 