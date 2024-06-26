package com.hankaji.icm.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "email")
    private String email;

    @Column(name = "encrypted_password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Roles role;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Audit> audits;

    public User() {
    }

    public User(UUID id, String fullname, String email, String password, Roles role) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String fullname, String email, String password) {
        this.id = UUID.randomUUID();
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public User(String fullname, String email, String password, Roles role) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static enum Roles {
        ADMIN,
        DEPENDENT,
        POLICY_HOLDER,
        POLICY_OWNER,
        PROVIDER
    }
}