package com.example.utasapplikacio.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateLine {
    @SerializedName("vonalId")
    @Expose
    private String line;
    @SerializedName("buszId")
    @Expose
    private String bus;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("datum")
    @Expose
    private String date;

    public UpdateLine(){}

    public UpdateLine(String line, String bus, Double lat, Double lon, String date) {
        this.line = line;
        this.bus = bus;
        this.lat = lat;
        this.lon = lon;
        this.date = date;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String vonalId) {
        this.line = vonalId;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String buszId) {
        this.bus = buszId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String datum) {
        this.date = datum;
    }
}
