package org.unibl.etf.ip.erent.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(length = 2000)
    private String content;

    private LocalDate createdAt;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    private Employee createdBy;
}
