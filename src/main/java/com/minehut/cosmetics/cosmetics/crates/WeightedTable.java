package com.minehut.cosmetics.cosmetics.crates;

import java.util.HashMap;
import java.util.Map;

public class WeightedTable<T> {

    private final HashMap<T, Integer> table = new HashMap<>();
    private int totalWeight = 0;

    public T roll() {
        if (table.isEmpty()) {
            throw new IllegalStateException("Cannot roll for items on an empty table.");
        }

        int currentWeight = 0;
        int roll = (int) (Math.random() * totalWeight);

        for (final Map.Entry<T, Integer> entry : table.entrySet()) {
            currentWeight += entry.getValue();
            if (roll <= currentWeight) return entry.getKey();
        }

        throw new IllegalStateException("Failed to roll a valid number.");
    }

    public void registerItem(T item, int weight) {
        table.put(item, weight);
        totalWeight += weight;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {
        private final WeightedTable<T> table = new WeightedTable<>();

        public Builder<T> register(T entry, int weight) {
            table.registerItem(entry, weight);
            return this;
        }

        public WeightedTable<T> build() {
            return table;
        }
    }
}