package com.example.ruslan.usersservice.userlist;

import com.example.ruslan.usersservice.api.pojo.Result;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ruslan on 19.03.2017.
 */

public class UserItem {

    private String id;
    private String name;
    private String sureName;
    private String city;
    private String gender;
    private String telephoneNumber;
    private String pictureLink;

    public UserItem(Result result) {
        this.id = result.getId().getValue();
        this.name = result.getName().getFirst();
        this.sureName = result.getName().getLast();
        this.city = result.getLocation().getCity();
        this.gender = result.getGender();
        this.pictureLink = result.getPicture().getThumbnail();
        this.telephoneNumber = result.getPhone();
    }

    public UserItem(String name, String sureName, String city, String gender, String telephoneNumber, String pictureLink) {
        this.name = name;
        this.sureName = sureName;
        this.city = city;
        this.gender = gender;
        this.telephoneNumber = telephoneNumber;
        this.pictureLink = pictureLink;
    }

    public String toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id",id);
        jsonObject.put("name",name);
        jsonObject.put("surename",sureName);
        jsonObject.put("city",city);
        jsonObject.put("gender",gender);
        jsonObject.put("picture",pictureLink);
        jsonObject.put("telephone",telephoneNumber);

        return jsonObject.toString();

    }

    public String getName() {
        return name;
    }

    public String getSureName() {
        return sureName;
    }

    public String getCity() {
        return city;
    }

    public String getGender() {
        return gender;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getPictureLink() {
        return pictureLink;
    }
}
