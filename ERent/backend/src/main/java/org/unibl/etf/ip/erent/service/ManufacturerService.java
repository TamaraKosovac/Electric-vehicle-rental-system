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
        if (manufacturer.getName() == null || manufacturer.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Manufacturer name cannot be empty");
        }
        if (!manufacturerRepository.findByName(manufacturer.getName()).isEmpty()) {
            throw new IllegalArgumentException("Manufacturer with this name already exists");
        }

        return manufacturerRepository.save(manufacturer);
    }

    public Manufacturer update(Long id, Manufacturer manufacturerDetails) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found"));

        if (!manufacturer.getName().equalsIgnoreCase(manufacturerDetails.getName())) {
            if (!manufacturerRepository.findByName(manufacturerDetails.getName()).isEmpty()) {
                throw new IllegalArgumentException("Manufacturer with this name already exists");
            }
        }
        manufacturer.setName(manufacturerDetails.getName());
        manufacturer.setCountry(manufacturerDetails.getCountry());
        manufacturer.setAddress(manufacturerDetails.getAddress());
        manufacturer.setPhone(manufacturerDetails.getPhone());
        manufacturer.setFax(manufacturerDetails.getFax());
        manufacturer.setEmail(manufacturerDetails.getEmail());

        return manufacturerRepository.save(manufacturer);
    }

    public void delete(Long id) {
        if (!manufacturerRepository.existsById(id)) {
            throw new RuntimeException("Manufacturer not found");
        }
        manufacturerRepository.deleteById(id);
    }
}