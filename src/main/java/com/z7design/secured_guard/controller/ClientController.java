package com.z7design.secured_guard.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.z7design.secured_guard.dto.ClientDTO;
import com.z7design.secured_guard.mapper.ClientMapper;
import com.z7design.secured_guard.model.Client;
import com.z7design.secured_guard.service.ClientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        Client createdClient = clientService.create(client);
        return ResponseEntity.ok(clientMapper.toDTO(createdClient));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR')")
    public ResponseEntity<ClientDTO> update(@PathVariable UUID id, @RequestBody ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        Client updatedClient = clientService.update(id, client);
        return ResponseEntity.ok(clientMapper.toDTO(updatedClient));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<ClientDTO> findById(@PathVariable UUID id) {
        Client client = clientService.findById(id);
        return ResponseEntity.ok(clientMapper.toDTO(client));
    }

    @GetMapping("/cnpj/{cnpj}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<ClientDTO> findByCnpj(@PathVariable String cnpj) {
        Client client = clientService.findByCnpj(cnpj);
        return ResponseEntity.ok(clientMapper.toDTO(client));
    }

    @GetMapping("/company/{companyName}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<ClientDTO> findByCompanyName(@PathVariable String companyName) {
        Client client = clientService.findByCompanyName(companyName);
        return ResponseEntity.ok(clientMapper.toDTO(client));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTOR') or hasRole('SUPERVISOR')")
    public ResponseEntity<List<ClientDTO>> findAll() {
        List<Client> clients = clientService.findAll();
        List<ClientDTO> clientDTOs = clients.stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientDTOs);
    }
} 