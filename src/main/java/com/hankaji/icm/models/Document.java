package com.hankaji.icm.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "documents_url")
public class Document {
    @Id
    private UUID id;
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "claim_id")
    private Claim claim;
    @Column(name = "title")
    private String title;
    @Column(name = "url")
    private String url;

    public Document() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
