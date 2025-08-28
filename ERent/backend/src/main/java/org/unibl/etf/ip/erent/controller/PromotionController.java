package org.unibl.etf.ip.erent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.erent.dto.PromotionDTO;
import org.unibl.etf.ip.erent.model.Promotion;
import org.unibl.etf.ip.erent.service.PromotionService;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @GetMapping
    public List<Promotion> getAll() {
        return promotionService.findAll();
    }

    @GetMapping("/{id}")
    public Promotion getById(@PathVariable Long id) {
        return promotionService.findById(id);
    }

    @PostMapping
    public Promotion create(@RequestBody PromotionDTO dto) {
        return promotionService.createFromDTO(dto);
    }

    @PutMapping("/{id}")
    public Promotion update(@PathVariable Long id, @RequestBody PromotionDTO dto) {
        return promotionService.updateFromDTO(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        promotionService.delete(id);
    }
}
