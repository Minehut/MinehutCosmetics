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
import kong.unirest.GenericType;
import kong.unirest.HttpMethod;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

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

    public abstract CompletableFuture<HttpResponse<SimpleResponse>> equipCosmetic(UUID uuid, String category, String id);

    public abstract CompletableFuture<PlayerRank[]> getRanks();

    public abstract CompletableFuture<HttpResponse<ConsumeResponse>> consumeCosmetic(UUID uuid, CosmeticCategory category, String id, int quantity);

    public abstract CompletableFuture<HttpResponse<Void>> unlockCosmetic(UnlockCosmeticRequest request);

    protected void request(HttpMethod method, String endpoint) {
        return Unirest.request(method.name(), config().apiUrl() + endpoint)
                .header("");
    }

    protected Gson gson() {
        return gson;
    }

    public Config config() {
        return config;
    }
}
