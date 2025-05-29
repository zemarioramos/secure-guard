package com.z7design.secured_guard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UnidadeTest {

    private Unidade unidade;

    @BeforeEach
    void setUp() {
        unidade = new Unidade();
        unidade.setId(1L);
        unidade.setNome("Test Unidade");
        unidade.setEndereco("Test Address");
        unidade.setTelefone("123456789");
        unidade.setEmail("test@example.com");
    }

    @Test
    void whenCreateUnidade_thenUnidadeIsCreated() {
        assertNotNull(unidade);
        assertEquals(1L, unidade.getId());
        assertEquals("Test Unidade", unidade.getNome());
        assertEquals("Test Address", unidade.getEndereco());
        assertEquals("123456789", unidade.getTelefone());
        assertEquals("test@example.com", unidade.getEmail());
    }

    @Test
    void whenUpdateNome_thenNomeIsUpdated() {
        unidade.setNome("Updated Unidade");
        assertEquals("Updated Unidade", unidade.getNome());
    }

    @Test
    void whenUpdateEndereco_thenEnderecoIsUpdated() {
        unidade.setEndereco("Updated Address");
        assertEquals("Updated Address", unidade.getEndereco());
    }
} 