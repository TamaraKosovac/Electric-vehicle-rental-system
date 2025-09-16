package org.unibl.etf.ip.erent.dto;

import java.time.LocalDateTime;

public class RentalDTO {
    private Long id;
    private String manufacturer;
    private String model;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Double duration;
    private Double price;
    private Double startLatitude;
    private Double startLongitude;
    private Double endLatitude;
    private Double endLongitude;

    public RentalDTO() { }

    public RentalDTO(Long id, String manufacturer, String model,
                     LocalDateTime startDateTime, LocalDateTime endDateTime,
                     Double duration, Double price,
                     Double startLatitude, Double startLongitude,
                     Double endLatitude, Double endLongitude) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.duration = duration;
        this.price = price;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public LocalDateTime getStartDateTime() { return startDateTime; }
    public void setStartDateTime(LocalDateTime startDateTime) { this.startDateTime = startDateTime; }

    public LocalDateTime getEndDateTime() { return endDateTime; }
    public void setEndDateTime(LocalDateTime endDateTime) { this.endDateTime = endDateTime; }

    public Double getDuration() { return duration; }
    public void setDuration(Double duration) { this.duration = duration; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Double getStartLatitude() { return startLatitude; }
    public void setStartLatitude(Double startLatitude) { this.startLatitude = startLatitude; }

    public Double getStartLongitude() { return startLongitude; }
    public void setStartLongitude(Double startLongitude) { this.startLongitude = startLongitude; }

    public Double getEndLatitude() { return endLatitude; }
    public void setEndLatitude(Double endLatitude) { this.endLatitude = endLatitude; }

    public Double getEndLongitude() { return endLongitude; }
    public void setEndLongitude(Double endLongitude) { this.endLongitude = endLongitude; }
}