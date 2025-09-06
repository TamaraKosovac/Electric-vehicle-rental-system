package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.erent.dto.CarDTO;
import org.unibl.etf.ip.erent.dto.CarDetailsDTO;
import org.unibl.etf.ip.erent.dto.MalfunctionDTO;
import org.unibl.etf.ip.erent.model.Car;
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
                        !car.getMalfunctions().isEmpty(),
                        car.isRented(),
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
        if (image != null && !image.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            car.setImagePath("/" + fileName);
        }
        if (car.getCurrentLatitude() == null || car.getCurrentLongitude() == null) {
            car.setCurrentLatitude(44.7722);
            car.setCurrentLongitude(17.1910);
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
        dto.setRented(car.isRented());
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

        existing.setUniqueId(updated.getUniqueId());
        existing.setModel(updated.getModel());
        existing.setManufacturer(updated.getManufacturer());
        existing.setPurchasePrice(updated.getPurchasePrice());
        existing.setPurchaseDate(updated.getPurchaseDate());
        existing.setDescription(updated.getDescription());
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

        return carRepository.save(existing);
    }

}
