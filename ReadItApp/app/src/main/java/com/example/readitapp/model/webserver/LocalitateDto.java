package com.example.readitapp.model.webserver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocalitateDto {

    @SerializedName("idLocalitate")
    @Expose
    private Integer idLocalitate;

    @SerializedName("nume")
    @Expose
    private String nume;

    @SerializedName("idJudet")
    @Expose
    private Integer idJudet;

    public Integer getIdLocalitate() {
        return idLocalitate;
    }

    public void setIdLocalitate(Integer idLocalitate) {
        this.idLocalitate = idLocalitate;
    }

    @Override
    public String toString() {
        return nume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getIdJudet() {
        return idJudet;
    }
}
