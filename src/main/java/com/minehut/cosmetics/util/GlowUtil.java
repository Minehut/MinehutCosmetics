package com.minehut.cosmetics.util;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Entity;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

public class GlowUtil {
    public void setColoredGlow(@NotNull Entity entity, @NotNull Scoreboard scoreboard, @NotNull NamedTextColor color) {
        entity.setGlowing(true);
        getGlowColorTeam(scoreboard, color).addEntry(entity.getUniqueId().toString());
    }

    public Team getGlowColorTeam(@NotNull Scoreboard scoreboard, @NotNull NamedTextColor color) {
        final String identifier = "g_" + color.asHexString();

        Team team = scoreboard.getTeam(identifier);

        if (team == null) {
            team = scoreboard.registerNewTeam(identifier);
        }
        team.color(color);
        return team;
    }
}
