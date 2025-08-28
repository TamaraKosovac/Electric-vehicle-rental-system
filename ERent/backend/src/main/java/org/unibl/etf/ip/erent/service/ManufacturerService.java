package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.erent.model.Manufacturer;
import org.unibl.etf.ip.erent.repository.ManufacturerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

    public Manufacturer findById(Long id) {
        return manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found"));
    }

    public Manufacturer save(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public void delete(Long id) {
        manufacturerRepository.deleteById(id);
    }
}
