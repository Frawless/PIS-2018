package cz.vut.fit.pis.bakery.bakery.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usersorder")
public class UsersOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "orderdate")
    private Date orderDate;

    @NotNull
    @Pattern(regexp = "(PENDING|ACCEPTED|DONE|IN_PROCESS)")
    @Column(name = "state")
    private String state;

    @JsonBackReference(value = "users-order")
    @ManyToOne
    @JoinColumn(name = "bakeryuser")
    private BakeryUser bakeryUser;

    @JsonBackReference(value = "orders-car")
    @ManyToOne
    @JoinColumn(name = "car")
    private Car car;

    @JsonManagedReference(value = "orders-item")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    private List<Item> items;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
//
//    public Set<Catalog> getProducts() {
//        return products;
//    }
//
//    public void setProducts(Set<Catalog> products) {
//        this.products = products;
//    }
}
