package cz.vut.fit.pis.bakery.bakery.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public class Role {

    @Id
    private String name;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "roles"
    )
    @JsonIgnore
    private List<BakeryUser> users;


    @OneToMany
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BakeryUser> getUsers() {
        return users;
    }

    public void setUsers(List<BakeryUser> users) {
        this.users = users;
    }
}
