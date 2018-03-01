package cz.vut.fit.pis.bakery.bakery.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usersorder")
public class UsersOrder extends ID{

    @Column(name = "isDelivery")
    private Boolean isDelivery = false;

    @Column(name = "orderdate")
    private Date orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state = State.ACCEPTED;

    @NotNull
    @JsonBackReference(value = "users-order")
    @ManyToOne
    @JoinColumn(name = "bakeryuser")
    private BakeryUser bakeryUser;

    @JsonBackReference(value = "orders-car")
    @ManyToOne
    @JoinColumn(name = "car")
    private Car car;

    @NotNull
    @JsonManagedReference(value = "orders-item")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    private List<Item> items;


    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }


    public BakeryUser getBakeryUser() {
        return bakeryUser;
    }

    public void setBakeryUser(BakeryUser bakeryUser) {
        this.bakeryUser = bakeryUser;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Boolean getDelivery() {
        return isDelivery;
    }

    public void setDelivery(Boolean delivery) {
        isDelivery = delivery;
    }
}
