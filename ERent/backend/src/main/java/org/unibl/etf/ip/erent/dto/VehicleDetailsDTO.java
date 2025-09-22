package org.unibl.etf.ip.erent.dto;

import lombok.Data;
import org.unibl.etf.ip.erent.model.Manufacturer;
import org.unibl.etf.ip.erent.model.VehicleState;
import java.util.List;

@Data
public class VehicleDetailsDTO {
    private String uniqueId;
    private Manufacturer manufacturer;
    private String model;
    private Double purchasePrice;
    private String imagePath;
    private VehicleState state;
    private Double currentLatitude;
    private Double currentLongitude;
    private List<MalfunctionDTO> malfunctions;
}