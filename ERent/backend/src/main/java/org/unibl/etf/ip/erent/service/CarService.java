package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.erent.dto.CarDTO;
import org.unibl.etf.ip.erent.dto.CarDetailsDTO;
import org.unibl.etf.ip.erent.dto.MalfunctionDTO;
import org.unibl.etf.ip.erent.model.Car;
import org.unibl.etf.ip.erent.model.VehicleState;
import org.unibl.etf.ip.erent.repository.CarRepository;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public List<CarDTO> findAllDto() {
        return carRepository.findAll()
                .stream()
                .map(car -> new CarDTO(
                        car.getId(),
                        car.getUniqueId(),
                        car.getModel(),
                        car.getManufacturer().getName(),
                        car.getPurchasePrice(),
                        car.getImagePath(),
                        car.getPurchaseDate(),
                        car.getState(),
                        car.getDescription(),
                        car.getCurrentLatitude(),
                        car.getCurrentLongitude()
                ))
                .toList();
    }

    public Car findById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));
    }

    public Car save(Car car, MultipartFile image) throws IOException {
        validateCar(car);

        handleImageUpload(car, image);
        handleCoordinates(car);

        if (car.getMalfunctions() != null && !car.getMalfunctions().isEmpty()) {
            car.setState(VehicleState.BROKEN);
        } else if (car.getState() == null) {
            car.setState(VehicleState.AVAILABLE);
        }
        return carRepository.save(car);
    }

    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    public CarDetailsDTO findDetailsById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        CarDetailsDTO dto = new CarDetailsDTO();
        dto.setUniqueId(car.getUniqueId());
        dto.setManufacturer(car.getManufacturer());
        dto.setModel(car.getModel());
        dto.setPurchasePrice(car.getPurchasePrice());
        dto.setImagePath(car.getImagePath());
        dto.setState(car.getState());
        dto.setDescription(car.getDescription());
        dto.setCurrentLatitude(car.getCurrentLatitude());
        dto.setCurrentLongitude(car.getCurrentLongitude());

        if (car.getPurchaseDate() != null) {
            dto.setPurchaseDate(car.getPurchaseDate().toString());
        }

        dto.setMalfunctions(
                car.getMalfunctions().stream()
                        .map(m -> {
                            MalfunctionDTO mDto = new MalfunctionDTO();
                            mDto.setId(m.getId());
                            mDto.setDescription(m.getDescription());
                            mDto.setDateTime(m.getDateTime());
                            mDto.setVehicleId(car.getId());
                            return mDto;
                        })
                        .toList()
        );

        return dto;
    }

    public Car update(Long id, Car updated, MultipartFile image) throws IOException {
        Car existing = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        validateCar(updated);

        existing.setUniqueId(updated.getUniqueId());
        existing.setModel(updated.getModel());
        existing.setManufacturer(updated.getManufacturer());
        existing.setPurchasePrice(updated.getPurchasePrice());
        existing.setPurchaseDate(updated.getPurchaseDate());
        existing.setDescription(updated.getDescription());
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
        return carRepository.save(existing);
    }

    private void validateCar(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car cannot be null");
        }
        if (car.getUniqueId() == null || car.getUniqueId().trim().isEmpty()) {
            throw new IllegalArgumentException("Unique ID is required");
        }
        if (car.getModel() == null || car.getModel().trim().isEmpty()) {
            throw new IllegalArgumentException("Model is required");
        }
        if (car.getManufacturer() == null) {
            throw new IllegalArgumentException("Manufacturer is required");
        }
        if (car.getPurchasePrice() == null || car.getPurchasePrice() < 0) {
            throw new IllegalArgumentException("Purchase price must be non-negative");
        }
    }

    private void handleImageUpload(Car car, MultipartFile image) throws IOException {
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

            car.setImagePath("/" + fileName);
        }
    }

    private void handleCoordinates(Car car) {
        if (car.getCurrentLatitude() == null || car.getCurrentLongitude() == null) {
            car.setCurrentLatitude(44.7722);
            car.setCurrentLongitude(17.1910);
        } else {
            if (car.getCurrentLatitude() < -90 || car.getCurrentLatitude() > 90
                    || car.getCurrentLongitude() < -180 || car.getCurrentLongitude() > 180) {
                throw new IllegalArgumentException("Invalid coordinates");
            }
        }
    }
}