package org.unibl.etf.ip.erent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.erent.dto.RentalDTO;
import org.unibl.etf.ip.erent.model.Rental;
import org.unibl.etf.ip.erent.service.RentalService;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @GetMapping
    public List<Rental> findAll() {
        return rentalService.findAll();
    }

    @GetMapping("/{id}")
    public Rental getById(@PathVariable Long id) {
        return rentalService.findById(id);
    }

    @PostMapping
    public Rental create(@RequestBody RentalDTO dto) {
        return rentalService.createFromDTO(dto);
    }

    @PutMapping("/{id}")
    public Rental update(@PathVariable Long id, @RequestBody RentalDTO dto) {
        return rentalService.updateFromDTO(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        rentalService.delete(id);
    }
}
