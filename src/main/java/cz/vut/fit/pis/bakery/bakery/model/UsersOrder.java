package cz.vut.fit.pis.bakery.bakery.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "bakeryuser")
    private BakeryUser bakeryUser;

    @ManyToOne
    @JoinColumn(name = "car")
    private Car car;

    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = {@JoinColumn(name = "users_order")},
            inverseJoinColumns = {@JoinColumn(name = "product")}
    )
    private Set<Product> products;


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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
