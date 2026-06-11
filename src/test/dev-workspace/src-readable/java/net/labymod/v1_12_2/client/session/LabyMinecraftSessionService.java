package net.labymod.v1_12_2.client.session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.exceptions.UserMigratedException;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import com.mojang.authlib.yggdrasil.request.JoinMinecraftServerRequest;
import com.mojang.authlib.yggdrasil.response.ProfileSearchResultsResponse;
import com.mojang.authlib.yggdrasil.response.Response;
import com.mojang.util.UUIDTypeAdapter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/session/LabyMinecraftSessionService.class */
public class LabyMinecraftSessionService extends YggdrasilMinecraftSessionService {
    private static final String BASE_URL = "https://sessionserver.mojang.com/session/minecraft/";
    private static final URL JOIN_URL = HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/join");
    private final Gson gson;

    public LabyMinecraftSessionService(YggdrasilAuthenticationService authenticationService) {
        super(authenticationService);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(GameProfile.class, new GameProfileSerializer());
        builder.registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer());
        builder.registerTypeAdapter(UUID.class, new UUIDTypeAdapter());
        builder.registerTypeAdapter(ProfileSearchResultsResponse.class, new ProfileSearchResultsResponse.Serializer());
        this.gson = builder.create();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.authlib.exceptions.AuthenticationException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.authlib.exceptions.AuthenticationUnavailableException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.authlib.exceptions.InvalidCredentialsException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.authlib.exceptions.UserMigratedException */
    public void joinServer(GameProfile profile, String authenticationToken, String serverId) throws UserMigratedException, AuthenticationException, InvalidCredentialsException, AuthenticationUnavailableException {
        JoinMinecraftServerRequest request = new JoinMinecraftServerRequest();
        request.accessToken = authenticationToken;
        request.selectedProfile = profile.getId();
        request.serverId = serverId;
        try {
            String jsonResult = getAuthenticationService().performPostRequest(JOIN_URL, this.gson.toJson(request), "application/json");
            Response result = (Response) this.gson.fromJson(jsonResult, Response.class);
            if (result != null && StringUtils.isNotBlank(result.getError())) {
                if ("UserMigratedException".equals(result.getCause())) {
                    throw new UserMigratedException(result.getErrorMessage());
                }
                if (result.getError().equals("ForbiddenOperationException")) {
                    throw new InvalidCredentialsException(result.getErrorMessage());
                }
                throw new AuthenticationException(result.getErrorMessage());
            }
        } catch (IOException | JsonParseException | IllegalStateException e) {
            throw new AuthenticationUnavailableException("Cannot contact authentication server", e);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/session/LabyMinecraftSessionService$GameProfileSerializer.class */
    private static class GameProfileSerializer implements JsonSerializer<GameProfile>, JsonDeserializer<GameProfile> {
        private GameProfileSerializer() {
        }

        /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
        public GameProfile m1241deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = (JsonObject) json;
            UUID id = object.has("id") ? (UUID) context.deserialize(object.get("id"), UUID.class) : null;
            String name = object.has("name") ? object.getAsJsonPrimitive("name").getAsString() : null;
            return new GameProfile(id, name);
        }

        public JsonElement serialize(GameProfile src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            if (src.getId() != null) {
                result.add("id", context.serialize(src.getId()));
            }
            if (src.getName() != null) {
                result.addProperty("name", src.getName());
            }
            return result;
        }
    }
}
