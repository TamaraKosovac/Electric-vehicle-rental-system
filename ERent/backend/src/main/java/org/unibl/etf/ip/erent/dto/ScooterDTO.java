package org.unibl.etf.ip.erent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScooterDTO extends VehicleDTO {
    private Integer maxSpeed;

    public ScooterDTO(Long id, String uniqueId, String model, String manufacturer,
                      Integer maxSpeed, Double purchasePrice,
                      boolean hasMalfunctions, boolean rented) {
        super(id, uniqueId, model, manufacturer, purchasePrice, hasMalfunctions, rented);
        this.maxSpeed = maxSpeed;
    }
}

