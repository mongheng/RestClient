package com.earmongheng.restclient.models;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by earmongheng on 5/1/2016.
 */
public class House implements Serializable{

    private int houseid;
    private float price;
    private float deposite;
    private String description;
    private double latitute;
    private double longtitute;
    private String picture;

    private Type type;
    private User user;
    private Set<Question> question;

    public House() {
    }

    public House(int houseid, float price, float deposite, String description, double latitute, double longtitute, String picture) {
        this.houseid = houseid;
        this.price = price;
        this.deposite = deposite;
        this.description = description;
        this.latitute = latitute;
        this.longtitute = longtitute;
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

    public float getDeposite() {
        return deposite;
    }

    public void setDeposite(float deposite) {
        this.deposite = deposite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitute() {
        return latitute;
    }

    public void setLatitute(double latitute) {
        this.latitute = latitute;
    }

    public double getLongtitute() {
        return longtitute;
    }

    public void setLongtitute(double longtitute) {
        this.longtitute = longtitute;
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
