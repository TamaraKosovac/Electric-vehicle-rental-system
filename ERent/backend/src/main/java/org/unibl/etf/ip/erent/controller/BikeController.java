package org.unibl.etf.ip.erent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.erent.dto.BikeDTO;
import org.unibl.etf.ip.erent.model.Bike;
import org.unibl.etf.ip.erent.service.BikeService;

import java.util.List;

@RestController
@RequestMapping("/api/bikes")
@RequiredArgsConstructor
public class BikeController {

    private final BikeService bikeService;

    @GetMapping
    public List<BikeDTO> getAll() {
        return bikeService.findAllDto();
    }

    @GetMapping("/{id}")
    public Bike getById(@PathVariable Long id) {
        return bikeService.findById(id);
    }

    @PostMapping
    public Bike create(@RequestBody Bike bike) {
        return bikeService.save(bike);
    }

    @PutMapping("/{id}")
    public Bike update(@PathVariable Long id, @RequestBody Bike bike) {
        bike.setId(id);
        return bikeService.save(bike);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bikeService.delete(id);
    }
}
