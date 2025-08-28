package org.unibl.etf.ip.erent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.erent.model.Bike;

public interface BikeRepository extends JpaRepository<Bike, Long> {
}
