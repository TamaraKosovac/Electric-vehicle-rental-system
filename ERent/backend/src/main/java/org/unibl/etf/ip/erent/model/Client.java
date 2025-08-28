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
    private String email;
    private String phone;
    private String avatarPath;
}
