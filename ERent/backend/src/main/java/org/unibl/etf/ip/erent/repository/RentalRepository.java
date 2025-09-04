package org.unibl.etf.ip.erent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.erent.model.Rental;
import org.unibl.etf.ip.erent.model.Vehicle;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByVehicle(Vehicle vehicle);
}
