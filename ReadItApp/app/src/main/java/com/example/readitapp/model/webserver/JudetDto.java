package com.example.readitapp.model.webserver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JudetDto {

    @SerializedName("idJudet")
    @Expose
    private Integer idJudet;

    @SerializedName("nume")
    @Expose
    private String nume;

    public Integer getIdJudet() {
        return idJudet;
    }

    public void setIdJudet(Integer idJudet) {
        this.idJudet = idJudet;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return nume;
    }
}
