package com.hankaji.icm.models;

import jakarta.persistence.*;

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

    public enum Roles {
        ADMIN,
        CUSTOMER,
        POLICY_OWNER,
        PROVIDER
    }
}