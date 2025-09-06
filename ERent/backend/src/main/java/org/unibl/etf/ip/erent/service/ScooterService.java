package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.erent.dto.MalfunctionDTO;
import org.unibl.etf.ip.erent.dto.ScooterDTO;
import org.unibl.etf.ip.erent.dto.ScooterDetailsDTO;
import org.unibl.etf.ip.erent.model.Scooter;
import org.unibl.etf.ip.erent.repository.ScooterRepository;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScooterService {

    private final ScooterRepository scooterRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public List<Scooter> findAll() {
        return scooterRepository.findAll();
    }

    public List<ScooterDTO> findAllDto() {
        return scooterRepository.findAll()
                .stream()
                .map(scooter -> new ScooterDTO(
                        scooter.getId(),
                        scooter.getUniqueId(),
                        scooter.getModel(),
                        scooter.getManufacturer().getName(),
                        scooter.getMaxSpeed(),
                        scooter.getPurchasePrice(),
                        scooter.getImagePath(),
                        !scooter.getMalfunctions().isEmpty(),
                        scooter.isRented(),
                        scooter.getCurrentLatitude(),
                        scooter.getCurrentLongitude()
                ))
                .toList();
    }

    public Scooter findById(Long id) {
        return scooterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scooter not found"));
    }

    public Scooter save(Scooter scooter, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            scooter.setImagePath("/" + fileName);
        }
        if (scooter.getCurrentLatitude() == null || scooter.getCurrentLongitude() == null) {
            scooter.setCurrentLatitude(44.7722);
            scooter.setCurrentLongitude(17.1910);
        }
        return scooterRepository.save(scooter);
    }

    public void delete(Long id) {
        scooterRepository.deleteById(id);
    }

    public ScooterDetailsDTO findDetailsById(Long id) {
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scooter not found"));

        ScooterDetailsDTO dto = new ScooterDetailsDTO();
        dto.setUniqueId(scooter.getUniqueId());
        dto.setManufacturer(scooter.getManufacturer());
        dto.setModel(scooter.getModel());
        dto.setPurchasePrice(scooter.getPurchasePrice());
        dto.setImagePath(scooter.getImagePath());
        dto.setRented(scooter.isRented());
        dto.setMaxSpeed(scooter.getMaxSpeed());
        dto.setCurrentLatitude(scooter.getCurrentLatitude());
        dto.setCurrentLongitude(scooter.getCurrentLongitude());

        dto.setMalfunctions(
                scooter.getMalfunctions().stream()
                        .map(m -> {
                            MalfunctionDTO mDto = new MalfunctionDTO();
                            mDto.setId(m.getId());
                            mDto.setDescription(m.getDescription());
                            mDto.setDateTime(m.getDateTime());
                            mDto.setVehicleId(scooter.getId());
                            return mDto;
                        })
                        .toList()
        );

        return dto;
    }

    public Scooter update(Long id, Scooter updated, MultipartFile image) throws IOException {
        Scooter existing = scooterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scooter not found"));

        existing.setUniqueId(updated.getUniqueId());
        existing.setModel(updated.getModel());
        existing.setManufacturer(updated.getManufacturer());
        existing.setMaxSpeed(updated.getMaxSpeed());
        existing.setPurchasePrice(updated.getPurchasePrice());
        existing.setRented(updated.isRented());
        existing.setCurrentLatitude(updated.getCurrentLatitude());
        existing.setCurrentLongitude(updated.getCurrentLongitude());

        if (image != null && !image.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            existing.setImagePath("/" + fileName);
        }

        if (existing.getCurrentLatitude() == null || existing.getCurrentLongitude() == null) {
            existing.setCurrentLatitude(44.7722);
            existing.setCurrentLongitude(17.1910);
        }

        return scooterRepository.save(existing);
    }
}
