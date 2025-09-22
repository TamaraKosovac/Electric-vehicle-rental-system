package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.erent.dto.ChartDataDTO;
import org.unibl.etf.ip.erent.dto.MalfunctionDTO;
import org.unibl.etf.ip.erent.model.Malfunction;
import org.unibl.etf.ip.erent.repository.MalfunctionRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
        if (malfunction.getDescription() == null || malfunction.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Malfunction description cannot be empty");
        }

        if (malfunction.getDateTime() == null) {
            malfunction.setDateTime(LocalDateTime.now());
        }

        if (malfunction.getVehicle() == null || malfunction.getVehicle().getId() == null) {
            throw new IllegalArgumentException("Malfunction must be linked to a valid vehicle");
        }

        Malfunction saved = malfunctionRepository.save(malfunction);
        return toDto(saved);
    }

    public void delete(Long id) {
        if (!malfunctionRepository.existsById(id)) {
            throw new RuntimeException("Malfunction not found");
        }
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

        if (malfunction.getVehicle() != null) {
            dto.setVehicleId(malfunction.getVehicle().getId());
            dto.setVehicleModel(malfunction.getVehicle().getModel());

            if (malfunction.getVehicle().getManufacturer() != null) {
                dto.setManufacturerName(malfunction.getVehicle().getManufacturer().getName());
            }
        }

        return dto;
    }

    public List<MalfunctionDTO> findAllDtos() {
        return malfunctionRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<ChartDataDTO> countByVehicleType() {
        List<Malfunction> malfunctions = malfunctionRepository.findAll();

        Map<String, Long> grouped = malfunctions.stream()
                .filter(m -> m.getVehicle() != null)
                .collect(Collectors.groupingBy(
                        m -> m.getVehicle().getClass().getSimpleName(),
                        Collectors.counting()
                ));

        return grouped.entrySet().stream()
                .map(e -> new ChartDataDTO(e.getKey(), e.getValue()))
                .toList();
    }
}