package org.unibl.etf.ip.erent.bean;

public class ClientBean {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String documentType;
    private String documentNumber;
    private String drivingLicense;
    private String email;
    private String phone;
    private String avatarPath;
    private boolean blocked;

    public ClientBean() {}

    public ClientBean(int id, String username, String password, String firstName, String lastName,
                      String documentType, String documentNumber, String drivingLicense,
                      String email, String phone, String avatarPath, boolean blocked) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.drivingLicense = drivingLicense;
        this.email = email;
        this.phone = phone;
        this.avatarPath = avatarPath;
        this.blocked = blocked;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getDocumentType() { return documentType; }
    public void setDocumentType(String documentType) { this.documentType = documentType; }

    public String getDocumentNumber() { return documentNumber; }
    public void setDocumentNumber(String documentNumber) { this.documentNumber = documentNumber; }

    public String getDrivingLicense() { return drivingLicense; }
    public void setDrivingLicense(String drivingLicense) { this.drivingLicense = drivingLicense; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAvatarPath() { return avatarPath; }
    public void setAvatarPath(String avatarPath) { this.avatarPath = avatarPath; }

    public boolean isBlocked() { return blocked; }
    public void setBlocked(boolean blocked) { this.blocked = blocked; }
}