package com.minehut.cosmetics.network;

import com.google.gson.Gson;
import com.minehut.cosmetics.config.ConfigManager;
import com.minehut.cosmetics.model.PackInfo;
import com.minehut.cosmetics.model.profile.CosmeticProfileResponse;
import com.minehut.cosmetics.model.rank.PlayerRank;
import kong.unirest.HttpMethod;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.Unirest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public abstract class CosmeticsAPI {
    private static final Gson gson = new Gson();

    private ConfigManager config;

    public CosmeticsAPI(ConfigManager config) {
        this.config = config;
    }

    public abstract Optional<PackInfo> getPackInfo();

    public abstract Optional<CosmeticProfileResponse> getProfile(UUID uuid);

    public abstract void equipCosmetic(UUID uuid, String category, String id);

    public abstract List<PlayerRank> getRanks();

    private HttpRequestWithBody generateRequest(HttpMethod method, String endpoint, Function<HttpRequestWithBody, HttpRequestWithBody> req) {
        final String url = config.apiUrl() + endpoint;
        return req.apply(Unirest.request(method.name(), url).headers(Map.of("x-access-token", config.apiSecret())));
    }

    protected String request(HttpMethod method, String endpoint, Function<HttpRequestWithBody, HttpRequestWithBody> req) {
        try {
            return generateRequest(method, endpoint, req).asString().getBody();
        } catch (Exception e) {
            return "";
        }
    }

    protected String request(HttpMethod method, String endpoint) {
        return request(method, endpoint, (req) -> req);
    }

    protected <T> Optional<T> requestType(HttpMethod method, String endpoint, Class<T> type, Function<HttpRequestWithBody, HttpRequestWithBody> req) {
        try {
            final String raw = generateRequest(method, endpoint, req).asString().getBody();
            return Optional.ofNullable(gson.fromJson(raw, type));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    protected <T> Optional<T> requestType(HttpMethod method, String endpoint, Class<T> type) {
        return requestType(method, endpoint, type, (req) -> req);
    }


}
