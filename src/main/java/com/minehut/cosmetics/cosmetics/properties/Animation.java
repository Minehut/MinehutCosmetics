package com.minehut.cosmetics.cosmetics.properties;

import java.util.List;

public class Animation {
    private List<Integer> FRAMES;
    private int idx;

    public Animation(List<Integer> FRAMES) {
        this.FRAMES = FRAMES;
    }

    public int getNextFrame() {
        int frame = FRAMES.get(idx);

        idx++;
        if (idx == FRAMES.size()) {
            idx = 0;
        }

        return frame;
    }
}
