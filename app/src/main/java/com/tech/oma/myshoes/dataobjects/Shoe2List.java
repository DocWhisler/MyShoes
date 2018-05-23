package com.tech.oma.myshoes.dataobjects;

import java.util.Date;

public class Shoe2List {

    private String oid;

    private Shoe shoe;

    private ShoeList shoeList;

    private Date created;

    public Shoe2List(Shoe shoe, ShoeList list){
        this.oid = "S"+shoe.getId()+"L"+list.getId();
        this.created = new Date();
        this.shoe = shoe;
        this.shoeList = list;
    }

    public String getOid() {
        return oid;
    }

    protected void setOid(String oid) {
        this.oid = oid;
    }

    public Shoe getShoe() {
        return shoe;
    }

    protected void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }

    public ShoeList getShoeList() {
        return shoeList;
    }

    protected void setShoeList(ShoeList shoeList) {
        this.shoeList = shoeList;
    }

    public Date getCreated() {
        return created;
    }

    protected void setCreated(Date created) {
        this.created = created;
    }
}
