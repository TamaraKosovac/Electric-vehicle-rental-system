package org.unibl.etf.ip.erent.dto;

public class MalfunctionDTO {
    private Long id;
    private String description;
    private String dateTime;
    private String vehicleUniqueId;

    public MalfunctionDTO() { }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }

    public String getVehicleUniqueId() { return vehicleUniqueId; }
    public void setVehicleUniqueId(String vehicleUniqueId) { this.vehicleUniqueId = vehicleUniqueId; }
}
