package org.unibl.etf.ip.erent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.erent.model.RentalPrice;
import org.unibl.etf.ip.erent.model.VehicleType;

import java.util.Optional;

public interface RentalPriceRepository extends JpaRepository<RentalPrice, Long> {
}

