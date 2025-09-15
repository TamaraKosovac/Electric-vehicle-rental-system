package org.unibl.etf.ip.erent.beans;

import java.io.Serializable;

public class BikeBean extends VehicleBean implements Serializable {
    private Integer autonomy;

    public BikeBean() { }

    public Integer getAutonomy() { return autonomy; }
    public void setAutonomy(Integer autonomy) { this.autonomy = autonomy; }
}
