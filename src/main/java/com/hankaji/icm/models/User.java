package com.hankaji.icm.models;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table (name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fullname;

    private String email;

    private String encrypted_password;

    private UUID role_id;

    public User(String fullname, String email, String encrypted_password) {
        this.fullname = fullname;
        this.email = email;
        this.encrypted_password = encrypted_password;
    }

    public User() {
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", encrypted_password='" + encrypted_password + '\'' +
                ", role_id=" + role_id +
                '}';
    }
}

