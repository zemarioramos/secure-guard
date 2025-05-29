package com.z7design.secured_guard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.z7design.secured_guard.dto.EscalaDTO;
import com.z7design.secured_guard.dto.EscalaFuncionarioDTO;
import com.z7design.secured_guard.model.*;
import com.z7design.secured_guard.model.enums.StatusEscala;
import com.z7design.secured_guard.model.enums.Turno;
import com.z7design.secured_guard.service.EscalaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EscalaController.class)
class EscalaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EscalaService escalaService;

    @Test
    void criarEscala_DeveRetornarEscalaCriada() throws Exception {
        // Arrange
        EscalaDTO dto = new EscalaDTO();
        dto.setData(LocalDate.now());
        dto.setTurno(Turno.MANHA);
        dto.setStatus(StatusEscala.EM_SERVICO);

        Escala escala = new Escala();
        escala.setId(UUID.randomUUID());
        escala.setData(dto.getData());
        escala.setTurno(dto.getTurno());
        escala.setStatus(dto.getStatus());

        when(escalaService.criarEscala(any(EscalaDTO.class))).thenReturn(escala);

        // Act & Assert
        mockMvc.perform(post("/api/escalas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.turno").value(dto.getTurno().name()))
                .andExpect(jsonPath("$.status").value(dto.getStatus().name()));
    }

    @Test
    void adicionarFuncionario_DeveRetornarFuncionarioAdicionado() throws Exception {
        // Arrange
        UUID escalaId = UUID.randomUUID();
        EscalaFuncionarioDTO dto = new EscalaFuncionarioDTO();
        dto.setFuncionarioId(UUID.randomUUID());
        dto.setCargoId(UUID.randomUUID());

        EscalaFuncionario escalaFuncionario = new EscalaFuncionario();
        escalaFuncionario.setId(UUID.randomUUID());
        escalaFuncionario.setOrdemVisual(1);

        when(escalaService.adicionarFuncionario(any(EscalaFuncionarioDTO.class))).thenReturn(escalaFuncionario);

        // Act & Assert
        mockMvc.perform(post("/api/escalas/{escalaId}/funcionarios", escalaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.ordemVisual").value(1));
    }

    @Test
    void atualizarOrdemFuncionarios_DeveRetornarSucesso() throws Exception {
        // Arrange
        UUID escalaId = UUID.randomUUID();
        List<UUID> funcionarioIds = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());

        // Act & Assert
        mockMvc.perform(put("/api/escalas/{escalaId}/funcionarios/ordem", escalaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funcionarioIds)))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorPeriodo_DeveRetornarListaDeEscalas() throws Exception {
        // Arrange
        LocalDate dataInicio = LocalDate.now();
        LocalDate dataFim = dataInicio.plusDays(7);
        List<Escala> escalas = Arrays.asList(new Escala(), new Escala());

        when(escalaService.buscarPorPeriodo(dataInicio, dataFim)).thenReturn(escalas);

        // Act & Assert
        mockMvc.perform(get("/api/escalas/periodo")
                .param("dataInicio", dataInicio.toString())
                .param("dataFim", dataFim.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void buscarPorUnidadeEPeriodo_DeveRetornarListaDeEscalas() throws Exception {
        // Arrange
        UUID unidadeId = UUID.randomUUID();
        LocalDate dataInicio = LocalDate.now();
        LocalDate dataFim = dataInicio.plusDays(7);
        List<Escala> escalas = Arrays.asList(new Escala(), new Escala());

        when(escalaService.buscarPorUnidadeEPeriodo(unidadeId, dataInicio, dataFim)).thenReturn(escalas);

        // Act & Assert
        mockMvc.perform(get("/api/escalas/unidade/{unidadeId}/periodo", unidadeId)
                .param("dataInicio", dataInicio.toString())
                .param("dataFim", dataFim.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void buscarFuncionariosPorEscala_DeveRetornarListaDeFuncionarios() throws Exception {
        // Arrange
        UUID escalaId = UUID.randomUUID();
        List<EscalaFuncionario> funcionarios = Arrays.asList(
            new EscalaFuncionario(),
            new EscalaFuncionario()
        );

        when(escalaService.buscarFuncionariosPorEscala(escalaId)).thenReturn(funcionarios);

        // Act & Assert
        mockMvc.perform(get("/api/escalas/{escalaId}/funcionarios", escalaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }
} 