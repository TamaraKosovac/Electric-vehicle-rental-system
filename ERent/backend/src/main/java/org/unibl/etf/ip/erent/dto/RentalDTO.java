package org.unibl.etf.ip.erent.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RentalDTO {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Double duration;
    private Double startLatitude;
    private Double startLongitude;
    private Double endLatitude;
    private Double endLongitude;
    private Double price;
    private Long vehicleId;
    private Long clientId;
}
