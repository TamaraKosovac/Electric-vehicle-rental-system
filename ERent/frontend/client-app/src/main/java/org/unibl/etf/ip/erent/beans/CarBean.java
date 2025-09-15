package org.unibl.etf.ip.erent.beans;

import java.io.Serializable;
import java.time.LocalDate;

public class CarBean extends VehicleBean implements Serializable {
    private LocalDate purchaseDate;
    private String description;

    public CarBean() { }

    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}