package com.hankaji.icm.models;

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
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id")
    private Provider manager;
    @OneToMany(mappedBy = "manager", fetch = FetchType.EAGER)
    private List<Provider> surveyors;

    @OneToMany(mappedBy = "provider", fetch = FetchType.EAGER)
    List<PolicyOwner> policyOwners;

    public Provider() {
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public List<PolicyOwner> getPolicyOwners() {
        return policyOwners;
    }

    public Provider getManager() {
        return manager;
    }

    public List<Provider> getSurveyors() {
        return surveyors;
    }

    public void setManager(Provider manager) {
        this.manager = manager;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setSurveyors(List<Provider> surveyors) {
        this.surveyors = surveyors;
    }

    public void setPolicyOwners(List<PolicyOwner> policyOwners) {
        this.policyOwners = policyOwners;
    }

    
}
