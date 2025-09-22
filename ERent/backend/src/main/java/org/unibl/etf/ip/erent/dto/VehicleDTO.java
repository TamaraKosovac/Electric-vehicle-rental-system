package org.unibl.etf.ip.erent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.ip.erent.model.VehicleState;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private Long id;
    private String uniqueId;
    private String model;
    private String manufacturer;
    private Double purchasePrice;
    private String imagePath;
    private VehicleState state;
    private Double currentLatitude;
    private Double currentLongitude;
}