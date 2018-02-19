package cz.vut.fit.pis.bakery.bakery.model;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bakeryuser")
public class BakeryUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;


    @OneToMany(mappedBy = "bakeryUser", cascade = CascadeType.ALL)
    private List<UsersOrder> usersOrders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UsersOrder> getUsersOrders() {
        return usersOrders;
    }

    public void setUsersOrders(List<UsersOrder> usersOrders) {
        this.usersOrders = usersOrders;
    }
}
