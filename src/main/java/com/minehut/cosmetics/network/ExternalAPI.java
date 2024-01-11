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
import kong.unirest.Unirest;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ExternalAPI extends NetworkApi {


    public ExternalAPI(Config config, Gson gson) {
        super(config, gson);
    }

    @Override
    public CompletableFuture<HttpResponse<PackInfo>> getPackInfo() {
        return request(HttpMethod.GET, "/network/resourcepacks/info")
                .asObjectAsync(PackInfo.class);
    }

    @Override
    public CompletableFuture<HttpResponse<CosmeticProfileResponse>> getProfile(UUID uuid) {
        return request(HttpMethod.GET, "/cosmetics/profile/{uuid}")
                .routeParam("uuid", uuid.toString())
                .asObjectAsync(CosmeticProfileResponse.class);
    }

    public CompletableFuture<HttpResponse<PlayerRank[]>> getRanks() {
        return Unirest.get(config().apiUrl() + "/network/ranks").asObjectAsync(new GenericType<>() {
        });
    }

    @Override
    public CompletableFuture<HttpResponse<Void>> modifyCosmeticQuantity(ModifyCosmeticQuantityRequest req) {
        // cannot be called from player servers so we stub this
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<HttpResponse<Void>> salvageCosmetic(SalvageCosmeticRequest req) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<HttpResponse<Void>> unlockCosmetic(UnlockCosmeticRequest req) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<HttpResponse<SimpleResponse>> updateEquipment(EquipmentUpdateRequest request) {
        return CompletableFuture.completedFuture(null);
    }
}
