package org.unibl.etf.ip.erent.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client extends User {
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    private String documentNumber;
    private String drivingLicense;
    @Column(unique = true, nullable = false)
    private String email;
    private String phone;
    private String avatarPath;
    private boolean blocked = false;
    private boolean active = true;
}
