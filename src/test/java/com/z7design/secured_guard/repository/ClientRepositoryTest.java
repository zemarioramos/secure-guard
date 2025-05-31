package com.z7design.secured_guard.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.z7design.secured_guard.model.Client;

@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

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
        client.setWhatsapp("0987654321");
        client.setEmail("test@company.com");
        client.setResponsiblePerson("John Doe");
        client.setContact("Jane Doe");
        client.setCreatedAt(now);
        client.setUpdatedAt(now);

        entityManager.persist(client);
        entityManager.flush();
    }

    @Test
    void whenFindById_thenReturnClient() {
        Optional<Client> found = clientRepository.findById(clientId);

        assertTrue(found.isPresent());
        assertEquals(clientId, found.get().getId());
        assertEquals("Test Company", found.get().getCompanyName());
    }

    @Test
    void whenFindByCnpj_thenReturnClient() {
        Optional<Client> found = clientRepository.findByCnpj("12345678901234");

        assertTrue(found.isPresent());
        assertEquals("12345678901234", found.get().getCnpj());
    }

    @Test
    void whenFindByCompanyName_thenReturnClient() {
        Optional<Client> found = clientRepository.findByCompanyName("Test Company");

        assertTrue(found.isPresent());
        assertEquals("Test Company", found.get().getCompanyName());
    }

    @Test
    void whenFindAll_thenReturnAllClients() {
        List<Client> found = clientRepository.findAll();

        assertFalse(found.isEmpty());
        assertEquals(1, found.size());
    }

    @Test
    void whenSave_thenClientIsSaved() {
        Client newClient = new Client();
        newClient.setCompanyName("New Company");
        newClient.setCnpj("98765432109876");
        newClient.setAddress("New Address");
        newClient.setPhone("9876543210");
        newClient.setWhatsapp("0123456789");
        newClient.setEmail("new@company.com");
        newClient.setResponsiblePerson("New Person");
        newClient.setContact("New Contact");

        Client saved = clientRepository.save(newClient);

        assertNotNull(saved.getId());
        assertEquals("New Company", saved.getCompanyName());
    }

    @Test
    void whenDelete_thenClientIsDeleted() {
        clientRepository.delete(client);

        Optional<Client> found = clientRepository.findById(clientId);
        assertFalse(found.isPresent());
    }
} 