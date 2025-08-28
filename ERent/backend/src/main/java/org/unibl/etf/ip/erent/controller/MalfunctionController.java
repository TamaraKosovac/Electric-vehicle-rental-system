package org.unibl.etf.ip.erent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.erent.dto.MalfunctionDTO;
import org.unibl.etf.ip.erent.model.Malfunction;
import org.unibl.etf.ip.erent.model.Vehicle;
import org.unibl.etf.ip.erent.repository.VehicleRepository;
import org.unibl.etf.ip.erent.service.MalfunctionService;

import java.util.List;

@RestController
@RequestMapping("/api/malfunctions")
@RequiredArgsConstructor
public class MalfunctionController {

    private final MalfunctionService service;
    private final VehicleRepository vehicleRepository;

    @GetMapping
    public List<Malfunction> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Malfunction getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public List<Malfunction> getByVehicle(@PathVariable Long vehicleId) {
        return service.findByVehicle(vehicleId);
    }

    @PostMapping
    public Malfunction create(@RequestBody MalfunctionDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        Malfunction malfunction = new Malfunction();
        malfunction.setDescription(dto.getDescription());
        malfunction.setDateTime(dto.getDateTime());
        malfunction.setVehicle(vehicle);

        return service.save(malfunction);
    }


    @PutMapping("/{id}")
    public Malfunction update(@PathVariable Long id, @RequestBody MalfunctionDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        Malfunction malfunction = service.findById(id);
        malfunction.setDescription(dto.getDescription());
        malfunction.setDateTime(dto.getDateTime());
        malfunction.setVehicle(vehicle);

        return service.save(malfunction);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
