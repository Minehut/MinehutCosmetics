package com.minehut.cosmetics.modules.polling;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.model.rank.PlayerRank;
import kong.unirest.HttpResponse;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.Optional;

public class RankPollingModule extends PollingModule<List<PlayerRank>> {

    private final Cosmetics cosmetics;

    public RankPollingModule(Cosmetics cosmetics) {
        super(cosmetics, List.of(), 20 * 30);
        this.cosmetics = cosmetics;
    }

    @Override
    public Optional<List<PlayerRank>> poll() {
        final HttpResponse<PlayerRank[]> response = cosmetics.api().getRanks().join();
        final List<PlayerRank> ranks = List.of(response.getBody());
        if (ranks.isEmpty()) return Optional.empty();
        return Optional.of(ranks);
    }

    @Override
    protected void process(List<PlayerRank> state) {
        PlayerRank.registerRanks(state);
        cancel();
    }
}
