package com.minehut.cosmetics.crates;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WeightedTable<T> {

    private final HashMap<T, Integer> table = new HashMap<>();
    private int totalWeight = 0;

    public Optional<T> roll() {
        if (table.isEmpty()) return Optional.empty();

        int currentWeight = 0;
        int roll = (int) (Math.random() * totalWeight);

        for (final Map.Entry<T, Integer> entry : table.entrySet()) {
            currentWeight += entry.getValue();
            if (roll <= currentWeight) return Optional.of(entry.getKey());
        }

        return Optional.empty();
    }

    public void registerItem(T item, int weight) {
        table.put(item, weight);
        totalWeight += weight;
    }
}