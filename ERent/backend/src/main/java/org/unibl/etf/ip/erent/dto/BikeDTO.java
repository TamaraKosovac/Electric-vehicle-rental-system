package org.unibl.etf.ip.erent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BikeDTO extends VehicleDTO {
    private Integer autonomy;

    public BikeDTO(Long id, String uniqueId, String model, String manufacturer,Integer autonomy,
                   Double purchasePrice, boolean hasMalfunctions, boolean rented) {
        super(id, uniqueId, model, manufacturer, purchasePrice, hasMalfunctions, rented);
        this.autonomy = autonomy;
    }
}
