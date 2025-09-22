package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.erent.dto.BikeDTO;
import org.unibl.etf.ip.erent.dto.BikeDetailsDTO;
import org.unibl.etf.ip.erent.dto.MalfunctionDTO;
import org.unibl.etf.ip.erent.model.Bike;
import org.unibl.etf.ip.erent.model.VehicleState;
import org.unibl.etf.ip.erent.repository.BikeRepository;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BikeService {

    private final BikeRepository bikeRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public List<Bike> findAll() {
        return bikeRepository.findAll();
    }

    public List<BikeDTO> findAllDto() {
        return bikeRepository.findAll()
                .stream()
                .map(bike -> new BikeDTO(
                        bike.getId(),
                        bike.getUniqueId(),
                        bike.getModel(),
                        bike.getManufacturer().getName(),
                        bike.getAutonomy(),
                        bike.getPurchasePrice(),
                        bike.getImagePath(),
                        bike.getState(),
                        bike.getCurrentLatitude(),
                        bike.getCurrentLongitude()
                ))
                .toList();
    }

    public Bike findById(Long id) {
        return bikeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bike not found"));
    }

    public Bike save(Bike bike, MultipartFile image) throws IOException {
        validateBike(bike);

        handleImageUpload(bike, image);
        handleCoordinates(bike);

        if (bike.getMalfunctions() != null && !bike.getMalfunctions().isEmpty()) {
            bike.setState(VehicleState.BROKEN);
        } else if (bike.getState() == null) {
            bike.setState(VehicleState.AVAILABLE);
        }

        return bikeRepository.save(bike);
    }

    public void delete(Long id) {
        bikeRepository.deleteById(id);
    }

    public BikeDetailsDTO findDetailsById(Long id) {
        Bike bike = bikeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bike not found"));

        BikeDetailsDTO dto = new BikeDetailsDTO();
        dto.setUniqueId(bike.getUniqueId());
        dto.setManufacturer(bike.getManufacturer());
        dto.setModel(bike.getModel());
        dto.setPurchasePrice(bike.getPurchasePrice());
        dto.setImagePath(bike.getImagePath());
        dto.setState(bike.getState());
        dto.setAutonomy(bike.getAutonomy());
        dto.setCurrentLatitude(bike.getCurrentLatitude());
        dto.setCurrentLongitude(bike.getCurrentLongitude());

        dto.setMalfunctions(
                bike.getMalfunctions().stream()
                        .map(m -> {
                            MalfunctionDTO mDto = new MalfunctionDTO();
                            mDto.setId(m.getId());
                            mDto.setDescription(m.getDescription());
                            mDto.setDateTime(m.getDateTime());
                            mDto.setVehicleId(bike.getId());
                            return mDto;
                        })
                        .toList()
        );

        return dto;
    }

    public Bike update(Long id, Bike updated, MultipartFile image) throws IOException {
        Bike existing = bikeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bike not found"));

        validateBike(updated);

        existing.setUniqueId(updated.getUniqueId());
        existing.setModel(updated.getModel());
        existing.setManufacturer(updated.getManufacturer());
        existing.setAutonomy(updated.getAutonomy());
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

        return bikeRepository.save(existing);
    }

    private void validateBike(Bike bike) {
        if (bike == null) {
            throw new IllegalArgumentException("Bike cannot be null");
        }
        if (bike.getUniqueId() == null || bike.getUniqueId().trim().isEmpty()) {
            throw new IllegalArgumentException("Unique ID is required");
        }
        if (bike.getModel() == null || bike.getModel().trim().isEmpty()) {
            throw new IllegalArgumentException("Model is required");
        }
        if (bike.getManufacturer() == null) {
            throw new IllegalArgumentException("Manufacturer is required");
        }
        if (bike.getPurchasePrice() == null || bike.getPurchasePrice() < 0) {
            throw new IllegalArgumentException("Purchase price must be non-negative");
        }
        if (bike.getAutonomy() != null && bike.getAutonomy() < 0) {
            throw new IllegalArgumentException("Autonomy must be non-negative");
        }
    }

    private void handleImageUpload(Bike bike, MultipartFile image) throws IOException {
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

            bike.setImagePath("/" + fileName);
        }
    }

    private void handleCoordinates(Bike bike) {
        if (bike.getCurrentLatitude() == null || bike.getCurrentLongitude() == null) {
            bike.setCurrentLatitude(44.7722);
            bike.setCurrentLongitude(17.1910);
        } else {
            if (bike.getCurrentLatitude() < -90 || bike.getCurrentLatitude() > 90
                    || bike.getCurrentLongitude() < -180 || bike.getCurrentLongitude() > 180) {
                throw new IllegalArgumentException("Invalid coordinates");
            }
        }
    }
}