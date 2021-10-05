package com.vula.vulkan.com.lcapp;

public class LuckyCoinOffer {
    String name;
    String url;

    public LuckyCoinOffer() {
    }

    public LuckyCoinOffer(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
