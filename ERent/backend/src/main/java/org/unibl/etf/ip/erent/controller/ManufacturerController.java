package org.unibl.etf.ip.erent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.erent.model.Manufacturer;
import org.unibl.etf.ip.erent.service.ManufacturerService;
import java.util.List;

@RestController
@RequestMapping("/api/manufacturers")
@RequiredArgsConstructor
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    @GetMapping
    public List<Manufacturer> getAll() {
        return manufacturerService.findAll();
    }

    @GetMapping("/{id}")
    public Manufacturer getById(@PathVariable Long id) {
        return manufacturerService.findById(id);
    }

    @PostMapping
    public Manufacturer create(@RequestBody Manufacturer manufacturer) {
        return manufacturerService.save(manufacturer);
    }

    @PutMapping("/{id}")
    public Manufacturer update(@PathVariable Long id, @RequestBody Manufacturer manufacturer) {
        manufacturer.setId(id);
        return manufacturerService.update(id, manufacturer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        manufacturerService.delete(id);
    }
}