package com.safetynet.safetynet.model;

public class Firestation {

    private String address;
    private Integer station;

    public Firestation() {}

    public Firestation(String address, Integer station) {
        this.station = station;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStation() {
        return station;
    }

    public void setStation(Integer station) {
        this.station = station;
    }
}
