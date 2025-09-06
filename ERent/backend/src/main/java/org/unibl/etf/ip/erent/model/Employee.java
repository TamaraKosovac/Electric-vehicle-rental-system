package org.unibl.etf.ip.erent.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends User {
    @Enumerated(EnumType.STRING)
    private Role role;
}
