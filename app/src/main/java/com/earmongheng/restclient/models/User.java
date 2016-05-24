package com.earmongheng.restclient.models;

import java.io.Serializable;

/**
 * Created by earmongheng on 5/1/2016.
 */
public class User implements Serializable{

    private int userid;
    private String username;
    private String password;
    private String telephone;
    private String email;

    public User() {
    }

    public User(String username, String password, String email, String telephone) {
        this.username = username;
        this.telephone = telephone;
        this.password = password;
        this.email = email;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
