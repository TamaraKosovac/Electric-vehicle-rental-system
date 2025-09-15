package org.unibl.etf.ip.erent.dto;

import java.io.Serializable;

public class BikeDTO extends VehicleDTO implements Serializable {
    private Integer autonomy;

    public BikeDTO() {}

    public Integer getAutonomy() { return autonomy; }
    public void setAutonomy(Integer autonomy) { this.autonomy = autonomy; }
}
