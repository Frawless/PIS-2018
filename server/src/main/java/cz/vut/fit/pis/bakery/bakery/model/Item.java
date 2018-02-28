package cz.vut.fit.pis.bakery.bakery.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "item")
public class Item extends ID {


    @Min(1)
    @Max(10)
    @Column(name = "ordered_amount")
    private int orderedAmount;

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
