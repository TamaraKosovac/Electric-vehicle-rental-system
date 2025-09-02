package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.erent.dto.CarDTO;
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
                        car.getPurchaseDate(),
                        !car.getMalfunctions().isEmpty(),
                        car.isRented(),
                        car.getDescription()
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

            car.setImagePath("uploads/" + fileName);
        }
        return carRepository.save(car);
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public void delete(Long id) {
        carRepository.deleteById(id);
    }
}
