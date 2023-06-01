package com.example.readitapp.model.webserver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressFullDto {

    @SerializedName("adresa")
    @Expose
    private AddressDto address;

    @SerializedName("localitate")
    @Expose
    private LocalitateDto localitate;

    @SerializedName("judet")
    @Expose
    private JudetDto judet;

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public LocalitateDto getLocalitate() {
        return localitate;
    }

    public void setLocalitate(LocalitateDto localitate) {
        this.localitate = localitate;
    }

    public JudetDto getJudet() {
        return judet;
    }

    public void setJudet(JudetDto judet) {
        this.judet = judet;
    }

    public boolean isEmpty() {
        if (address != null) {
            if(address.getStrada() != null) {
                return false;
            }
        } else if (localitate != null) {
            if (localitate.getNume() != null) {
                return false;
            }
        } else if (judet != null) {
            if(judet.getNume() != null) {
                return false;
            }
        }
        return true;
    }
}
