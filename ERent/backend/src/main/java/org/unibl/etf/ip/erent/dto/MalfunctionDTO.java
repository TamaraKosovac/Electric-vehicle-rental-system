package org.unibl.etf.ip.erent.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MalfunctionDTO {
    private Long id;
    private String description;
    @JsonFormat(pattern = "dd-MM-yyyy  HH:mm")
    private LocalDateTime dateTime;
    private Long vehicleId;
}
