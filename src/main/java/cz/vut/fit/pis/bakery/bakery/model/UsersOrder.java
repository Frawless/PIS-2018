package cz.vut.fit.pis.bakery.bakery.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

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
}
