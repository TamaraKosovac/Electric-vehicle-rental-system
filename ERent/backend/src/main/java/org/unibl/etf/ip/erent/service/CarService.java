package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.erent.dto.CarDTO;
import org.unibl.etf.ip.erent.model.Car;
import org.unibl.etf.ip.erent.repository.CarRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

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

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public void delete(Long id) {
        carRepository.deleteById(id);
    }
}
