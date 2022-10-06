package com.minehut.cosmetics.model.rank;

import java.util.Arrays;

public class Rank {

    private String id;
    private int ordinal;
    private String prefixColor;
    private String prefix;
    private int chatDelaySeconds;
    private String[] extraPermissions;
    private String[] extraAuras;

    public Rank(String id, int ordinal, String prefixColor, String prefix, int chatDelaySeconds, String[] extraPermissions, String[] extraAuras) {
        this.id = id;
        this.ordinal = ordinal;
        this.prefixColor = prefixColor;
        this.prefix = prefix;
        this.chatDelaySeconds = chatDelaySeconds;
        this.extraPermissions = extraPermissions;
        this.extraAuras = extraAuras;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public String getPrefixColor() {
        return prefixColor;
    }

    public void setPrefixColor(String prefixColor) {
        this.prefixColor = prefixColor;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getChatDelaySeconds() {
        return chatDelaySeconds;
    }

    public void setChatDelaySeconds(int chatDelaySeconds) {
        this.chatDelaySeconds = chatDelaySeconds;
    }

    public String[] getExtraPermissions() {
        return extraPermissions;
    }

    public void setExtraPermissions(String[] extraPermissions) {
        this.extraPermissions = extraPermissions;
    }

    public String[] getExtraAuras() {
        return extraAuras;
    }

    public void setExtraAuras(String[] extraAuras) {
        this.extraAuras = extraAuras;
    }

    @Override
    public String toString() {
        return "Rank{" +
                "id='" + id + '\'' +
                ", ordinal=" + ordinal +
                ", prefixColor='" + prefixColor + '\'' +
                ", prefix='" + prefix + '\'' +
                ", chatDelaySeconds=" + chatDelaySeconds +
                ", extraPermissions=" + Arrays.toString(extraPermissions) +
                ", extraAuras=" + Arrays.toString(extraAuras) +
                '}';
    }
}
