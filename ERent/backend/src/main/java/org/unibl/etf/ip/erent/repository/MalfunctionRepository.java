package org.unibl.etf.ip.erent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.erent.model.Malfunction;
import java.util.List;

public interface MalfunctionRepository extends JpaRepository<Malfunction, Long> {
    List<Malfunction> findByVehicleId(Long vehicleId);
}