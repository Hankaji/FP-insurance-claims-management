package com.hankaji.icm.models.providers;

public class Provider {
    private String id;
    private String providerName;

    public Provider(String id, String providerName) {
        this.id = id;
        this.providerName = providerName;
    }

    public String getId() {
        return id;
    }

    public String getProviderName() {
        return providerName;
    }
}
