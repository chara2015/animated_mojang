package net.labymod.core.util.jsonfilecache;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.function.LongConsumer;
import java.util.function.ToLongFunction;
import net.labymod.api.util.JsonFileCache;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.io.web.request.types.StringRequest;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.util.io.web.result.ResultCallback;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.util.io.web.connection.DefaultWebResolver;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/jsonfilecache/DefaultJsonFileCache.class */
public class DefaultJsonFileCache<T extends JsonElement> implements JsonFileCache<T> {
    private static final DefaultWebResolver WEB_RESOLVER = DefaultWebResolver.instance();
    private static final Gson GSON = new Gson();
    private static final Logging LOGGING = Logging.create((Class<?>) JsonFileCache.class);
    private final Path path;
    private final String name;
    private final Request<T> request;
    private final Class<T> type;
    private Result<JsonObject> latestCache;
    private Long lastModified;

    protected DefaultJsonFileCache(Path path, Request<T> request, String name) {
        if (!(request instanceof GsonRequest)) {
            throw new UnsupportedOperationException("Request has to be a GsonRequest!");
        }
        this.path = path;
        this.request = request;
        this.name = name;
        this.type = ((GsonRequest) request).getType();
        this.lastModified = 0L;
        this.latestCache = Result.empty();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.util.JsonFileCache
    public void read(boolean async, ResultCallback<T> resultCallback) {
        if (!IOUtil.exists(this.path)) {
            download(async, resultCallback);
            return;
        }
        try {
            Reader reader = Files.newBufferedReader(this.path, StandardCharsets.UTF_8);
            try {
                JsonObject cachedFile = (JsonObject) GSON.fromJson(reader, JsonObject.class);
                if (reader != null) {
                    reader.close();
                }
                if (isUpToDate(cachedFile)) {
                    String name = getName(true);
                    if (cachedFile.has(name)) {
                        try {
                            this.latestCache = Result.of(cachedFile);
                            JsonElement jsonElement = cachedFile.get(name);
                            if (jsonElement.isJsonArray()) {
                                resultCallback.acceptRaw((JsonElement) GSON.fromJson(jsonElement, this.type));
                                return;
                            } else {
                                resultCallback.acceptRaw((JsonElement) GSON.fromJson(cachedFile, this.type));
                                return;
                            }
                        } catch (JsonSyntaxException e) {
                            LOGGING.warn("An exception occurred while deserializing the json cache.", e);
                        }
                    }
                }
                download(async, resultCallback);
            } finally {
            }
        } catch (Exception e2) {
            LOGGING.warn("An exception occurred while reading the local file.", e2);
            download(async, resultCallback);
        }
    }

    @Override // net.labymod.api.util.JsonFileCache
    public void update(boolean async, ResultCallback<T> callback) {
        Result<JsonObject> latestResult = this.latestCache;
        if (!latestResult.isPresent()) {
            read(async, callback);
        } else if (!isUpToDate()) {
            download(async, result -> {
                if (result.isPresent()) {
                    callback.accept(result);
                } else {
                    this.latestCache = latestResult;
                }
            });
        }
    }

    @Override // net.labymod.api.util.JsonFileCache
    public void download(boolean async, ResultCallback<T> callback) {
        WEB_RESOLVER.resolveConnection(this.request.copy().async(async), response -> {
            if (response.hasException()) {
                callback.acceptException(response.exception());
                return;
            }
            JsonElement jsonElement = (JsonElement) response.get();
            this.latestCache = Result.of(saveToFile(jsonElement));
            callback.acceptRaw(jsonElement);
        });
    }

    @Override // net.labymod.api.util.JsonFileCache
    public boolean isUpToDate() {
        if (!this.latestCache.isPresent()) {
            return false;
        }
        return isUpToDate(this.latestCache.get());
    }

    @Override // net.labymod.api.util.JsonFileCache
    public JsonFileCache<T> readLastModifiedLongFromHeader(String headerKey) {
        return readLastModifiedLongFromHeader(headerKey, null);
    }

    @Override // net.labymod.api.util.JsonFileCache
    public JsonFileCache<T> readLastModifiedLongFromHeader(String headerKey, LongConsumer consumer) {
        sendHeaderRequest(consumer != null, headerKey, result -> {
            if (result.isPresent()) {
                try {
                    this.lastModified = Long.valueOf(Long.parseLong((String) result.get()));
                } catch (Exception e) {
                    this.lastModified = 0L;
                }
                if (consumer != null) {
                    consumer.accept(this.lastModified.longValue());
                }
            }
        });
        return this;
    }

    @Override // net.labymod.api.util.JsonFileCache
    public JsonFileCache<T> readLastModifiedDateFromHeader(String headerKey, SimpleDateFormat dateFormat) {
        return readLastModifiedDateFromHeader(headerKey, dateFormat, null);
    }

    @Override // net.labymod.api.util.JsonFileCache
    public JsonFileCache<T> readLastModifiedDateFromHeader(String headerKey, SimpleDateFormat dateFormat, LongConsumer consumer) {
        sendHeaderRequest(consumer != null, headerKey, result -> {
            if (result.isPresent()) {
                try {
                    this.lastModified = Long.valueOf(dateFormat.parse((String) result.get()).getTime());
                } catch (Exception e) {
                    this.lastModified = 0L;
                }
                if (consumer != null) {
                    consumer.accept(this.lastModified.longValue());
                }
            }
        });
        return this;
    }

    @Override // net.labymod.api.util.JsonFileCache
    public JsonFileCache<T> readLastModifiedFromUrl(String url, ToLongFunction<Result<String>> parser) {
        return readLastModifiedFromUrl(url, parser, null);
    }

    @Override // net.labymod.api.util.JsonFileCache
    public JsonFileCache<T> readLastModifiedFromUrl(String url, ToLongFunction<Result<String>> parser, LongConsumer callback) {
        StringRequest stringRequestOfString = Request.ofString();
        stringRequestOfString.url(url, new Object[0]);
        stringRequestOfString.async(callback != null);
        WEB_RESOLVER.resolveConnection(stringRequestOfString, response -> {
            long lastModified;
            try {
                lastModified = parser.applyAsLong(response);
            } catch (Exception e) {
                lastModified = 0;
            }
            String string = String.valueOf(lastModified);
            if (string.length() == 10) {
                lastModified *= 1000;
            }
            this.lastModified = Long.valueOf(lastModified);
            if (callback != null) {
                callback.accept(lastModified);
            }
        });
        return this;
    }

    @Override // net.labymod.api.util.JsonFileCache
    public JsonFileCache<T> setLastModified(long lastModified) {
        this.lastModified = Long.valueOf(lastModified);
        return this;
    }

    @Override // net.labymod.api.util.JsonFileCache
    public <R> R deserialize(Class<R> cls) {
        String name = getName(true);
        if (!this.latestCache.isPresent() || !this.latestCache.get().has(name)) {
            return null;
        }
        try {
            JsonElement jsonElement = (JsonObject) this.latestCache.get();
            JsonElement jsonElement2 = jsonElement;
            if (this.name == null || this.name.isEmpty()) {
                jsonElement2 = jsonElement.get(name);
            }
            return (R) GSON.fromJson(jsonElement2, cls);
        } catch (Exception e) {
            LOGGING.warn("An exception occurred while deserializing the json cache.", e);
            return null;
        }
    }

    @Override // net.labymod.api.util.JsonFileCache
    public Result<JsonObject> getJsonObject() {
        return this.latestCache;
    }

    private JsonObject saveToFile(JsonElement jsonElement) {
        if (jsonElement.isJsonObject()) {
            String name = getName(false);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (name != null && jsonObject.has(name) && jsonObject.entrySet().size() == 1) {
                jsonElement = jsonObject.get(name);
            }
        }
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("updated_at", Long.valueOf(TimeUtil.getCurrentTimeMillis()));
        jsonObject2.add(getName(true), jsonElement);
        try {
            if (!IOUtil.exists(this.path)) {
                IOUtil.createDirectories(this.path.getParent());
                IOUtil.createFile(this.path);
            }
            Files.writeString(this.path, GSON.toJson(jsonObject2), new OpenOption[0]);
        } catch (Exception e) {
            LOGGING.warn("An exception occurred while writing the json cache to hard drive.", e);
        }
        return jsonObject2;
    }

    private boolean isUpToDate(JsonObject jsonObject) {
        if (jsonObject == null || !jsonObject.has("updated_at")) {
            return false;
        }
        return this.lastModified.longValue() == 0 || jsonObject.get("updated_at").getAsLong() > this.lastModified.longValue();
    }

    private void sendHeaderRequest(boolean async, String key, ResultCallback<String> callback) {
        Request<T> request = this.request.copy();
        request.async(async);
        request.method(Request.Method.HEAD);
        WEB_RESOLVER.resolveConnection(request, response -> {
            if (response.hasException()) {
                callback.acceptException(response.exception());
                return;
            }
            if (response.getStatusCode() != 200) {
                callback.acceptException(new UnsupportedOperationException("Response code is not 200 (was " + response.getStatusCode() + ")"));
                return;
            }
            String header = response.getHeaders().get(key);
            if (header == null) {
                callback.acceptException(new UnsupportedOperationException("Header \"" + key + "\" not found."));
            } else {
                callback.acceptRaw(header);
            }
        });
    }

    private String getName(boolean cache) {
        if (this.name != null && !this.name.isEmpty()) {
            return this.name;
        }
        if (cache) {
            return "cache";
        }
        return null;
    }

    public static Gson getGson() {
        return GSON;
    }
}
