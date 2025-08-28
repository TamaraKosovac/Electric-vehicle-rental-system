package org.unibl.etf.ip.erent.dto;

import lombok.Data;

@Data
public class ClientDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String avatarPath;
    private String documentType;
    private String documentNumber;
    private String drivingLicense;
}
