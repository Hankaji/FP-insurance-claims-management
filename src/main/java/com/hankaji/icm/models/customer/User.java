package com.hankaji.icm.models.customer;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "fullname")
    private String fullname;
    private String email;
    @Column(name = "encrypted_password")
    private String password;
    public User() {
    }
    public User(String fullname, String email, String password) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }
}
