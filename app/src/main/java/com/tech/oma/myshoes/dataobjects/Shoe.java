package com.tech.oma.myshoes.dataobjects;

import java.util.Date;

/**
 * Created by Whisler on 25.02.2018.
 */

public class Shoe {

    private String oid;

    private int id;

    private String titel;

    private String description;

    private String imagePath;

    private String tag;

    private double price;

    private Date erzeugt;


    public Shoe(int id, String titel, String description, String imagePath, String tag, double price) {
        this.oid = "SHOE-"+id;
        this.erzeugt = new Date();
        this.id = id;
        this.titel = titel;
        this.description = description;
        this.imagePath = imagePath;
        this.tag = tag;
        this.price = price;
    }

    public String getOid() {
        return oid;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getErzeugt() {
        return erzeugt;
    }

    public void setErzeugt(Date erzeugt) {
        this.erzeugt = erzeugt;
    }
}
