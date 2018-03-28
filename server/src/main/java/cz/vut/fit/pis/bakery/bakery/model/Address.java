package cz.vut.fit.pis.bakery.bakery.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Embeddable
public class Address {

    @NotNull
    @Column(name = "city")
    private String city;

    @NotNull
    @Column(name = "psc")
    private String psc;

    @NotNull
    @Column(name = "streetName")
    private String streetName;

    @NotNull
    @Column(name = "streetNumber")
    private int streetNumber;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPsc() {
        return psc;
    }

    public void setPsc(String psc) {
        this.psc = psc;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

}
