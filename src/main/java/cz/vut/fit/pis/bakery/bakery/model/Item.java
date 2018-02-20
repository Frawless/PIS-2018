package cz.vut.fit.pis.bakery.bakery.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ordered_amount")
    private int orderedAmount;

    @Column(name = "date_of_cooking")
    private Date dateOfCooking;

    @Column(name = "best_before")
    private Date bestBefore;

    @ManyToOne
    @JoinColumn(name = "catalog")
    private Catalog catalog;

    @ManyToOne
    @JoinColumn(name = "order_id")
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

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public UsersOrder getOrder() {
        return order;
    }

    public void setOrder(UsersOrder order) {
        this.order = order;
    }
}
