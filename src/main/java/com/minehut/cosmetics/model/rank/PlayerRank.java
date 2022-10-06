package com.minehut.cosmetics.model.rank;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class PlayerRank {

    public final static PlayerRank DEFAULT = new PlayerRank(
            "DEFAULT",
            18,
            NamedTextColor.GRAY,
            "",
            5,
            new ArrayList<>(),
            new ArrayList<>()
    );

    private final static Map<String, PlayerRank> backingRanks = Maps.newHashMap();
    private transient Set<String> permissions = Sets.newHashSet();

    private String id;
    private int ordinal;

    private TextColor prefixColor;
    private String prefix;
    private int chatDelaySeconds;
    private List<String> extraPermissions;
    private List<String> extraAuras;

    public PlayerRank(String id, int ordinal, TextColor prefixColor, String prefix, int chatDelaySeconds, List<String> extraPermissions, List<String> extraAuras) {
        this.id = id;
        this.ordinal = ordinal;
        this.prefixColor = prefixColor;
        this.prefix = prefix;
        this.chatDelaySeconds = chatDelaySeconds;
        this.extraPermissions = extraPermissions;
        this.extraAuras = extraAuras;
    }

    public static PlayerRank fromRank(Rank rank){

        TextColor prefixColor = NamedTextColor.NAMES.value(rank.getPrefixColor().toLowerCase(Locale.ROOT));
        if(prefixColor == null){
            prefixColor = NamedTextColor.GRAY;
        }

        return new PlayerRank(
                rank.getId(),
                rank.getOrdinal(),
                prefixColor,
                rank.getPrefix(),
                rank.getChatDelaySeconds(),
                Arrays.asList(rank.getExtraPermissions()),
                Arrays.asList(rank.getExtraAuras())
        );
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
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

    public TextColor getPrefixColor() {
        return prefixColor;
    }

    public void setPrefixColor(TextColor prefixColor) {
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

    public List<String> getExtraPermissions() {
        return extraPermissions;
    }

    public void setExtraPermissions(List<String> extraPermissions) {
        this.extraPermissions = extraPermissions;
    }

    public List<String> getExtraAuras() {
        return extraAuras;
    }

    public void setExtraAuras(List<String> extraAuras) {
        this.extraAuras = extraAuras;
    }

    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }

    public boolean hasAccess(PlayerRank rank) {
        return rank.ordinal >= ordinal;
    }

    public boolean hasAccess(String rank) {
        return getBackingRank(rank).map(this::hasAccess).orElse(false);
    }

    public static void clearBackingRanks(){
        backingRanks.clear();
    }

    public Set<String> getPermissions() {
        if (permissions == null) permissions = Sets.newHashSet();
        return permissions;
    }

    private void register() {
        backingRanks.put(id, this);
    }

    private void registerPermissions() {
        Set<String> perms = getPermissions();
        perms.addAll(extraPermissions);
        backingRanks.values().stream().filter(this::hasAccess).forEach(rank -> perms.add("minehut.rank." + rank.id.toLowerCase(Locale.ROOT)));
        permissions.addAll(perms);
    }

    public static Collection<PlayerRank> getRanks() {
        return ImmutableList.copyOf(backingRanks.values());
    }

    public static Optional<PlayerRank> getBackingRank(String id) {
        return Optional.ofNullable(backingRanks.getOrDefault(id.toUpperCase(Locale.ROOT), null));
    }

    public static void registerRanks(List<PlayerRank> playerRanks) {
        for (PlayerRank playerRank : playerRanks) {
            playerRank.register();
        }
        for (PlayerRank playerRank : playerRanks) {
            playerRank.registerPermissions();
        }
    }

    public Component getFormattedPrefix() {
        return Component.text(prefix, getPrefixColor());
    }

    public Component getFormattedName(String name) {
        return Component.text(prefix + name, getPrefixColor());
    }

    public Component getFormattedName(String name, TextColor color) {
        return Component.text(prefix, getPrefixColor()).append(Component.text(name, color));
    }

    @Override
    public String toString() {
        return "PlayerRank{" +
                "permissions=" + permissions +
                ", id='" + id + '\'' +
                ", ordinal=" + ordinal +
                ", prefixColor=" + prefixColor +
                ", prefix='" + prefix + '\'' +
                ", chatDelaySeconds=" + chatDelaySeconds +
                ", extraPermissions=" + extraPermissions +
                ", extraAuras=" + extraAuras +
                '}';
    }
}
