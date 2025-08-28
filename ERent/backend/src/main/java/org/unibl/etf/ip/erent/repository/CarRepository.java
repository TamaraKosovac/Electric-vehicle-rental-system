package org.unibl.etf.ip.erent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.erent.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
