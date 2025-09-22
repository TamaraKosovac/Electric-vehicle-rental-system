package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.erent.dto.MalfunctionDTO;
import org.unibl.etf.ip.erent.dto.ScooterDTO;
import org.unibl.etf.ip.erent.dto.ScooterDetailsDTO;
import org.unibl.etf.ip.erent.model.Scooter;
import org.unibl.etf.ip.erent.model.VehicleState;
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
                        scooter.getState(),
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
        validateScooter(scooter);
        handleImageUpload(scooter, image);
        handleCoordinates(scooter);

        if (scooter.getMalfunctions() != null && !scooter.getMalfunctions().isEmpty()) {
            scooter.setState(VehicleState.BROKEN);
        } else if (scooter.getState() == null) {
            scooter.setState(VehicleState.AVAILABLE);
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
        dto.setState(scooter.getState());
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

        validateScooter(updated);

        existing.setUniqueId(updated.getUniqueId());
        existing.setModel(updated.getModel());
        existing.setManufacturer(updated.getManufacturer());
        existing.setMaxSpeed(updated.getMaxSpeed());
        existing.setPurchasePrice(updated.getPurchasePrice());
        existing.setState(updated.getState());
        existing.setCurrentLatitude(updated.getCurrentLatitude());
        existing.setCurrentLongitude(updated.getCurrentLongitude());

        handleImageUpload(existing, image);
        handleCoordinates(existing);

        if (existing.getMalfunctions() != null && !existing.getMalfunctions().isEmpty()) {
            existing.setState(VehicleState.BROKEN);
        } else if (existing.getState() == null) {
            existing.setState(VehicleState.AVAILABLE);
        }

        return scooterRepository.save(existing);
    }

    private void validateScooter(Scooter scooter) {
        if (scooter == null) {
            throw new IllegalArgumentException("Scooter cannot be null");
        }
        if (scooter.getUniqueId() == null || scooter.getUniqueId().trim().isEmpty()) {
            throw new IllegalArgumentException("Unique ID is required");
        }
        if (scooter.getModel() == null || scooter.getModel().trim().isEmpty()) {
            throw new IllegalArgumentException("Model is required");
        }
        if (scooter.getManufacturer() == null) {
            throw new IllegalArgumentException("Manufacturer is required");
        }
        if (scooter.getPurchasePrice() == null || scooter.getPurchasePrice() < 0) {
            throw new IllegalArgumentException("Purchase price must be non-negative");
        }
        if (scooter.getMaxSpeed() == null || scooter.getMaxSpeed() <= 0) {
            throw new IllegalArgumentException("Max speed must be greater than 0");
        }
    }

    private void handleImageUpload(Scooter scooter, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            String contentType = image.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IllegalArgumentException("Only image files are allowed");
            }

            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            scooter.setImagePath("/" + fileName);
        }
    }

    private void handleCoordinates(Scooter scooter) {
        if (scooter.getCurrentLatitude() == null || scooter.getCurrentLongitude() == null) {
            scooter.setCurrentLatitude(44.7722);
            scooter.setCurrentLongitude(17.1910);
        } else {
            if (scooter.getCurrentLatitude() < -90 || scooter.getCurrentLatitude() > 90
                    || scooter.getCurrentLongitude() < -180 || scooter.getCurrentLongitude() > 180) {
                throw new IllegalArgumentException("Invalid coordinates");
            }
        }
    }
}