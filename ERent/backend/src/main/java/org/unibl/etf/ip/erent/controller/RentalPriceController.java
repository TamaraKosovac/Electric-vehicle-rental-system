package org.unibl.etf.ip.erent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.erent.model.RentalPrice;
import org.unibl.etf.ip.erent.service.RentalPriceService;

import java.util.List;

@RestController
@RequestMapping("/api/rental-prices")
@RequiredArgsConstructor
public class RentalPriceController {

    private final RentalPriceService rentalPriceService;

    @GetMapping
    public List<RentalPrice> getAll() {
        return rentalPriceService.findAll();
    }

    @PutMapping("/{id}")
    public RentalPrice update(@PathVariable Long id, @RequestBody RentalPrice dto) {
        return rentalPriceService.update(id, dto);
    }
}
