package org.unibl.etf.ip.erent.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.ip.erent.model.VehicleState;

@Data
@NoArgsConstructor
public class BikeDTO extends VehicleDTO {
    private Integer autonomy;

    public BikeDTO(Long id, String uniqueId, String model, String manufacturer,
                   Integer autonomy, Double purchasePrice, String imagePath,
                   VehicleState state, Double currentLatitude, Double currentLongitude) {
        super(id, uniqueId, model, manufacturer, purchasePrice, imagePath, state, currentLatitude, currentLongitude);
        this.autonomy = autonomy;
    }
}