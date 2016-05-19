package com.example.edwin.hypeweatherservice.data.sqlite;

/**
 * Created by edwin on 19-5-2016.
 */
public class Weather {
    private int id;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
