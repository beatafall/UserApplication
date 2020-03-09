package com.example.utasapplikacio.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Line {
    @SerializedName("Line")
    @Expose
    public Integer line;

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }
}
