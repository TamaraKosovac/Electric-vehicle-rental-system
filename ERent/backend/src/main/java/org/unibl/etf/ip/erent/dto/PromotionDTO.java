package org.unibl.etf.ip.erent.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PromotionDTO {
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long createdByEmployeeId;
}
