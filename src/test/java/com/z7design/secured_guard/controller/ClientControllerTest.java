package com.z7design.secured_guard.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.z7design.secured_guard.dto.ClientDTO;
import com.z7design.secured_guard.mapper.ClientMapper;
import com.z7design.secured_guard.model.Client;
import com.z7design.secured_guard.service.ClientService;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    @MockBean
    private ClientMapper clientMapper;

    private Client client;
    private ClientDTO clientDTO;
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

        clientDTO = new ClientDTO();
        clientDTO.setId(clientId);
        clientDTO.setCompanyName("Test Company");
        clientDTO.setCnpj("12345678901234");
        clientDTO.setAddress("Test Address");
        clientDTO.setPhone("1234567890");
        clientDTO.setWhatsapp("9876543210");
        clientDTO.setEmail("test@company.com");
        clientDTO.setResponsiblePerson("John Doe");
        clientDTO.setContact("Jane Doe");
        clientDTO.setCreatedAt(now);
        clientDTO.setUpdatedAt(now);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GESTOR"})
    void whenCreateClient_thenReturnCreatedClient() throws Exception {
        when(clientMapper.toEntity(any(ClientDTO.class))).thenReturn(client);
        when(clientService.create(any(Client.class))).thenReturn(client);
        when(clientMapper.toDTO(any(Client.class))).thenReturn(clientDTO);

        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(clientId.toString()))
                .andExpect(jsonPath("$.companyName").value("Test Company"));

        verify(clientMapper).toEntity(any(ClientDTO.class));
        verify(clientService).create(any(Client.class));
        verify(clientMapper).toDTO(any(Client.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GESTOR"})
    void whenUpdateClient_thenReturnUpdatedClient() throws Exception {
        when(clientMapper.toEntity(any(ClientDTO.class))).thenReturn(client);
        when(clientService.update(any(UUID.class), any(Client.class))).thenReturn(client);
        when(clientMapper.toDTO(any(Client.class))).thenReturn(clientDTO);

        mockMvc.perform(put("/api/clients/{id}", clientId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clientId.toString()))
                .andExpect(jsonPath("$.companyName").value("Test Company"));

        verify(clientMapper).toEntity(any(ClientDTO.class));
        verify(clientService).update(any(UUID.class), any(Client.class));
        verify(clientMapper).toDTO(any(Client.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenDeleteClient_thenReturnNoContent() throws Exception {
        doNothing().when(clientService).delete(any(UUID.class));

        mockMvc.perform(delete("/api/clients/{id}", clientId))
                .andExpect(status().isNoContent());

        verify(clientService).delete(clientId);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GESTOR", "SUPERVISOR"})
    void whenFindById_thenReturnClient() throws Exception {
        when(clientService.findById(any(UUID.class))).thenReturn(client);
        when(clientMapper.toDTO(any(Client.class))).thenReturn(clientDTO);

        mockMvc.perform(get("/api/clients/{id}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clientId.toString()))
                .andExpect(jsonPath("$.companyName").value("Test Company"));

        verify(clientService).findById(clientId);
        verify(clientMapper).toDTO(any(Client.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GESTOR", "SUPERVISOR"})
    void whenFindByCnpj_thenReturnClient() throws Exception {
        when(clientService.findByCnpj(anyString())).thenReturn(client);
        when(clientMapper.toDTO(any(Client.class))).thenReturn(clientDTO);

        mockMvc.perform(get("/api/clients/cnpj/{cnpj}", "12345678901234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clientId.toString()))
                .andExpect(jsonPath("$.cnpj").value("12345678901234"));

        verify(clientService).findByCnpj("12345678901234");
        verify(clientMapper).toDTO(any(Client.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GESTOR", "SUPERVISOR"})
    void whenFindByCompanyName_thenReturnClient() throws Exception {
        when(clientService.findByCompanyName(anyString())).thenReturn(client);
        when(clientMapper.toDTO(any(Client.class))).thenReturn(clientDTO);

        mockMvc.perform(get("/api/clients/company/{companyName}", "Test Company"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clientId.toString()))
                .andExpect(jsonPath("$.companyName").value("Test Company"));

        verify(clientService).findByCompanyName("Test Company");
        verify(clientMapper).toDTO(any(Client.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GESTOR", "SUPERVISOR"})
    void whenFindAll_thenReturnAllClients() throws Exception {
        List<Client> clients = Arrays.asList(client);
        List<ClientDTO> clientDTOs = Arrays.asList(clientDTO);

        when(clientService.findAll()).thenReturn(clients);
        when(clientMapper.toDTO(any(Client.class))).thenReturn(clientDTO);

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(clientId.toString()))
                .andExpect(jsonPath("$[0].companyName").value("Test Company"));

        verify(clientService).findAll();
        verify(clientMapper).toDTO(any(Client.class));
    }
} 