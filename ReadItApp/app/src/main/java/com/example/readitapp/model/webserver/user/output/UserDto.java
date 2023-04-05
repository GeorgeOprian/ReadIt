package com.example.readitapp.model.webserver.user.output;

import com.example.readitapp.model.webserver.user.input.UserCreateDto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDto extends UserCreateDto {

    @SerializedName("userId")
    @Expose
    private Integer userId;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}