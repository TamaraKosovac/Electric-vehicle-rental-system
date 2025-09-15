package org.unibl.etf.ip.erent.beans;

import java.io.Serializable;

public class ScooterBean extends VehicleBean implements Serializable {
    private Integer maxSpeed;

    public ScooterBean() { }

    public Integer getMaxSpeed() { return maxSpeed; }
    public void setMaxSpeed(Integer maxSpeed) { this.maxSpeed = maxSpeed; }
}