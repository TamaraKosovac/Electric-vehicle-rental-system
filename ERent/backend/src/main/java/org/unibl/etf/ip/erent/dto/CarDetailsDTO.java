package org.unibl.etf.ip.erent.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class CarDetailsDTO extends VehicleDetailsDTO {
    private String purchaseDate;
    private String description;
}
