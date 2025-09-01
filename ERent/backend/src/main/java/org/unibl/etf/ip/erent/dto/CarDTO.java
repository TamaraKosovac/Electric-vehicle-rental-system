package org.unibl.etf.ip.erent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CarDTO extends VehicleDTO {
    private LocalDate purchaseDate;
    private String description;

    public CarDTO(Long id, String uniqueId, String model, String manufacturer,
                  Double purchasePrice, LocalDate purchaseDate,
                  boolean hasMalfunctions, boolean rented, String description) {
        super(id, uniqueId, model, manufacturer, purchasePrice, hasMalfunctions, rented);
        this.purchaseDate = purchaseDate;
        this.description = description;
    }
}
