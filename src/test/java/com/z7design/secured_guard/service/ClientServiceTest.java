package com.z7design.secured_guard.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.z7design.secured_guard.exception.ResourceNotFoundException;
import com.z7design.secured_guard.model.Client;
import com.z7design.secured_guard.repository.ClientRepository;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

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
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client createdClient = clientService.create(client);

        assertNotNull(createdClient);
        assertEquals(clientId, createdClient.getId());
        assertEquals("Test Company", createdClient.getCompanyName());
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    void whenUpdateClient_thenClientIsUpdated() {
        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        client.setCompanyName("Updated Company");
        client.setCnpj("98765432109876");
        client.setAddress("Updated Address");
        client.setPhone("0987654321");
        client.setWhatsapp("1234567890");
        client.setEmail("updated@company.com");
        client.setResponsiblePerson("Jane Smith");
        client.setContact("John Smith");

        Client updatedClient = clientService.update(clientId, client);

        assertNotNull(updatedClient);
        assertEquals("Updated Company", updatedClient.getCompanyName());
        assertEquals("98765432109876", updatedClient.getCnpj());
        assertEquals("Updated Address", updatedClient.getAddress());
        assertEquals("0987654321", updatedClient.getPhone());
        assertEquals("1234567890", updatedClient.getWhatsapp());
        assertEquals("updated@company.com", updatedClient.getEmail());
        assertEquals("Jane Smith", updatedClient.getResponsiblePerson());
        assertEquals("John Smith", updatedClient.getContact());
        verify(clientRepository).findById(clientId);
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    void whenUpdateNonExistentClient_thenThrowException() {
        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            clientService.update(clientId, client);
        });

        verify(clientRepository).findById(clientId);
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void whenDeleteClient_thenClientIsDeleted() {
        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));
        doNothing().when(clientRepository).delete(any(Client.class));

        clientService.delete(clientId);

        verify(clientRepository).findById(clientId);
        verify(clientRepository).delete(client);
    }

    @Test
    void whenDeleteNonExistentClient_thenThrowException() {
        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            clientService.delete(clientId);
        });

        verify(clientRepository).findById(clientId);
        verify(clientRepository, never()).delete(any(Client.class));
    }

    @Test
    void whenFindById_thenReturnClient() {
        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.of(client));

        Client foundClient = clientService.findById(clientId);

        assertNotNull(foundClient);
        assertEquals(clientId, foundClient.getId());
        verify(clientRepository).findById(clientId);
    }

    @Test
    void whenFindNonExistentClientById_thenThrowException() {
        when(clientRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            clientService.findById(clientId);
        });

        verify(clientRepository).findById(clientId);
    }

    @Test
    void whenFindByCnpj_thenReturnClient() {
        when(clientRepository.findByCnpj(anyString())).thenReturn(Optional.of(client));

        Client foundClient = clientService.findByCnpj("12345678901234");

        assertNotNull(foundClient);
        assertEquals("12345678901234", foundClient.getCnpj());
        verify(clientRepository).findByCnpj("12345678901234");
    }

    @Test
    void whenFindNonExistentClientByCnpj_thenThrowException() {
        when(clientRepository.findByCnpj(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            clientService.findByCnpj("12345678901234");
        });

        verify(clientRepository).findByCnpj("12345678901234");
    }

    @Test
    void whenFindByCompanyName_thenReturnClient() {
        when(clientRepository.findByCompanyName(anyString())).thenReturn(Optional.of(client));

        Client foundClient = clientService.findByCompanyName("Test Company");

        assertNotNull(foundClient);
        assertEquals("Test Company", foundClient.getCompanyName());
        verify(clientRepository).findByCompanyName("Test Company");
    }

    @Test
    void whenFindNonExistentClientByCompanyName_thenThrowException() {
        when(clientRepository.findByCompanyName(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            clientService.findByCompanyName("Test Company");
        });

        verify(clientRepository).findByCompanyName("Test Company");
    }

    @Test
    void whenFindAll_thenReturnAllClients() {
        List<Client> clients = Arrays.asList(client);
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> foundClients = clientService.findAll();

        assertNotNull(foundClients);
        assertEquals(1, foundClients.size());
        verify(clientRepository).findAll();
    }
} 