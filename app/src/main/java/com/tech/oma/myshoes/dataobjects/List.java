package com.tech.oma.myshoes.dataobjects;

import java.util.Date;

public class List {

    private String oid;

    private String id;

    private String name;

    private Date erzeugt;

    public List(String id, String name){
        this.oid = "List-"+id;
        this.erzeugt = new Date();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getErzeugt() {
        return erzeugt;
    }

    public void setErzeugt(Date erzeugt) {
        this.erzeugt = erzeugt;
    }
}
