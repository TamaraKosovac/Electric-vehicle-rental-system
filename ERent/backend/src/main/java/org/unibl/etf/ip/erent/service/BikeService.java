package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.erent.dto.BikeDTO;
import org.unibl.etf.ip.erent.dto.BikeDetailsDTO;
import org.unibl.etf.ip.erent.dto.MalfunctionDTO;
import org.unibl.etf.ip.erent.model.Bike;
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
                        !bike.getMalfunctions().isEmpty(),
                        bike.isRented()
                ))
                .toList();
    }

    public Bike findById(Long id) {
        return bikeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bike not found"));
    }

    public Bike save(Bike bike, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            bike.setImagePath("uploads/" + fileName);
        }
        return bikeRepository.save(bike);
    }

    public Bike save(Bike bike) {
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
        dto.setRented(bike.isRented());
        dto.setAutonomy(bike.getAutonomy());

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
}
