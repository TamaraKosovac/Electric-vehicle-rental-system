package org.unibl.etf.ip.erent.dto;

public class ManufacturerDTO {
    private Long id;
    private String name;
    private String country;

    public ManufacturerDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}