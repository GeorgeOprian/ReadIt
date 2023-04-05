package com.example.readitapp.model.webserver.user.input;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCreateDto {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("userName")
    @Expose
    private String userName;

    public UserCreateDto() {
    }

    public UserCreateDto(String email, String userName) {
        this.email = email;
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
