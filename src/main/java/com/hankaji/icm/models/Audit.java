package com.hankaji.icm.models;

import com.hankaji.icm.models.customer.Customer;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "audits")
public class Audit {
    @Id
    private UUID id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "description")
    private String description;

    public Audit() {
    }

    public Audit(User user, String description) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
