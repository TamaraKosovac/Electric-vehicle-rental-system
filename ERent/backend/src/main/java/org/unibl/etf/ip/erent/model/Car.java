package org.unibl.etf.ip.erent.model;

import jakarta.persistence.Entity;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car extends Vehicle {
    private LocalDate purchaseDate;
    private String description;
}