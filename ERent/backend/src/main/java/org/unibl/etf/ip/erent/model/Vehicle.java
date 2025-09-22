package org.unibl.etf.ip.erent.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String uniqueId;
    @ManyToOne
    private Manufacturer manufacturer;
    private String model;
    private Double purchasePrice;
    private String imagePath;
    @Enumerated(EnumType.STRING)
    private VehicleState state = VehicleState.AVAILABLE;
    private Double currentLatitude;
    private Double currentLongitude;
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Malfunction> malfunctions = new ArrayList<>();
}