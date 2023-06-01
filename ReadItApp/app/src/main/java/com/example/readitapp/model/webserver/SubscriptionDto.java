package com.example.readitapp.model.webserver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;



public class SubscriptionDto {

    @SerializedName("subscriptionId")
    @Expose
    private Integer subscriptionId;

    @NotNull
    @SerializedName("startDate")
    @Expose
    private String startDate;

    @NotNull
    @SerializedName("endDate")
    @Expose
    private String endDate;

    @NotNull
    @SerializedName("userEmail")
    @Expose
    private String userEmail;


    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    @NotNull
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull String startDate) {
        this.startDate = startDate;
    }

    @NotNull
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull String endDate) {
        this.endDate = endDate;
    }

    @NotNull
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(@NotNull String userEmail) {
        this.userEmail = userEmail;
    }
}
