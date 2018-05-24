package com.tech.oma.myshoes.dataobjects;

import java.util.Date;

public class ShoeList {

    private String oid;

    private int id;

    private String name;

    private Date created;

    private boolean aktiv;

    public ShoeList(int id, String name, boolean aktiv){
        this.oid = "LIST-"+id;
        this.created = new Date();
        this.id = id;
        this.name = name;
        this.aktiv = aktiv;
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

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    protected void setCreated(Date created) {
        this.created = created;
    }

    public boolean isAktiv() {
        return aktiv;
    }

    protected void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }
}
