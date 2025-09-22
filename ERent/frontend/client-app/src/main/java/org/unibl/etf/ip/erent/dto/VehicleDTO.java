package org.unibl.etf.ip.erent.dto;

public class VehicleDTO {
    private Long id;
    private String uniqueId;
    private String model;
    private String manufacturerName;
    private String state;
    private String type;

    public VehicleDTO() { }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUniqueId() { return uniqueId; }
    public void setUniqueId(String uniqueId) { this.uniqueId = uniqueId; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getManufacturerName() { return manufacturerName; }
    public void setManufacturerName(String manufacturerName) { this.manufacturerName = manufacturerName; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}