package com.z7design.secured_guard.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.z7design.secured_guard.dto.FilialDTO;
import com.z7design.secured_guard.model.Unit;
import com.z7design.secured_guard.repository.UnitRepository;
import com.z7design.secured_guard.repository.EmployeeRepository;

@Service
public class FilialService {
    
    @Autowired
    private UnitRepository unitRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    public List<FilialDTO> getAllFiliais() {
        return unitRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public Page<FilialDTO> getFiliaisPaginated(Pageable pageable) {
        return unitRepository.findAll(pageable)
            .map(this::convertToDTO);
    }
    
    public FilialDTO getFilialById(UUID id) {
        Unit unit = unitRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Filial not found"));
        return convertToDTO(unit);
    }
    
    public FilialDTO createFilial(FilialDTO filialDTO) {
        Unit unit = Unit.builder()
            .name(filialDTO.getNome())
            .address(filialDTO.getEndereco())
            .phone(filialDTO.getTelefone())
            .description("Responsável: " + filialDTO.getResponsavel())
            .active(filialDTO.getAtiva() != null ? filialDTO.getAtiva() : true)
            .build();
        
        Unit savedUnit = unitRepository.save(unit);
        return convertToDTO(savedUnit);
    }
    
    public FilialDTO updateFilial(UUID id, FilialDTO filialDTO) {
        Unit unit = unitRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Filial not found"));
        
        unit.setName(filialDTO.getNome());
        unit.setAddress(filialDTO.getEndereco());
        unit.setPhone(filialDTO.getTelefone());
        unit.setDescription("Responsável: " + filialDTO.getResponsavel());
        unit.setActive(filialDTO.getAtiva());
        
        Unit updatedUnit = unitRepository.save(unit);
        return convertToDTO(updatedUnit);
    }
    
    public void deleteFilial(UUID id) {
        if (!unitRepository.existsById(id)) {
            throw new RuntimeException("Filial not found");
        }
        unitRepository.deleteById(id);
    }
    
    private FilialDTO convertToDTO(Unit unit) {
        String responsavel = "";
        if (unit.getDescription() != null && unit.getDescription().startsWith("Responsável: ")) {
            responsavel = unit.getDescription().substring("Responsável: ".length());
        }
        
        Integer funcionarios = employeeRepository.countByUnitId(unit.getId());
        
        return FilialDTO.builder()
            .id(unit.getId())
            .nome(unit.getName())
            .endereco(unit.getAddress())
            .telefone(unit.getPhone())
            .responsavel(responsavel)
            .funcionarios(funcionarios)
            .ativa(unit.getActive())
            .createdAt(unit.getCreatedAt())
            .updatedAt(unit.getUpdatedAt())
            .build();
    }
}