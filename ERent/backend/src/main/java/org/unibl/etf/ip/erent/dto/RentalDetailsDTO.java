package org.unibl.etf.ip.erent.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RentalDetailsDTO {
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime startDateTime;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime endDateTime;
    private Double duration;
    private Double startLatitude;
    private Double startLongitude;
    private Double endLatitude;
    private Double endLongitude;
    private Double price;
    private String clientFirstName;
    private String clientLastName;
    private String manufacturerName;
    private String vehicleModel;
    private Long vehicleId;
    private String imagePath;
}