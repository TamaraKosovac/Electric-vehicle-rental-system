package org.unibl.etf.ip.erent.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bike extends Vehicle {
    private Integer autonomy;
}