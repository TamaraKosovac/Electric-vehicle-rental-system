package org.unibl.etf.ip.erent.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MalfunctionBean implements Serializable {
    private Long id;
    private String description;
    private LocalDateTime dateTime;
    private Long vehicleId;

    public MalfunctionBean() { }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }
}