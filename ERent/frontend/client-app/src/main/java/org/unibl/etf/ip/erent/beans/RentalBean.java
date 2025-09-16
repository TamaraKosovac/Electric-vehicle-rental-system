package org.unibl.etf.ip.erent.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RentalBean implements Serializable {
    private Long id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Double duration;
    private Double startLatitude;
    private Double startLongitude;
    private Double endLatitude;
    private Double endLongitude;
    private Double price;
    private VehicleBean vehicle;
    private ClientBean client;

    public RentalBean() { }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getStartDateTime() { return startDateTime; }
    public void setStartDateTime(LocalDateTime startDateTime) { this.startDateTime = startDateTime; }

    public LocalDateTime getEndDateTime() { return endDateTime; }
    public void setEndDateTime(LocalDateTime endDateTime) { this.endDateTime = endDateTime; }

    public Double getDuration() { return duration; }
    public void setDuration(Double duration) { this.duration = duration; }

    public Double getStartLatitude() { return startLatitude; }
    public void setStartLatitude(Double startLatitude) { this.startLatitude = startLatitude; }

    public Double getStartLongitude() { return startLongitude; }
    public void setStartLongitude(Double startLongitude) { this.startLongitude = startLongitude; }

    public Double getEndLatitude() { return endLatitude; }
    public void setEndLatitude(Double endLatitude) { this.endLatitude = endLatitude; }

    public Double getEndLongitude() { return endLongitude; }
    public void setEndLongitude(Double endLongitude) { this.endLongitude = endLongitude; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public VehicleBean getVehicle() { return vehicle; }
    public void setVehicle(VehicleBean vehicle) { this.vehicle = vehicle; }

    public ClientBean getClient() { return client; }
    public void setClient(ClientBean client) { this.client = client; }
}