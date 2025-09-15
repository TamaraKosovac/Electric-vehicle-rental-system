package org.unibl.etf.ip.erent.dto;

import java.io.Serializable;

public class CarDTO extends VehicleDTO implements Serializable {
    private String purchaseDate;
    private String description;

    public CarDTO() {}

    public String getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(String purchaseDate) { this.purchaseDate = purchaseDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
