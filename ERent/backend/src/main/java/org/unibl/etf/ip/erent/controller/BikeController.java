package org.unibl.etf.ip.erent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.erent.dto.BikeDTO;
import org.unibl.etf.ip.erent.dto.BikeDetailsDTO;
import org.unibl.etf.ip.erent.model.Bike;
import org.unibl.etf.ip.erent.service.BikeService;

import java.io.IOException;
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
    public BikeDetailsDTO getById(@PathVariable Long id) {
        return bikeService.findDetailsById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Bike create(
            @RequestPart("bike") Bike bike,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        return bikeService.save(bike, image);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Bike update(
            @PathVariable Long id,
            @RequestPart("bike") Bike bike,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        return bikeService.update(id, bike, image);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bikeService.delete(id);
    }
}
