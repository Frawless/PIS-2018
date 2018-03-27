package cz.vut.fit.pis.bakery.bakery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "address")
public class Address extends ID {

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

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address", cascade = CascadeType.ALL)
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
