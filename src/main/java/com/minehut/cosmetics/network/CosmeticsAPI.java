package com.minehut.cosmetics.network;

import com.google.gson.Gson;
import com.minehut.cosmetics.config.Config;
import com.minehut.cosmetics.model.PackInfo;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
import com.minehut.cosmetics.model.profile.SimpleResponse;
import com.minehut.cosmetics.model.rank.PlayerRank;
import com.minehut.cosmetics.model.request.EquipCosmeticRequest;
import com.minehut.cosmetics.model.request.ModifyCosmeticQuantityRequest;
import com.minehut.cosmetics.model.request.UnlockCosmeticRequest;
import kong.unirest.HttpMethod;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class CosmeticsAPI {

    private final Gson gson;
    private final Config config;

    public CosmeticsAPI(Config config, Gson gson) {
        this.config = config;
        this.gson = gson;
        Unirest.config().setObjectMapper(new ObjectMapper() {
            @Override
            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return gson.fromJson(value, valueType);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public String writeValue(Object value) {
                try {
                    return gson.toJson(value);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public abstract CompletableFuture<HttpResponse<PackInfo>> getPackInfo();

    public abstract CompletableFuture<HttpResponse<CosmeticProfileResponse>> getProfile(UUID uuid);

    public abstract CompletableFuture<HttpResponse<SimpleResponse>> equipCosmetic(EquipCosmeticRequest req);

    public abstract CompletableFuture<PlayerRank[]> getRanks();

    public abstract CompletableFuture<HttpResponse<Void>> modifyCosmeticQuantity(ModifyCosmeticQuantityRequest req);

    public abstract CompletableFuture<HttpResponse<Void>> unlockCosmetic(UnlockCosmeticRequest req);

    protected HttpRequestWithBody request(HttpMethod method, String endpoint) {
        return Unirest.request(method.name(), config().apiUrl() + endpoint)
                .headers(Map.of("x-access-token", config.apiSecret()));
    }

    protected HttpRequestWithBody postJSON(String endpoint) {
        return request(HttpMethod.POST, endpoint).contentType("application/json");
    }

    protected Gson gson() {
        return gson;
    }

    public Config config() {
        return config;
    }
}
