package com.minehut.cosmetics.modules.polling;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.model.PackInfo;

import java.util.Optional;

public class ResourcePackPollingModule extends PollingModule<PackInfo> {

    private final Cosmetics cosmetics;

    public ResourcePackPollingModule(Cosmetics cosmetics) {
        super(cosmetics, new PackInfo("", ""), 20 * 60 * 5);
        this.cosmetics = cosmetics;
    }

    @Override
    public Optional<PackInfo> poll() {
        return Optional.ofNullable(cosmetics.api().getPackInfo().join().getBody());
    }
}
