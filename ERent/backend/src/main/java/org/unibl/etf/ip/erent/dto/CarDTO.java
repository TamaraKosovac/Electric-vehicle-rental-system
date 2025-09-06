package org.unibl.etf.ip.erent.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CarDTO extends VehicleDTO {
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate purchaseDate;
    private String description;

    public CarDTO(Long id, String uniqueId, String model, String manufacturer,
                  Double purchasePrice, String imagePath, LocalDate purchaseDate,
                  boolean hasMalfunctions, boolean rented, String description, Double currentLatitude, Double currentLongitude) {
        super(id, uniqueId, model, manufacturer, purchasePrice, imagePath, hasMalfunctions, rented, currentLatitude, currentLongitude);
        this.purchaseDate = purchaseDate;
        this.description = description;
    }
}
