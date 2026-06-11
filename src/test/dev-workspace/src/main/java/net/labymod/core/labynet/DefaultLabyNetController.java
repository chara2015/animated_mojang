package net.labymod.core.labynet;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.client.network.server.ServerJoinEvent;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.TokenStorage;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.labynet.models.NameHistory;
import net.labymod.api.labynet.models.ServerGroup;
import net.labymod.api.labynet.models.ServerManifest;
import net.labymod.api.labynet.models.SocialMediaEntry;
import net.labymod.api.labynet.models.service.ServiceDataType;
import net.labymod.api.labynet.models.service.ServiceStatus;
import net.labymod.api.labynet.models.textures.Skin;
import net.labymod.api.labynet.models.textures.TextureResult;
import net.labymod.api.models.Implements;
import net.labymod.api.notification.Notification;
import net.labymod.api.notification.NotificationController;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.JsonFileCache;
import net.labymod.api.util.collection.Lists;
import net.labymod.api.util.collection.map.DualKeyMap;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.api.util.io.web.request.Callback;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.io.web.request.types.TypeTokenGsonRequest;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.util.io.web.result.ResultCallback;
import net.labymod.core.labynet.ServerGroupRegistry;
import net.labymod.core.labynet.model.Survey;
import net.labymod.core.labynet.model.SurveyVoteResult;
import net.labymod.core.labynet.model.UserProfile;
import net.labymod.core.util.jsonfilecache.DefaultJsonFileCacheFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/DefaultLabyNetController.class */
@Singleton
@Implements(LabyNetController.class)
public class DefaultLabyNetController implements LabyNetController {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
    private ServerGroup currentServerGroup;
    public boolean showSurvey = true;
    private final ServerGroupRegistry serverGroupRegistry = new ServerGroupRegistry();
    private final JsonFileCache<JsonObject> indexFileCache = DefaultJsonFileCacheFactory.createJsonFileCache(Constants.Files.LABY_NET_INDEX, Constants.LegacyUrls.SERVER_GROUPS, "server_groups", JsonObject.class).readLastModifiedDateFromHeader("last-modified", DATE_FORMAT);
    private final Map<String, ServerManifest> manifestCaches = new HashMap();
    private final Map<UUID, List<SocialMediaEntry>> socialMediaCaches = new HashMap();
    private final DualKeyMap<List<NameHistory>> nameHistoryCache = new DualKeyMap<>(ConcurrentHashMap::new);
    private final DualKeyMap<UUID> playerIdentityCache = new DualKeyMap<>(ConcurrentHashMap::new);
    private final Map<UUID, List<Survey>> surveyCache = new HashMap();

    @Inject
    public DefaultLabyNetController(EventBus eventBus) {
        eventBus.registerListener(this);
        eventBus.registerListener(new ServerChatDataHandler(this));
    }

    @Subscribe(-128)
    public void initializeCurrentServerGroup(ServerJoinEvent event) {
        this.currentServerGroup = getServerByIp(event.serverData().address()).orElse(null);
    }

    @Subscribe
    public void clearCurrentServerGroup(ServerDisconnectEvent event) {
        this.currentServerGroup = null;
    }

    @Override // net.labymod.api.labynet.LabyNetController
    public void loadServerData() {
        this.indexFileCache.read(false, result -> {
            ServerGroupRegistry.ServerGroupIndex deserialize;
            if (result.isPresent() && (deserialize = (ServerGroupRegistry.ServerGroupIndex) this.indexFileCache.deserialize(ServerGroupRegistry.ServerGroupIndex.class)) != null) {
                this.serverGroupRegistry.initialize(deserialize);
            }
        });
    }

    @Override // net.labymod.api.labynet.LabyNetController
    public Optional<ServerGroup> getCurrentServer() {
        return Optional.ofNullable(this.currentServerGroup);
    }

    @Override // net.labymod.api.labynet.LabyNetController
    public Optional<ServerGroup> getServerByName(String name) {
        return Optional.ofNullable(this.serverGroupRegistry.getServerByName(name));
    }

    @Override // net.labymod.api.labynet.LabyNetController
    public Optional<ServerGroup> getServerByIp(String ip) {
        return Optional.ofNullable(this.serverGroupRegistry.getServerByIp(ip));
    }

    @Override // net.labymod.api.labynet.LabyNetController
    public Optional<ServerGroup> getServerByIp(ServerAddress serverAddress) {
        return getServerByIp(serverAddress.getHost().toLowerCase(Locale.ROOT));
    }

    @Override // net.labymod.api.labynet.LabyNetController
    public void getOrLoadManifest(ServerGroup serverGroup, ResultCallback<ServerManifest> callback) {
        ServerManifest manifest = this.manifestCaches.get(serverGroup.getServerName());
        if (manifest != null) {
            callback.acceptRaw(manifest);
            return;
        }
        Optional<ServerGroup.Attachment> optionalAttachment = serverGroup.getAttachment("manifest.json");
        if (optionalAttachment.isEmpty()) {
            return;
        }
        JsonFileCache<JsonObject> fileCache = DefaultJsonFileCacheFactory.createJsonFileCache(Constants.Files.LABY_NET_SERVER_GROUPS.resolve(serverGroup.getServerName() + ".json"), optionalAttachment.get().getUrl(), null, JsonObject.class);
        fileCache.readLastModifiedDateFromHeader("last-modified", DATE_FORMAT, success -> {
            fileCache.read(false, result -> {
                if (result.isPresent()) {
                    ServerManifest deserialize = (ServerManifest) fileCache.deserialize(ServerManifest.class);
                    if (deserialize != null) {
                        this.manifestCaches.put(serverGroup.getServerName(), deserialize);
                        callback.acceptRaw(deserialize);
                        return;
                    }
                    return;
                }
                if (result.hasException()) {
                    callback.acceptException(result.exception());
                }
            });
        });
    }

    @Override // net.labymod.api.labynet.LabyNetController
    public void loadNameHistory(UUID uniqueId, ResultCallback<List<NameHistory>> callback) {
        List<NameHistory> cached = this.nameHistoryCache.get(uniqueId);
        if (cached != null) {
            callback.acceptRaw(cached);
        } else {
            LabyExecutors.executeBackgroundTask(() -> {
                Result<List<NameHistory>> result = loadNameHistory(uniqueId);
                if (result.hasException()) {
                    callback.acceptException(result.exception());
                } else {
                    callback.acceptRaw(result.get());
                }
            });
        }
    }

    @Override // net.labymod.api.labynet.LabyNetController
    public void loadNameHistory(String name, ResultCallback<List<NameHistory>> callback) {
        List<NameHistory> cached = this.nameHistoryCache.get(name);
        if (cached != null) {
            callback.acceptRaw(cached);
        } else {
            LabyExecutors.executeBackgroundTask(() -> {
                Result<List<NameHistory>> result = loadNameHistory(name);
                if (result.hasException()) {
                    callback.acceptException(result.exception());
                } else {
                    callback.acceptRaw(result.get());
                }
            });
        }
    }

    @Override // net.labymod.api.labynet.LabyNetController
    public void loadNameByUniqueId(UUID uniqueId, ResultCallback<String> callback) {
        List<NameHistory> cached = this.nameHistoryCache.get(uniqueId);
        if (cached != null) {
            callback.acceptRaw(((NameHistory) cached.getFirst()).getName());
        } else {
            LabyExecutors.executeBackgroundTask(() -> {
                Result<List<NameHistory>> result = loadNameHistory(uniqueId);
                if (result.hasException()) {
                    callback.acceptException(result.exception());
                } else {
                    callback.acceptRaw(((NameHistory) result.get().getFirst()).getName());
                }
            });
        }
    }

    @Override // net.labymod.api.labynet.LabyNetController
    public Result<String> loadNameByUniqueIdSync(UUID uniqueId) {
        List<NameHistory> cached = this.nameHistoryCache.get(uniqueId);
        if (cached != null) {
            return Result.of(((NameHistory) cached.getFirst()).getName());
        }
        Result<List<NameHistory>> result = loadNameHistory(uniqueId);
        if (result.hasException()) {
            return Result.ofException(result.exception());
        }
        return Result.of(((NameHistory) result.get().getFirst()).getName());
    }

    @Override // net.labymod.api.labynet.LabyNetController
    public void loadUniqueIdByName(String name, ResultCallback<UUID> callback) {
        UUID cachedUuid = getCachedUuid(name);
        if (cachedUuid != null) {
            callback.acceptRaw(cachedUuid);
        } else {
            LabyExecutors.executeBackgroundTask(() -> {
                callback.accept((Result) loadUniqueIdByNameSync(name));
            });
        }
    }

    @Override // net.labymod.api.labynet.LabyNetController
    public Result<UUID> loadUniqueIdByNameSync(String name) {
        UUID cachedUuid = getCachedUuid(name);
        if (cachedUuid != null) {
            return Result.of(cachedUuid);
        }
        Request<UserProfile> request = buildRequest(String.format(Locale.ROOT, Constants.Urls.LABYNET_USER_GET_UUID, name), UserProfile.class);
        if (request == null) {
            return Result.empty();
        }
        Response<UserProfile> response = request.executeSync();
        if (response.hasException()) {
            return Result.ofException(response.exception());
        }
        if (response.isEmpty()) {
            return Result.ofException(new IllegalStateException("Invalid response"));
        }
        UserProfile userProfile = response.get();
        UUID uuid = userProfile.uuid();
        this.playerIdentityCache.put(uuid, name, uuid);
        return Result.of(uuid);
    }

    @Nullable
    private UUID getCachedUuid(String name) {
        return this.playerIdentityCache.get(name);
    }

    @Nullable
    private TokenStorage.Token getLabyConnectToken() {
        TokenStorage.Token token;
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null || (token = session.tokenStorage().getToken(TokenStorage.Purpose.JWT, session.self().getUniqueId())) == null || token.isExpired()) {
            return null;
        }
        return token;
    }

    private Result<List<NameHistory>> loadNameHistory(UUID uuid) {
        List<NameHistory> cached = this.nameHistoryCache.get(uuid);
        if (cached != null) {
            return Result.of(cached);
        }
        Request<JsonElement> request = buildRequest(String.format(Locale.ROOT, Constants.Urls.LABYNET_USER_GET_NAMES, uuid), JsonElement.class);
        if (request == null) {
            return Result.empty();
        }
        Result<List<NameHistory>> result = parseNameHistoryResponse(request.executeSync());
        if (!result.hasException() && result.isPresent()) {
            List<NameHistory> nameChanges = result.get();
            String name = ((NameHistory) nameChanges.getFirst()).getName();
            this.nameHistoryCache.put(uuid, name, nameChanges);
            this.playerIdentityCache.put(uuid, name, uuid);
        }
        return result;
    }

    private Result<List<NameHistory>> loadNameHistory(String name) {
        List<NameHistory> cached = this.nameHistoryCache.get(name);
        if (cached != null) {
            return Result.of(cached);
        }
        Request<JsonElement> request = buildRequest(String.format(Locale.ROOT, Constants.Urls.LABYNET_USER_GET_NAMES, name), JsonElement.class);
        if (request == null) {
            return Result.empty();
        }
        Result<List<NameHistory>> result = parseNameHistoryResponse(request.executeSync());
        if (!result.hasException() && result.isPresent()) {
            this.nameHistoryCache.put(name, result.get());
        }
        return result;
    }

    private Result<List<NameHistory>> parseNameHistoryResponse(Response<JsonElement> response) {
        if (response.hasException()) {
            return Result.ofException(response.exception());
        }
        if (response.isEmpty()) {
            return Result.ofException(new IllegalStateException("Invalid response"));
        }
        JsonElement element = response.get();
        if (!element.isJsonArray()) {
            return Result.ofException(new IllegalStateException("Invalid response"));
        }
        JsonArray<JsonElement> entries = element.getAsJsonArray();
        if (entries.isEmpty()) {
            return Result.ofException(new NullPointerException("No user found"));
        }
        List<NameHistory> nameChanges = Lists.newArrayList();
        for (JsonElement entry : entries) {
            if (!entry.isJsonObject()) {
                return Result.ofException(new IllegalStateException("Invalid response"));
            }
            nameChanges.add((NameHistory) GsonUtil.DEFAULT_GSON.fromJson(entry, NameHistory.class));
        }
        return Result.of(Collections.unmodifiableList(nameChanges.reversed()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.labynet.LabyNetController
    public void loadServiceData(ServiceDataType type, ResultCallback<ServiceStatus> callback) {
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            callback.acceptRaw(ServiceStatus.of(ServiceStatus.Status.NOT_CONNECTED));
            return;
        }
        TokenStorage.Token token = session.tokenStorage().getToken(TokenStorage.Purpose.CLIENT, session.self().getUniqueId());
        if (token == null || token.isExpired()) {
            callback.acceptRaw(ServiceStatus.of(ServiceStatus.Status.NOT_CONNECTED));
        } else {
            ((GsonRequest) ((GsonRequest) ((GsonRequest) Request.ofGson(type.getClazz()).url(Constants.Urls.LABYNET_SERVICE_WIDGET, type.name().toLowerCase(Locale.ROOT))).authorization("Client", token.getToken())).async()).execute(result -> {
                ServiceStatus serviceStatusOf;
                if (result.hasException()) {
                    switch (result.getStatusCode()) {
                        case 400:
                            serviceStatusOf = ServiceStatus.of(ServiceStatus.Status.NOT_LINKED);
                            break;
                        case 401:
                            serviceStatusOf = ServiceStatus.of(ServiceStatus.Status.NOT_CONNECTED);
                            break;
                        case 402:
                        default:
                            serviceStatusOf = ServiceStatus.of(ServiceStatus.Status.ERROR);
                            break;
                        case 403:
                            serviceStatusOf = ServiceStatus.of(ServiceStatus.Status.NEEDS_RELINK);
                            break;
                    }
                    ServiceStatus status = serviceStatusOf;
                    callback.acceptRaw(status);
                    return;
                }
                callback.acceptRaw((ServiceStatus) result.get());
            });
        }
    }

    @Override // net.labymod.api.labynet.LabyNetController
    public void loadSocials(UUID uniqueId, ResultCallback<List<SocialMediaEntry>> callback) {
        List<SocialMediaEntry> entries = this.socialMediaCaches.get(uniqueId);
        if (entries != null) {
            callback.acceptRaw(entries);
            return;
        }
        Request<JsonArray> request = buildRequest(String.format(Locale.ROOT, Constants.Urls.LABYNET_USER_SOCIALS, uniqueId), JsonArray.class);
        if (request == null) {
            callback.acceptRaw(Collections.emptyList());
        } else {
            request.async().execute(result -> {
                if (result.hasException()) {
                    callback.acceptException(result.exception());
                    return;
                }
                ArrayList arrayListNewArrayList = Lists.newArrayList();
                JsonArray<JsonElement> jsonArray = (JsonArray) result.get();
                for (JsonElement jsonElement : jsonArray) {
                    arrayListNewArrayList.add((SocialMediaEntry) GsonUtil.DEFAULT_GSON.fromJson(jsonElement, SocialMediaEntry.class));
                }
                this.socialMediaCaches.put(uniqueId, arrayListNewArrayList);
                callback.acceptRaw(arrayListNewArrayList);
            });
        }
    }

    public void loadTexturesFromUser(TextureResult.Type type, UUID uniqueId, ResultCallback<TextureResult> callback) {
        Request<JsonObject> request = buildRequest(String.format(Constants.Urls.LABYNET_USER_GET_TEXTURES, uniqueId), JsonObject.class);
        if (request == null) {
            callback.acceptRaw(new TextureResult(Collections.emptyList()));
        } else {
            request.async().execute(response -> {
                if (response.hasException()) {
                    callback.acceptException(response.exception());
                    return;
                }
                if (!response.isPresent()) {
                    callback.accept(Result.empty());
                    return;
                }
                JsonObject jsonObject = (JsonObject) response.get();
                if (!jsonObject.has(type.name())) {
                    callback.accept(Result.empty());
                    return;
                }
                try {
                    List<Skin> textures = new ArrayList<>();
                    JsonArray<JsonElement> jsonArray = jsonObject.get(type.name()).getAsJsonArray();
                    for (JsonElement element : jsonArray) {
                        if (element.isJsonObject()) {
                            JsonObject textureObject = element.getAsJsonObject();
                            String imageHash = textureObject.get("image_hash").getAsString();
                            boolean slim = textureObject.get("slim_skin").getAsBoolean();
                            textures.add(new Skin(slim ? MinecraftServices.SkinVariant.SLIM : MinecraftServices.SkinVariant.CLASSIC, imageHash));
                        }
                    }
                    callback.acceptRaw(new TextureResult(textures));
                } catch (Exception exception) {
                    callback.acceptException(exception);
                }
            });
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void getSurveys(ResultCallback<List<Survey>> callback) {
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            return;
        }
        List<Survey> cachedSurveys = this.surveyCache.get(session.self().getUniqueId());
        if (cachedSurveys != null) {
            callback.acceptRaw(cachedSurveys);
            return;
        }
        TokenStorage.Token token = getLabyConnectToken();
        if (token == null) {
            callback.acceptRaw(Collections.emptyList());
        } else {
            ((TypeTokenGsonRequest) ((TypeTokenGsonRequest) ((TypeTokenGsonRequest) Request.ofGsonList(Survey.class).url(Constants.Urls.LABYNET_SURVEY_SOURCE_CLIENT, new Object[0])).authorization("Bearer", token.getToken())).async()).execute(result -> {
                if (result.hasException()) {
                    callback.acceptException(result.exception());
                } else {
                    this.surveyCache.put(session.self().getUniqueId(), (List) result.get());
                    callback.acceptRaw((List) result.get());
                }
            });
        }
    }

    public void clearSurveyCache() {
        this.surveyCache.clear();
    }

    public void sendSurveyAnswer(Survey survey, Integer answerId, String text, ResultCallback<SurveyVoteResult> callback) {
        Request<SurveyVoteResult> request;
        if (survey == null) {
            throw new IllegalArgumentException("Survey must not be null");
        }
        TokenStorage.Token token = getLabyConnectToken();
        if (token == null || (request = buildRequest(String.format(Locale.ROOT, Constants.Urls.LABYNET_SURVEY, Integer.valueOf(survey.getId())), SurveyVoteResult.class)) == null) {
            return;
        }
        JsonObject jsonObject = new JsonObject();
        if (answerId != null) {
            jsonObject.addProperty("answer_id", answerId);
        }
        if (text != null) {
            jsonObject.addProperty("text", text);
        }
        request.method((answerId == null && text == null) ? Request.Method.DELETE : Request.Method.PUT).json(jsonObject).async().execute(result -> {
            NotificationController notificationController = Laby.labyAPI().notificationController();
            if (result.hasException()) {
                notificationController.push(Notification.builder().title(Component.translatable("labymod.survey.error.title", new Component[0])).text(Component.translatable("labymod.survey.error.text", new Component[0])).build());
                callback.acceptException(result.exception());
            } else {
                notificationController.push(Notification.builder().title(Component.translatable("labymod.survey.success.title", new Component[0])).text(Component.translatable("labymod.survey.success.text", new Component[0])).build());
                callback.acceptRaw((SurveyVoteResult) result.get());
            }
        });
    }

    public void loadTextures(@NotNull TextureResult.Type type, @Nullable String search, @NotNull TextureResult.Order order, int offset, int limit, @NotNull Callback<TextureResult> response) {
        String url = String.format(Locale.ROOT, Constants.Urls.LABYNET_TEXTURE_SEARCH, type.name(), order.name().toLowerCase(Locale.ROOT));
        if (limit != 0) {
            url = url + "&limit=" + limit;
        }
        if (offset != 0) {
            url = url + "&offset=" + offset;
        }
        if (search != null && !search.isEmpty()) {
            url = url + "&input=" + search;
        }
        Request<TextureResult> request = buildRequest(url, TextureResult.class);
        if (request == null) {
            return;
        }
        request.async().execute(response);
    }

    private <T> Request<T> buildRequest(String url, Class<T> type) {
        return buildRequest(url, type, Request::ofGson);
    }

    private <T> Request<T> buildRequest(String url, Class<T> type, Function<Class<T>, Request<T>> requestFactory) {
        TokenStorage.Token token = getLabyConnectToken();
        if (token == null) {
            return null;
        }
        return requestFactory.apply(type).url(url, new Object[0]).authorization("Bearer", token.getToken()).async(false);
    }
}
