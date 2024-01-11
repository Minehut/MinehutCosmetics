package com.minehut.cosmetics.network;

import com.google.gson.Gson;
import com.minehut.cosmetics.config.Config;
import com.minehut.cosmetics.model.PackInfo;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
import com.minehut.cosmetics.model.profile.SimpleResponse;
import com.minehut.cosmetics.model.rank.PlayerRank;
import com.minehut.cosmetics.model.request.EquipmentUpdateRequest;
import com.minehut.cosmetics.model.request.ModifyCosmeticQuantityRequest;
import com.minehut.cosmetics.model.request.SalvageCosmeticRequest;
import com.minehut.cosmetics.model.request.UnlockCosmeticRequest;
import kong.unirest.GenericType;
import kong.unirest.HttpMethod;
import kong.unirest.HttpResponse;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class InternalAPI extends NetworkApi {

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
    public CompletableFuture<HttpResponse<SimpleResponse>> updateEquipment(EquipmentUpdateRequest request) {
        return postJSON("/v1/cosmetics/update")
                .body(request)
                .asObjectAsync(SimpleResponse.class);
    }

    @Override
    public CompletableFuture<HttpResponse<PlayerRank[]>> getRanks() {
        return request(HttpMethod.GET, "/v1/ranks")
                .asObjectAsync(new GenericType<>() {
                });
    }

    @Override
    public CompletableFuture<HttpResponse<Void>> unlockCosmetic(UnlockCosmeticRequest req) {
        return postJSON("/v1/cosmetics/unlock")
                .body(req)
                .asObjectAsync(Void.class);
    }

    @Override
    public CompletableFuture<HttpResponse<Void>> modifyCosmeticQuantity(ModifyCosmeticQuantityRequest req) {
        return postJSON("/v1/cosmetics/modifyQuantity")
                .body(req)
                .asObjectAsync(Void.class);
    }

    @Override
    public CompletableFuture<HttpResponse<Void>> salvageCosmetic(SalvageCosmeticRequest req) {
        return postJSON("/v1/cosmetics/salvage")
                .body(req)
                .asObjectAsync(Void.class);
    }
}
