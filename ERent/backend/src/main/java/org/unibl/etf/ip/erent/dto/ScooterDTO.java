package org.unibl.etf.ip.erent.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.ip.erent.model.VehicleState;

@Data
@NoArgsConstructor
public class ScooterDTO extends VehicleDTO {
    private Integer maxSpeed;

    public ScooterDTO(Long id, String uniqueId, String model, String manufacturer,
                      Integer maxSpeed, Double purchasePrice, String imagePath,
                      VehicleState state, Double currentLatitude, Double currentLongitude) {
        super(id, uniqueId, model, manufacturer, purchasePrice, imagePath, state, currentLatitude, currentLongitude);
        this.maxSpeed = maxSpeed;
    }
}