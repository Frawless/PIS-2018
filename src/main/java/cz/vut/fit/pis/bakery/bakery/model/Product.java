package cz.vut.fit.pis.bakery.bakery.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "energy_value")
    private String energyValue;

    @Column(name = "date_of_cooking")
    private Date dateOfCooking;

    @Column(name = "best_before")
    private Date bestBefore;

    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "products")
    private Set<UsersOrder> orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnergyValue() {
        return energyValue;
    }

    public void setEnergyValue(String energyValue) {
        this.energyValue = energyValue;
    }

    public Date getDateOfCooking() {
        return dateOfCooking;
    }

    public void setDateOfCooking(Date dateOfCooking) {
        this.dateOfCooking = dateOfCooking;
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

    public Set<UsersOrder> getOrders() {
        return orders;
    }

    public void setOrders(Set<UsersOrder> orders) {
        this.orders = orders;
    }
}
