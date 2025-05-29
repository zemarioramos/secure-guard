package com.z7design.secured_guard.controller;

import com.z7design.secured_guard.dto.EscalaFuncionarioDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EscalaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateEscalaFuncionario() throws Exception {
        EscalaFuncionarioDTO dto = new EscalaFuncionarioDTO();
        dto.setFuncionarioId(UUID.randomUUID());
        dto.setPositionId(UUID.randomUUID());
        dto.setObservacao("Test observation");

        mockMvc.perform(post("/api/escalas/funcionarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"funcionarioId\":\"" + dto.getFuncionarioId() + "\",\"positionId\":\"" + dto.getPositionId() + "\",\"observacao\":\"" + dto.getObservacao() + "\"}"))
                .andExpect(status().isOk());
    }
} 