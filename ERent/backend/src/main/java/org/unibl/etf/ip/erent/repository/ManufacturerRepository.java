package org.unibl.etf.ip.erent.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.erent.model.Manufacturer;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
}
