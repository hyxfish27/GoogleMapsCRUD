package com.example.myapplication.Model;

public class Place {

    private int id;
    private String platitude;
    private String plongitude;
    private String title;
    private String snippet;

    public Place() {

    }

    public Place(int id, String platitude, String plongitude, String title, String snippet) {
        this.id = id;
        this.platitude = platitude;
        this.plongitude = plongitude;
        this.title = title;
        this.snippet = snippet;
    }

    public Place(String platitude, String plongitude, String title, String snippet) {
        this.platitude = platitude;
        this.plongitude = plongitude;
        this.title = title;
        this.snippet = snippet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlatitude() {
        return platitude;
    }

    public void setPlatitude(String platitude) {
        this.platitude = platitude;
    }

    public String getPlongitude() {
        return plongitude;
    }

    public void setPlongitude(String plongitude) {
        this.plongitude = plongitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
}
