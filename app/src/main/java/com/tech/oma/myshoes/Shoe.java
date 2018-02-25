package com.tech.oma.myshoes;

/**
 * Created by Whisler on 25.02.2018.
 */

public class Shoe {

    private int id;

    private String titel;

    private String description;

    private String imagePath;

    private String art;

    public Shoe(int id, String titel, String description, String imagePath, String art) {
        this.id = id;
        this.titel = titel;
        this.description = description;
        this.imagePath = imagePath;
        this.art = art;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }
}
