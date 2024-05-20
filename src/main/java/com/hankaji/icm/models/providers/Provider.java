package com.hankaji.icm.models.providers;

import com.hankaji.icm.models.User;
import com.hankaji.icm.models.customer.PolicyOwner;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "providers")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "is_manager")
    private Boolean isManager;

    @OneToMany(mappedBy = "provider")
    List<PolicyOwner> policyOwners;

    public Provider(User user, String companyName, Boolean isManager) {
        this.user = user;
        this.companyName = companyName;
        this.isManager = isManager;
    }

    public Provider() {
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getId() {
        return id;
    }
}
