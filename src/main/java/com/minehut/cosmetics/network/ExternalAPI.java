package com.minehut.cosmetics.network;

import com.google.gson.Gson;
import com.minehut.cosmetics.config.Config;
import com.minehut.cosmetics.model.PackInfo;
import com.minehut.cosmetics.model.gson.GsonModel;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
import com.minehut.cosmetics.model.rank.PlayerRank;
import kong.unirest.HttpMethod;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ExternalAPI extends CosmeticsAPI {

    private static final Gson gson = new Gson();

    public ExternalAPI(Config config) {
        super(config);
    }

    @Override
    public Optional<PackInfo> getPackInfo() {
        return requestType(HttpMethod.GET, "/network/resourcepacks/info", PackInfo.class);
    }

    @Override
    public Optional<CosmeticProfileResponse> getProfile(UUID uuid) {
        return requestType(HttpMethod.GET, "/cosmetics/profile/{uuid}", CosmeticProfileResponse.class, (req) ->
                req.routeParam("uuid", uuid.toString())
        );
    }

    @Override
    public void equipCosmetic(UUID uuid, String category, String id) {
        // equip cosmetic is not possible from an external server
    }

    @Override
    public List<PlayerRank> getRanks() {
        final String body = request(HttpMethod.GET, "/network/ranks");

        try {
            PlayerRank[] ranks = GsonModel.RANK.fromJson(body, PlayerRank[].class);

            if (ranks.length == 0) {
                return List.of();
            }

            return List.of(ranks);
        } catch (Exception e) {
            return List.of();
        }

    }
}
