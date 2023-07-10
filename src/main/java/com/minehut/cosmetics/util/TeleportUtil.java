package com.minehut.cosmetics.util;

import com.google.common.collect.ImmutableList;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.List;

public class TeleportUtil {

    public static void teleport(Entity entity, Location location) {
        List<Entity> passengers = ImmutableList.copyOf(entity.getPassengers());

        // remove all passengers
        entity.eject();

        entity.teleport(location);

        for (Entity passenger : passengers) {
            entity.addPassenger(passenger);
        }
    }
}
