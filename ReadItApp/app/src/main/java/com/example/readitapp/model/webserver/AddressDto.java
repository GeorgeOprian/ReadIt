package com.example.readitapp.model.webserver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressDto {

    @SerializedName("idAdresa")
    @Expose
    private Integer idAdresa;

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

    public Integer getIdAdresa() {
        return idAdresa;
    }

    public void setIdAdresa(Integer idAdresa) {
        this.idAdresa = idAdresa;
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
}
