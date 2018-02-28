package cz.vut.fit.pis.bakery.bakery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ingredient")
public class Ingredient  extends ID{


    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "supplier")
    private String supplier;

    @Column(name = "date_of_manufacture")
    private Date dateOfManufacture;

    @Column(name = "besf_before")
    private Date bestBefore;

    @NotNull
    @Column(name = "stored")
    private int stored;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "ingredients"
            )
    @JsonIgnore
    private Set<Product> products;


    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Date getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(Date dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
    }

    public Date getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(Date bestBefore) {
        this.bestBefore = bestBefore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getStored() {
        return stored;
    }

    public void setStored(int stored) {
        this.stored = stored;
    }



    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
