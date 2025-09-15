package org.unibl.etf.ip.erent.dto;

import java.io.Serializable;

public class ScooterDTO extends VehicleDTO implements Serializable {
    private Integer maxSpeed;

    public ScooterDTO() {}

    public Integer getMaxSpeed() { return maxSpeed; }
    public void setMaxSpeed(Integer maxSpeed) { this.maxSpeed = maxSpeed; }
}
