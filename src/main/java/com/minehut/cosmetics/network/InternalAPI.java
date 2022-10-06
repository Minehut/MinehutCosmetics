package com.minehut.cosmetics.network;

import com.minehut.cosmetics.config.ConfigManager;
import com.minehut.cosmetics.model.PackInfo;
import com.minehut.cosmetics.model.gson.GsonModel;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
import com.minehut.cosmetics.model.rank.PlayerRank;
import kong.unirest.HttpMethod;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InternalAPI extends CosmeticsAPI {

    public InternalAPI(ConfigManager config) {
        super(config);
    }

    @Override
    public Optional<PackInfo> getPackInfo() {
        return requestType(HttpMethod.GET, "/v1/resourcepacks/info", PackInfo.class);
    }

    @Override
    public Optional<CosmeticProfileResponse> getProfile(UUID uuid) {
        return requestType(HttpMethod.GET, "/v1/cosmetics/profile/{uuid}", CosmeticProfileResponse.class, (req) ->
                req.routeParam("uuid", uuid.toString())
        );
    }

    @Override
    public void equipCosmetic(UUID uuid, String category, String id) {
        request(HttpMethod.POST, "/v1/cosmetics/equip/{uuid}/{category}/{id}", (req) ->
                req.routeParam("uuid", uuid.toString())
                        .routeParam("category", category)
                        .routeParam("id", id)
        );
    }

    @Override
    public List<PlayerRank> getRanks() {
        try {
            final String body = request(HttpMethod.GET, "/v1/ranks");


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
