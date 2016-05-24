package com.earmongheng.restclient.models;

import java.io.Serializable;

/**
 * Created by earmongheng on 5/1/2016.
 */
public class Question implements Serializable{

    private int questionid;
    private String title;
    private String message;

    private User user;
    private House house;

    public Question() {
    }

    public Question(int questionid, String title, String message) {
        this.questionid = questionid;
        this.title = title;
        this.message = message;
    }

    public int getQuestionid() {
        return questionid;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
