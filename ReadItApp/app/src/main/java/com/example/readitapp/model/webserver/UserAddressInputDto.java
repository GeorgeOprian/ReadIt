package com.example.readitapp.model.webserver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserAddressInputDto {

    @SerializedName("userEmail")
    @Expose
    private String userEmail;

    @SerializedName("strada")
    @Expose
    private String strada;

    @SerializedName("numar")
    @Expose
    private Integer numar;

    @SerializedName("bloc")
    @Expose
    private String bloc;

    @SerializedName("scara")
    @Expose
    private String scara;

    @SerializedName("numarApartament")
    @Expose
    private Integer numarApartament;

    @SerializedName("idLocalitate")
    @Expose
    private Integer idLocalitate;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public Integer getNumar() {
        return numar;
    }

    public void setNumar(Integer numar) {
        this.numar = numar;
    }

    public String getBloc() {
        return bloc;
    }

    public void setBloc(String bloc) {
        this.bloc = bloc;
    }

    public String getScara() {
        return scara;
    }

    public void setScara(String scara) {
        this.scara = scara;
    }

    public Integer getNumarApartament() {
        return numarApartament;
    }

    public void setNumarApartament(Integer numarApartament) {
        this.numarApartament = numarApartament;
    }

    public Integer getIdLocalitate() {
        return idLocalitate;
    }

    public void setIdLocalitate(Integer idLocalitate) {
        this.idLocalitate = idLocalitate;
    }
}
