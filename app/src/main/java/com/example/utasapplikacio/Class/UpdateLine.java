package com.example.utasapplikacio.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateLine {
    @SerializedName("vonalId")
    @Expose
    private int line;
    @SerializedName("buszId")
    @Expose
    private int bus;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("datum")
    @Expose
    private String date;

    public UpdateLine(){}

    public UpdateLine(int line, int bus, String lat, String lon, String date) {
        this.line = line;
        this.bus = bus;
        this.lat = lat;
        this.lon = lon;
        this.date = date;
    }

    public int getLine() {
        return line;
    }

    public void setLine(Integer vonalId) {
        this.line = vonalId;
    }

    public int getBus() {
        return bus;
    }

    public void setBus(Integer buszId) {
        this.bus = buszId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String datum) {
        this.date = datum;
    }
}
