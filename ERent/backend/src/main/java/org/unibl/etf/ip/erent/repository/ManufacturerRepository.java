package org.unibl.etf.ip.erent.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.erent.model.Manufacturer;

import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    Optional<Manufacturer> findByName(String name);

}
