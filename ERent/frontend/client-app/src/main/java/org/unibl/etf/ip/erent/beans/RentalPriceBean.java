package org.unibl.etf.ip.erent.beans;

import java.io.Serializable;

public class RentalPriceBean implements Serializable {
    private Long id;
    private String vehicleType;
    private Double pricePerHour;

    public RentalPriceBean() {}

    public RentalPriceBean(Long id, String vehicleType, Double pricePerHour) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.pricePerHour = pricePerHour;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    @Override
    public String toString() {
        return "RentalPriceBean{" +
                "id=" + id +
                ", vehicleType='" + vehicleType + '\'' +
                ", pricePerHour=" + pricePerHour +
                '}';
    }
}