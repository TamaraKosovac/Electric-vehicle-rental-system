package org.unibl.etf.ip.erent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.erent.dto.ScooterDTO;
import org.unibl.etf.ip.erent.model.Scooter;
import org.unibl.etf.ip.erent.service.ScooterService;

import java.io.IOException;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Scooter create(
            @RequestPart("scooter") Scooter scooter,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        return scooterService.save(scooter, image);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Scooter update(
            @PathVariable Long id,
            @RequestPart("scooter") Scooter scooter,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        scooter.setId(id);
        return scooterService.save(scooter, image);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        scooterService.delete(id);
    }
}
