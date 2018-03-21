package cz.vut.fit.pis.bakery.bakery.model;

import javax.persistence.*;

@MappedSuperclass
public abstract class ID {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
