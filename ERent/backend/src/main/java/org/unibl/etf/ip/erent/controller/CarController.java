package org.unibl.etf.ip.erent.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.erent.dto.CarDTO;
import org.unibl.etf.ip.erent.dto.CarDetailsDTO;
import org.unibl.etf.ip.erent.model.Car;
import org.unibl.etf.ip.erent.service.CarService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public List<CarDTO> getAll() {
        return carService.findAllDto();
    }

    @GetMapping("/{id}")
    public CarDetailsDTO getById(@PathVariable Long id) {
        return carService.findDetailsById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Car create(
            @RequestPart("car") String carJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        Car car = objectMapper.readValue(carJson, Car.class);
        return carService.save(car, image);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Car update(
            @PathVariable Long id,
            @RequestPart("car") String carJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        Car car = objectMapper.readValue(carJson, Car.class);
        car.setId(id);
        return carService.save(car, image);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        carService.delete(id);
    }
}
