package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.erent.dto.MalfunctionDTO;
import org.unibl.etf.ip.erent.model.Malfunction;
import org.unibl.etf.ip.erent.repository.MalfunctionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MalfunctionService {

    private final MalfunctionRepository malfunctionRepository;

    public List<Malfunction> findAll() {
        return malfunctionRepository.findAll();
    }

    public Malfunction findById(Long id) {
        return malfunctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Malfunction not found"));
    }

    public List<Malfunction> findByVehicle(Long vehicleId) {
        return malfunctionRepository.findByVehicleId(vehicleId);
    }

    public MalfunctionDTO save(Malfunction malfunction) {
        Malfunction saved = malfunctionRepository.save(malfunction);
        return toDto(saved);
    }

    public void delete(Long id) {
        malfunctionRepository.deleteById(id);
    }

    public MalfunctionDTO findDtoById(Long id) {
        Malfunction malfunction = malfunctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Malfunction not found"));
        return toDto(malfunction);
    }

    private MalfunctionDTO toDto(Malfunction malfunction) {
        MalfunctionDTO dto = new MalfunctionDTO();
        dto.setId(malfunction.getId());
        dto.setDescription(malfunction.getDescription());
        dto.setDateTime(malfunction.getDateTime());
        dto.setVehicleId(malfunction.getVehicle().getId());
        return dto;
    }

    public List<MalfunctionDTO> findAllDtos() {
        return malfunctionRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
