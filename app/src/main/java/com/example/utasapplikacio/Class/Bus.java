package com.example.utasapplikacio.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bus {

    @SerializedName("Bus")
    @Expose
    private Integer bus;

    public Bus(){}

    public Integer getBus() {
        return bus;
    }

    public void setBus(Integer bus) {
        this.bus = bus;
    }
}
