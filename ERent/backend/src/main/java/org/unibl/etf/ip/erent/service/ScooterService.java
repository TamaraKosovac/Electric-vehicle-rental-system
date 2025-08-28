package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.erent.model.Scooter;
import org.unibl.etf.ip.erent.repository.ScooterRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScooterService {

    private final ScooterRepository scooterRepository;

    public List<Scooter> findAll() {
        return scooterRepository.findAll();
    }

    public Scooter findById(Long id) {
        return scooterRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Scooter not found"));
    }

    public Scooter save(Scooter scooter) {
        return scooterRepository.save(scooter);
    }

    public void delete(Long id) {
        scooterRepository.deleteById(id);
    }
}
