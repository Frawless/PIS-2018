package cz.vut.fit.pis.bakery.bakery.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "car")
public class Car extends ID {

    @Column(name = "date_of_acquire")
    private Date dateofAcquire;

    @Column(name = "type")
    private String type;


    @JsonManagedReference(value = "orders-car")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "car", cascade = CascadeType.ALL)
    private List<UsersOrder> orders = new ArrayList<>();

    public Date getDateofAcquire() {
        return dateofAcquire;
    }

    public void setDateofAcquire(Date dateofAcquire) {
        this.dateofAcquire = dateofAcquire;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<UsersOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<UsersOrder> orders) {
        this.orders = orders;
    }
}
