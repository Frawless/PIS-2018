package cz.vut.fit.pis.bakery.bakery.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @Max(10)
    @Column(name = "ordered_amount")
    private int orderedAmount;


    @Column(name = "date_of_cooking")
    private Date dateOfCooking;

    @Column(name = "best_before")
    private Date bestBefore;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;

    @JsonBackReference(value = "orders-item")
    @ManyToOne
    @JoinColumn(name = "order_id")
    @NotNull
    private UsersOrder order;


    public int getOrderedAmount() {
        return orderedAmount;
    }

    public void setOrderedAmount(int orderedAmount) {
        this.orderedAmount = orderedAmount;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public UsersOrder getOrder() {
        return order;
    }

    public void setOrder(UsersOrder order) {
        this.order = order;
    }
}
