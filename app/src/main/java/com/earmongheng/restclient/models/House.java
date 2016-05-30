package com.earmongheng.restclient.models;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by earmongheng on 5/1/2016.
 */
public class House implements Serializable{

    private int houseid;
    private float price;
    private float deposit;
    private String description;
    private double latitude;
    private double longtitude;
    private String address;
    private String picture;

    private Type type;
    private User user;
    private Set<Question> question;

    public House() {
    }

    public House(int houseid, float price, float deposit, String description, double latitude, double longtitude, String address, String picture) {
        this.houseid = houseid;
        this.price = price;
        this.deposit = deposit;
        this.description = description;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.address = address;
        this.picture = picture;
    }

    public int getHouseid() {
        return houseid;
    }

    public void setHouseid(int houseid) {
        this.houseid = houseid;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Question> getQuestion() {
        return question;
    }

    public void setQuestion(Set<Question> question) {
        this.question = question;
    }
}
