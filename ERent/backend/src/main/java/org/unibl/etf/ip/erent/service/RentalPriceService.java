package org.unibl.etf.ip.erent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.erent.model.RentalPrice;
import org.unibl.etf.ip.erent.repository.RentalPriceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalPriceService {

    private final RentalPriceRepository rentalPriceRepository;

    public List<RentalPrice> findAll() {
        return rentalPriceRepository.findAll();
    }

    public RentalPrice update(Long id, RentalPrice dto) {
        RentalPrice rentalPrice = rentalPriceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Price not found"));

        rentalPrice.setPricePerHour(dto.getPricePerHour());
        return rentalPriceRepository.save(rentalPrice);
    }
}
