package org.unibl.etf.ip.erent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RssPromotionDTO {
    public String title;
    public String description;
    public LocalDate startDate;
    private LocalDate endDate;
}