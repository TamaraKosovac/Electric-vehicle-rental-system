package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.erent.dto.BikeDTO;
import org.unibl.etf.ip.erent.dto.CarDTO;
import org.unibl.etf.ip.erent.model.Bike;
import org.unibl.etf.ip.erent.repository.BikeRepository;
import org.unibl.etf.ip.erent.repository.ScooterRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BikeService {

    private final BikeRepository bikeRepository;

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
        return bikeRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Bike not found"));
    }

    public Bike save(Bike bike) {
        return bikeRepository.save(bike);
    }

    public void delete(Long id) {
        bikeRepository.deleteById(id);
    }
}
