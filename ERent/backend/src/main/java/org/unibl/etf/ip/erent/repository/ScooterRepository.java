package org.unibl.etf.ip.erent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.erent.model.Scooter;

public interface ScooterRepository extends JpaRepository<Scooter, Long> {
}
