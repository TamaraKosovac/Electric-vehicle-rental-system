package org.unibl.etf.ip.erent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.erent.dto.ScooterDTO;
import org.unibl.etf.ip.erent.model.Scooter;
import org.unibl.etf.ip.erent.service.ScooterService;

import java.util.List;

@RestController
@RequestMapping("/api/scooters")
@RequiredArgsConstructor
public class ScooterController {

    private final ScooterService scooterService;

    @GetMapping
    public List<ScooterDTO> getAll() {
        return scooterService.findAllDto();
    }

    @GetMapping("/{id}")
    public Scooter getById(@PathVariable Long id) {
        return scooterService.findById(id);
    }

    @PostMapping
    public Scooter create(@RequestBody Scooter scooter) {
        return scooterService.save(scooter);
    }

    @PutMapping("/{id}")
    public Scooter update(@PathVariable Long id, @RequestBody Scooter scooter) {
        scooter.setId(id);
        return scooterService.save(scooter);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        scooterService.delete(id);
    }
}
