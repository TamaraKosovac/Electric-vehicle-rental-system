package org.unibl.etf.ip.erent.dto;

import java.io.Serializable;

public class RentalPriceDTO implements Serializable {
    private Long id;
    private String vehicleType;
    private Double pricePerHour;

    public RentalPriceDTO() {}

    public RentalPriceDTO(Long id, String vehicleType, Double pricePerHour) {
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
        return "RentalPriceDTO{" +
                "id=" + id +
                ", vehicleType='" + vehicleType + '\'' +
                ", pricePerHour=" + pricePerHour +
                '}';
    }
}