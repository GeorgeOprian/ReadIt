package com.example.readitapp.model.webserver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public class SubscriptionDto {

    @SerializedName("subscriptionId")
    @Expose
    private Integer subscriptionId;

    @NotNull
    @SerializedName("startDate")
    @Expose
    private LocalDate startDate;

    @NotNull
    @SerializedName("endDate")
    @Expose
    private LocalDate endDate;

    @NotNull
    @SerializedName("userEmail")
    @Expose
    private String userEmail;
}
