package org.unibl.etf.ip.erent.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scooter extends Vehicle {
    private Integer maxSpeed;
}