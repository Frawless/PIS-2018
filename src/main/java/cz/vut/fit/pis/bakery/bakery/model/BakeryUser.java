package cz.vut.fit.pis.bakery.bakery.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "bakeryuser")
public class BakeryUser {

    @Id
    @Email
    @Column(name = "email")
    private String email;


    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "surname")
    private String surname;


    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_email")},
            inverseJoinColumns = {@JoinColumn(name = "role")}
    )
    private List<Role> roles;

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @JsonManagedReference(value = "users-order")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bakeryUser", cascade = CascadeType.ALL)
    private List<UsersOrder> usersOrders;




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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
