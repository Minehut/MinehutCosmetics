package com.minehut.cosmetics.util;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ExpiringSet<T> {
    private final long expire;

    private final HashMap<T, Long> map = new HashMap<>();

    public ExpiringSet(long expire, TimeUnit unit) {
        this.expire = unit.toMillis(expire);
    }

    public void add(T key) {
        map.put(key, System.currentTimeMillis() + expire);
    }

    public void remove(T key) {
        map.remove(key);
    }

    public boolean has(T key) {
        final Long expireTime = map.get(key);
        if (expireTime == null) {
            return false;
        }

        if (expireTime < System.currentTimeMillis()) {
            remove(key);
            return false;
        }

        return true;
    }
}
