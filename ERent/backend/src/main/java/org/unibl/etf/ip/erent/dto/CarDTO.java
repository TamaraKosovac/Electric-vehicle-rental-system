package org.unibl.etf.ip.erent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CarDTO {
    private Long id;
    private String uniqueId;
    private String model;
    private String manufacturer;
    private Double purchasePrice;
    private LocalDate purchaseDate;
    private boolean hasMalfunctions;
    private boolean rented;
    private String description;
}
