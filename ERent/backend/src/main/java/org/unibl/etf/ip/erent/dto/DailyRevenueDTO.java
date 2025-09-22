package org.unibl.etf.ip.erent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DailyRevenueDTO {
    private LocalDate date;
    private Double totalRevenue;
}