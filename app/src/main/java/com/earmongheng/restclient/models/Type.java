package com.earmongheng.restclient.models;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by earmongheng on 5/1/2016.
 */
public class Type implements Serializable{

    private int typeid;
    private int bedroom;
    private int bathroom;

    private EnumType enumType;
    private Set<House> houses;

    public Type() {
    }

    public Type(int typeid, int bedroom, int bathroom) {
        this.typeid = typeid;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public int getBedroom() {
        return bedroom;
    }

    public void setBedroom(int bedroom) {
        this.bedroom = bedroom;
    }

    public int getBathroom() {
        return bathroom;
    }

    public void setBathroom(int bathroom) {
        this.bathroom = bathroom;
    }

    public EnumType getEnumType() {
        return enumType;
    }

    public void setEnumType(EnumType enumType) {
        this.enumType = enumType;
    }

    public Set<House> getHouses() {
        return houses;
    }

    public void setHouses(Set<House> houses) {
        this.houses = houses;
    }
}
