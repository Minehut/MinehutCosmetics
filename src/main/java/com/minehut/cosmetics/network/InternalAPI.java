package com.minehut.cosmetics.network;

import com.google.gson.Gson;
import com.minehut.cosmetics.config.Config;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.model.PackInfo;
import com.minehut.cosmetics.model.profile.ConsumeResponse;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
import com.minehut.cosmetics.model.profile.SimpleResponse;
import com.minehut.cosmetics.model.rank.PlayerRank;
import com.minehut.cosmetics.model.request.UnlockCosmeticRequest;
import kong.unirest.HttpMethod;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class InternalAPI extends CosmeticsAPI {

    public InternalAPI(Config config, Gson gson) {
        super(config, gson);
    }

    @Override
    public CompletableFuture<HttpResponse<PackInfo>> getPackInfo() {
        return request(HttpMethod.GET, "/v1/resourcepacks/info")
                .asObjectAsync(PackInfo.class);
    }

    @Override
    public CompletableFuture<HttpResponse<CosmeticProfileResponse>> getProfile(UUID uuid) {
        return request(HttpMethod.GET, "/v1/cosmetics/profile/{uuid}")
                .routeParam("uuid", uuid.toString())
                .asObjectAsync(CosmeticProfileResponse.class);
    }

    @Override
    public CompletableFuture<HttpResponse<SimpleResponse>> equipCosmetic(UUID uuid, String category, String id) {
        return request(HttpMethod.POST, "/v1/cosmetics/equip/{uuid}/{category}/{id}")
                .routeParam("uuid", uuid.toString())
                .routeParam("category", category)
                .routeParam("id", id)
                .asObjectAsync(SimpleResponse.class);
    }

    @Override
    public CompletableFuture<PlayerRank[]> getRanks() {
        return request(HttpMethod.GET, "/v1/ranks")
                .asStringAsync()
                .thenApplyAsync(response -> gson().fromJson(response.getBody(), PlayerRank[].class));
    }

    @Override
    public CompletableFuture<HttpResponse<ConsumeResponse>> consumeCosmetic(UUID uuid, CosmeticCategory category, String id, int quantity) {
        return request(HttpMethod.POST, "/v1/cosmetics/consume/{uuid}/{category}/{id}")
                .routeParam("uuid", uuid.toString())
                .routeParam("category", category.name())
                .routeParam("id", id)
                .queryString("qty", quantity)
                .asObjectAsync(ConsumeResponse.class);
    }

    @Override
    public CompletableFuture<HttpResponse<Void>> unlockCosmetic(UnlockCosmeticRequest request) {
        return Unirest.post(config().apiUrl() + "/v1/cosmetics/unlock")
                .body(request)
                .header("x-access-token", config().apiSecret())
                .asObjectAsync(Void.class);
    }
}
