package com.z7design.secured_guard.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.z7design.secured_guard.model.Client;
import com.z7design.secured_guard.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {
    
    private final ClientRepository clientRepository;
    
    @Transactional
    public Client create(Client client) {
        if (clientRepository.existsByCnpj(client.getCnpj())) {
            throw new RuntimeException("CNPJ já cadastrado");
        }
        if (clientRepository.existsByCompanyName(client.getCompanyName())) {
            throw new RuntimeException("Razão Social já cadastrada");
        }
        return clientRepository.save(client);
    }
    
    @Transactional
    public Client update(UUID id, Client client) {
        Client existingClient = findById(id);
        
        if (!existingClient.getCnpj().equals(client.getCnpj()) && 
            clientRepository.existsByCnpj(client.getCnpj())) {
            throw new RuntimeException("CNPJ já cadastrado");
        }
        
        if (!existingClient.getCompanyName().equals(client.getCompanyName()) && 
            clientRepository.existsByCompanyName(client.getCompanyName())) {
            throw new RuntimeException("Razão Social já cadastrada");
        }
        
        client.setId(id);
        return clientRepository.save(client);
    }
    
    @Transactional
    public void delete(UUID id) {
        Client client = findById(id);
        clientRepository.delete(client);
    }
    
    public Client findById(UUID id) {
        return clientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }
    
    public Client findByCnpj(String cnpj) {
        return clientRepository.findByCnpj(cnpj)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }
    
    public Client findByCompanyName(String companyName) {
        return clientRepository.findByCompanyName(companyName)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }
    
    public List<Client> findAll() {
        return clientRepository.findAll();
    }
} 