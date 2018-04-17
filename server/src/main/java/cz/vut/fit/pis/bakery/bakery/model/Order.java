package cz.vut.fit.pis.bakery.bakery.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "\"order\"")
public class Order extends ID{

    @Column(name = "createdate")
    private Date createDate;

    @NotNull
    @Column(name = "exportdate")
    private Date exportDate;


    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state = State.ACCEPTED;

    @NotNull
    @JsonBackReference(value = "users-order")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @NotNull
    @JsonManagedReference(value = "orders-item")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    private List<Item> items;


    @NotNull
    @Embedded
    private Address address;


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExportDate() { return exportDate; }

    public void setExportDate(Date exportDate) { this.exportDate = exportDate; }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
