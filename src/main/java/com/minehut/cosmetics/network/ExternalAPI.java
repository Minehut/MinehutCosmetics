package com.minehut.cosmetics.network;

import com.google.gson.Gson;
import com.minehut.cosmetics.config.Config;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.model.PackInfo;
import com.minehut.cosmetics.model.profile.ConsumeResponse;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
import com.minehut.cosmetics.model.profile.SimpleResponse;
import com.minehut.cosmetics.model.rank.PlayerRank;
import kong.unirest.HttpMethod;
import kong.unirest.HttpResponse;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ExternalAPI extends CosmeticsAPI {


    public ExternalAPI(Config config, Gson gson) {
        super(config, gson);
    }

    @Override
    public CompletableFuture<HttpResponse<PackInfo>> getPackInfo() {
        return requestType(HttpMethod.GET, "/network/resourcepacks/info", PackInfo.class);
    }

    @Override
    public CompletableFuture<HttpResponse<CosmeticProfileResponse>> getProfile(UUID uuid) {
        return requestType(HttpMethod.GET, "/cosmetics/profile/{uuid}", CosmeticProfileResponse.class, (req) ->
            req.routeParam("uuid", uuid.toString())
        );
    }

    @Override
    public CompletableFuture<HttpResponse<SimpleResponse>> equipCosmetic(UUID uuid, String category, String id) {
        // stub this because equipCosmetic cannot be accessed by player servers
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<PlayerRank[]> getRanks() {
        return requestString(HttpMethod.GET, "/network/ranks").thenApplyAsync(response -> gson().fromJson(response.getBody(), PlayerRank[].class));
    }

    @Override
    public CompletableFuture<HttpResponse<ConsumeResponse>> consumeCosmetic(UUID uuid, CosmeticCategory category, String id, int quantity) {
        // cannot be called from player servers so we stub this
        return CompletableFuture.completedFuture(null);
    }
}
