package com.z7design.secured_guard.mapper;

import org.springframework.stereotype.Component;

import com.z7design.secured_guard.dto.ClientDTO;
import com.z7design.secured_guard.model.Client;

@Component
public class ClientMapper {

    public ClientDTO toDTO(Client client) {
        if (client == null) {
            return null;
        }

        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setCompanyName(client.getCompanyName());
        dto.setCnpj(client.getCnpj());
        dto.setAddress(client.getAddress());
        dto.setPhone(client.getPhone());
        dto.setWhatsapp(client.getWhatsapp());
        dto.setEmail(client.getEmail());
        dto.setResponsiblePerson(client.getResponsiblePerson());
        dto.setContact(client.getContact());

        return dto;
    }

    public Client toEntity(ClientDTO dto) {
        if (dto == null) {
            return null;
        }

        Client client = new Client();
        client.setId(dto.getId());
        client.setCompanyName(dto.getCompanyName());
        client.setCnpj(dto.getCnpj());
        client.setAddress(dto.getAddress());
        client.setPhone(dto.getPhone());
        client.setWhatsapp(dto.getWhatsapp());
        client.setEmail(dto.getEmail());
        client.setResponsiblePerson(dto.getResponsiblePerson());
        client.setContact(dto.getContact());

        return client;
    }
} 