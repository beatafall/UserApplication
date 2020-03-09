package com.example.utasapplikacio.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Messages {
    @SerializedName("MessageId")
    @Expose
    public String messageId;

    @SerializedName("MessageTypeId")
    @Expose
    public String messageTypeId;

    @SerializedName("Line")
    @Expose
    public String line;

    @SerializedName("MessageDate")
    @Expose
    public String date;

    @SerializedName("MessageLon")
    @Expose
    public String lon;

    @SerializedName("MessageLat")
    @Expose
    public String lat;

    public Messages(){}

    public Messages(String messageId, String messageTypeId, String line, String date, String lon, String lat) {
        this.messageId = messageId;
        this.messageTypeId = messageTypeId;
        this.line = line;
        this.date = date;
        this.lon = lon;
        this.lat = lat;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageTypeId() {
        return messageTypeId;
    }

    public void setMessageTypeId(String messageTypeName) {
        this.messageTypeId = messageTypeName;
    }


    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "{" +
                "MessageId='" + messageId + '\'' +
                ", MessageTypeId='" + messageTypeId + '\'' +
                ", Line=" + line +
                ", MessageDate=" + date +
                ", MessageLon=" + lon +
                ", MessageLat=" + lat +
                '}';
    }
}
