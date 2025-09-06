package org.unibl.etf.ip.erent.dto;

import lombok.Data;
import org.unibl.etf.ip.erent.model.Manufacturer;

import java.util.List;

@Data
public class VehicleDetailsDTO {
    private String uniqueId;
    private Manufacturer manufacturer;
    private String model;
    private Double purchasePrice;
    private String imagePath;
    private boolean rented;
    private Double currentLatitude;
    private Double currentLongitude;
    private List<MalfunctionDTO> malfunctions;
}