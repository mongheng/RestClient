package com.earmongheng.restclient.models;

/**
 * Created by earmongheng on 5/1/2016.
 */
public enum EnumType {

    FLAT("flat"),VILLA("villa"),CONDO("condo");

    private final String typename;

    private EnumType(String typename) {
        // TODO Auto-generated constructor stub
        this.typename = typename;
    }

    public String getTypename() {
        return typename;
    }
}
