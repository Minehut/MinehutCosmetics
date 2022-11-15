package com.minehut.cosmetics.model.profile;

public class SimpleResponse {
    private final boolean error;
    private final LocalizedMessage message;

    public SimpleResponse(final boolean error, final LocalizedMessage message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return this.error;
    }

    public LocalizedMessage getMessage() {
        return this.message;
    }
}