package org.unibl.etf.ip.erent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private Long id;
    private String uniqueId;
    private String model;
    private String manufacturer;
    private Double purchasePrice;
    private boolean hasMalfunctions;
    private boolean rented;
    private Double currentLatitude;
    private Double currentLongitude;
}
