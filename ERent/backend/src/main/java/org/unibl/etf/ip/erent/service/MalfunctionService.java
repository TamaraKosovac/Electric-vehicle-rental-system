package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.erent.dto.MalfunctionDTO;
import org.unibl.etf.ip.erent.model.Malfunction;
import org.unibl.etf.ip.erent.repository.MalfunctionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MalfunctionService {

    private final MalfunctionRepository malfunctionRepository;

    public List<Malfunction> findAll() {
        return malfunctionRepository.findAll();
    }

    public Malfunction findById(Long id) {
        return malfunctionRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Malfunction not found"));
    }

    public List<Malfunction> findByVehicle(Long vehicleId) {
        return malfunctionRepository.findByVehicleId(vehicleId);
    }

    public Malfunction save(Malfunction malfunction) {
        return malfunctionRepository.save(malfunction);
    }

    public void delete(Long id) {
        malfunctionRepository.deleteById(id);
    }

    public MalfunctionDTO findDtoById(Long id) {
        Malfunction malfunction = malfunctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Malfunction not found"));

        MalfunctionDTO dto = new MalfunctionDTO();
        dto.setId(malfunction.getId());
        dto.setDescription(malfunction.getDescription());
        dto.setDateTime(malfunction.getDateTime());
        dto.setVehicleId(malfunction.getVehicle().getId());

        return dto;
    }
}
