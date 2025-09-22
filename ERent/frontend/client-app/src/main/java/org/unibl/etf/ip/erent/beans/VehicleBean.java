package org.unibl.etf.ip.erent.beans;

import java.io.Serializable;
import java.util.List;

public class VehicleBean implements Serializable {
    private Long id;
    private String uniqueId;
    private ManufacturerBean manufacturer;
    private String model;
    private Double purchasePrice;
    private String imagePath;
    private String state;
    private Double currentLatitude;
    private Double currentLongitude;
    private List<MalfunctionBean> malfunctions;

    public VehicleBean() { }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUniqueId() { return uniqueId; }
    public void setUniqueId(String uniqueId) { this.uniqueId = uniqueId; }

    public ManufacturerBean getManufacturer() { return manufacturer; }
    public void setManufacturer(ManufacturerBean manufacturer) { this.manufacturer = manufacturer; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Double getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(Double purchasePrice) { this.purchasePrice = purchasePrice; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public Double getCurrentLatitude() { return currentLatitude; }
    public void setCurrentLatitude(Double currentLatitude) { this.currentLatitude = currentLatitude; }

    public Double getCurrentLongitude() { return currentLongitude; }
    public void setCurrentLongitude(Double currentLongitude) { this.currentLongitude = currentLongitude; }

    public List<MalfunctionBean> getMalfunctions() { return malfunctions; }
    public void setMalfunctions(List<MalfunctionBean> malfunctions) { this.malfunctions = malfunctions; }
}