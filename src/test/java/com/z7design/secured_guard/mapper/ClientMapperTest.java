package com.z7design.secured_guard.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.z7design.secured_guard.dto.ClientDTO;
import com.z7design.secured_guard.model.Client;

class ClientMapperTest {

    private ClientMapper clientMapper;
    private Client client;
    private ClientDTO clientDTO;
    private UUID clientId;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        clientMapper = new ClientMapper();
        clientId = UUID.randomUUID();
        now = LocalDateTime.now();

        client = Client.builder()
                .id(clientId)
                .companyName("Test Company")
                .cnpj("12345678901234")
                .address("Test Address")
                .phone("1234567890")
                .whatsapp("1234567890")
                .email("test@company.com")
                .responsiblePerson("John Doe")
                .contact("Jane Doe")
                .createdAt(now)
                .updatedAt(now)
                .build();

        clientDTO = new ClientDTO();
        clientDTO.setId(clientId);
        clientDTO.setCompanyName("Test Company");
        clientDTO.setCnpj("12345678901234");
        clientDTO.setAddress("Test Address");
        clientDTO.setPhone("1234567890");
        clientDTO.setWhatsapp("1234567890");
        clientDTO.setEmail("test@company.com");
        clientDTO.setResponsiblePerson("John Doe");
        clientDTO.setContact("Jane Doe");
    }

    @Test
    void whenToDTO_thenReturnClientDTO() {
        ClientDTO result = clientMapper.toDTO(client);

        assertNotNull(result);
        assertEquals(clientId, result.getId());
        assertEquals("Test Company", result.getCompanyName());
        assertEquals("12345678901234", result.getCnpj());
        assertEquals("Test Address", result.getAddress());
        assertEquals("1234567890", result.getPhone());
        assertEquals("1234567890", result.getWhatsapp());
        assertEquals("test@company.com", result.getEmail());
        assertEquals("John Doe", result.getResponsiblePerson());
        assertEquals("Jane Doe", result.getContact());
    }

    @Test
    void whenToDTOWithNull_thenReturnNull() {
        ClientDTO result = clientMapper.toDTO(null);
        assertNull(result);
    }

    @Test
    void whenToEntity_thenReturnClient() {
        Client result = clientMapper.toEntity(clientDTO);

        assertNotNull(result);
        assertEquals(clientId, result.getId());
        assertEquals("Test Company", result.getCompanyName());
        assertEquals("12345678901234", result.getCnpj());
        assertEquals("Test Address", result.getAddress());
        assertEquals("1234567890", result.getPhone());
        assertEquals("1234567890", result.getWhatsapp());
        assertEquals("test@company.com", result.getEmail());
        assertEquals("John Doe", result.getResponsiblePerson());
        assertEquals("Jane Doe", result.getContact());
    }

    @Test
    void whenToEntityWithNull_thenReturnNull() {
        Client result = clientMapper.toEntity(null);
        assertNull(result);
    }
} 