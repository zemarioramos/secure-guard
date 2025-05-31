package com.z7design.secured_guard.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.z7design.secured_guard.dto.EmpresaConfigDTO;
import com.z7design.secured_guard.model.EmpresaConfig;
import com.z7design.secured_guard.repository.EmpresaConfigRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvcServlet;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ConfiguracaoIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmpresaConfigRepository empresaConfigRepository;

    private MockMvc mockMvc;

    @Test
    void testGetEmpresaConfig() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Criar configuração de teste
        EmpresaConfig config = EmpresaConfig.builder()
                .nome("Empresa Teste")
                .cnpj("12.345.678/0001-90")
                .endereco("Rua Teste, 123")
                .email("teste@empresa.com")
                .build();
        empresaConfigRepository.save(config);

        mockMvc.perform(get("/api/configuracoes/empresa")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Empresa Teste"))
                .andExpect(jsonPath("$.cnpj").value("12.345.678/0001-90"));
    }

    @Test
    void testUpdateEmpresaConfig() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        EmpresaConfigDTO configDTO = EmpresaConfigDTO.builder()
                .nome("Empresa Atualizada")
                .cnpj("98.765.432/0001-10")
                .endereco("Nova Rua, 456")
                .email("novo@empresa.com")
                .build();

        mockMvc.perform(put("/api/configuracoes/empresa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(configDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Empresa Atualizada"));
    }

    @Test
    void testUploadLogo() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "logo.png",
                "image/png",
                "fake image content".getBytes()
        );

        mockMvc.perform(multipart("/api/configuracoes/empresa/logo")
                .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Logo uploaded successfully"))
                .andExpect(jsonPath("$.logoPath").exists());
    }
}