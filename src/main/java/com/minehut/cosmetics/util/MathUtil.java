package com.minehut.cosmetics.util;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public final class MathUtil {
    private MathUtil() {
    }

    public static Vector rotateVector(Vector v, Location loc) {
        double yaw = loc.getYaw() / 180.0D * 3.141592653589793D;
        double pitch = loc.getPitch() / 180.0D * 3.141592653589793D;
        v = rotateXAxis(v, pitch);
        v = rotateYAxis(v, -yaw);
        return v;
    }

    public static Vector rotateXAxis(Vector v, double a) {
        double y = Math.cos(a) * v.getY() - Math.sin(a) * v.getZ();
        double z = Math.sin(a) * v.getY() + Math.cos(a) * v.getZ();
        return v.setY(y).setZ(z);
    }

    public static Vector rotateYAxis(Vector v, double b) {
        double x = Math.cos(b) * v.getX() + Math.sin(b) * v.getZ();
        double z = -Math.sin(b) * v.getX() + Math.cos(b) * v.getZ();
        return v.setX(x).setZ(z);
    }

    public static Vector rotateZAxis(Vector v, double c) {
        double x = Math.cos(c) * v.getX() - Math.sin(c) * v.getY();
        double y = Math.sin(c) * v.getX() + Math.cos(c) * v.getY();
        return v.setX(x).setY(y);
    }

}
