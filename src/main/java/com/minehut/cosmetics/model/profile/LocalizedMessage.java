package com.minehut.cosmetics.model.profile;

import java.util.Arrays;
import java.util.List;

public class LocalizedMessage {
    private String message;
    private List<String> inserts;

    public LocalizedMessage(String message, String... inserts) {
        this.message = message;
        this.inserts = Arrays.asList(inserts);
    }

    public LocalizedMessage(final String message, final List<String> inserts) {
        this.message = message;
        this.inserts = inserts;
    }

    public String getMessage() {
        return this.message;
    }

    public List<String> getInserts() {
        return this.inserts;
    }
}
