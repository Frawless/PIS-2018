package cz.vut.fit.pis.bakery.bakery.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "item")
public class Item extends ID {


    @Min(1)
    @Max(10)
    @NotNull
    @Column(name = "count_ordered")
    private int countOrdered;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonBackReference(value = "orders-item")
    @ManyToOne
    @JoinColumn(name = "order_id")
    @NotNull
    private Order order;


    public int getCountOrdered() {
        return countOrdered;
    }

    public void setCountOrdered(int countOrdered) {
        this.countOrdered = countOrdered;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
