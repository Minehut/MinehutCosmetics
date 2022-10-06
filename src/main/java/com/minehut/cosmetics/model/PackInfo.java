package com.minehut.cosmetics.model;

public class PackInfo {
    private String url;
    private String sha1;

    public PackInfo(String url, String sha1) {
        this.url = url;
        this.sha1 = sha1;
    }

    public boolean isValid() {
        return sha1 != null
                && url != null
                && !sha1.isEmpty()
                && !url.isEmpty();
    }

    public String getUrl() {
        return this.url;
    }

    public String getSha1() {
        return this.sha1;
    }

    public String toString() {
        return "PackInfo{name='" + this.url + "', sha1='" + this.sha1 + "'}";
    }
}
