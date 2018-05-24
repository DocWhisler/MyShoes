package com.tech.oma.myshoes.dataobjects;

import java.util.Date;

public class Shoe2List {

    private String oid;

    private String shoeOid;

    private String shoeListOid;

    private Date created;

    public Shoe2List(Shoe shoe, ShoeList list){
        if(shoe == null || list == null){
            throw new Error("Schuh oder Liste null");
        }

        this.oid = "S"+shoe.getId()+"L"+list.getId();
        this.created = new Date();
        this.shoeOid = shoe.getOid();
        this.shoeListOid = list.getOid();
    }

    public String getOid() {
        return oid;
    }

    protected void setOid(String oid) {
        this.oid = oid;
    }

    public String getShoeOid() {
        return shoeOid;
    }

    protected void setShoeOid(String shoeOid) {
        this.shoeOid = shoeOid;
    }

    public String getShoeListOid() {
        return shoeListOid;
    }

    protected void setShoeListOid(String shoeListOid) {
        this.shoeListOid = shoeListOid;
    }

    public Date getCreated() {
        return created;
    }

    protected void setCreated(Date created) {
        this.created = created;
    }
}
