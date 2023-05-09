package com.minehut.cosmetics.modules.polling;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.model.rank.PlayerRank;
import kong.unirest.HttpResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RankPollingModule extends PollingModule<List<PlayerRank>> {

    private boolean success = false;
    private final Cosmetics cosmetics;

    public RankPollingModule(Cosmetics cosmetics) {
        super(cosmetics, List.of(), 20 * 30);
        this.cosmetics = cosmetics;
    }

    @Override
    public Optional<List<PlayerRank>> poll() {
        if (success) {
            return Optional.empty();
        }

        final HttpResponse<PlayerRank[]> response = cosmetics.api().getRanks().join();

        if (!response.isSuccess()) {
            return Optional.empty();
        }


        final PlayerRank[] ranks = response.getBody();

        if (ranks.length == 0) {
            return Optional.empty();
        }

        this.success = true;
        return Optional.of(Arrays.asList(ranks));
    }

    @Override
    protected void process(List<PlayerRank> state) {
        PlayerRank.registerRanks(state);
        cancel();
    }
}
