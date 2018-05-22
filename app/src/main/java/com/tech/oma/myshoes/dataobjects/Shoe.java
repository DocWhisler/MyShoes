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

    private Date created;


    public Shoe(int id, String titel, String description, String imagePath, String tag, double price) {
        this.oid = "SHOE-"+id;
        this.created = new Date();
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

    protected void setOid(String oid) {
        this.oid = oid;
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    protected void setTitel(String titel) {
        this.titel = titel;
    }

    public String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    protected void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTag() {
        return tag;
    }

    protected void setTag(String tag) {
        this.tag = tag;
    }

    public double getPrice() {
        return price;
    }

    protected void setPrice(double price) {
        this.price = price;
    }

    public Date getCreated() {
        return created;
    }

    protected void setCreated(Date created) {
        this.created = created;
    }
}
