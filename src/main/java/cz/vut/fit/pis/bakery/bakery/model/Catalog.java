package cz.vut.fit.pis.bakery.bakery.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "catalog")
public class Catalog {
    @Id
    @Column(name = "name")
    private String name;


    @Column(name = "energy_value")
    private String energyValue;


    @Column(name = "total_amount")
    private int totalAmount;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "catalog", cascade = CascadeType.ALL)
    private List<Item> items;


    public String getEnergyValue() {
        return energyValue;
    }

    public void setEnergyValue(String energyValue) {
        this.energyValue = energyValue;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
