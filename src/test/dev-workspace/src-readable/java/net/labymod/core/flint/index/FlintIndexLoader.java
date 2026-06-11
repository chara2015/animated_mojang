package net.labymod.core.flint.index;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Locale;
import net.labymod.api.Constants;
import net.labymod.api.util.JsonFileCache;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.util.io.web.result.ResultCallback;
import net.labymod.core.flint.FlintUrls;
import net.labymod.core.util.jsonfilecache.DefaultJsonFileCacheFactory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/index/FlintIndexLoader.class */
public class FlintIndexLoader {
    private static FlintIndexLoader instance;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
    private final JsonFileCache<JsonArray> indexFileCache;
    private ResultCallback<JsonArray> callback;
    private Result<JsonArray> latestIndex;

    private FlintIndexLoader() {
        Path path;
        String releaseChannel = FlintUrls.getCurrentReleaseChannel();
        if (releaseChannel.equals("production")) {
            path = Constants.Files.FILE_CACHE.resolve("index.json");
        } else {
            path = Constants.Files.FILE_CACHE.resolve("addons-" + releaseChannel + ".json");
        }
        this.indexFileCache = DefaultJsonFileCacheFactory.createJsonFileCache(path, String.format(FlintUrls.GET_INDEX, FlintUrls.getCurrentReleaseChannel()), "index", JsonArray.class).readLastModifiedDateFromHeader("Last-Modified", DATE_FORMAT);
        setupIndex();
    }

    public static FlintIndexLoader getInstance() {
        if (instance == null) {
            instance = new FlintIndexLoader();
        }
        return instance;
    }

    private void setupIndex() {
        this.indexFileCache.read(false, this::handleResult);
    }

    public Result<JsonArray> getLatestIndex() {
        return this.latestIndex == null ? Result.empty() : this.latestIndex;
    }

    public void addCallback(ResultCallback<JsonArray> callback) {
        this.callback = callback;
        if (this.latestIndex != null) {
            this.callback.accept(this.latestIndex);
        }
    }

    public JsonFileCache<JsonArray> getIndexFileCache() {
        return this.indexFileCache;
    }

    public void handleResult(Result<JsonArray> result) {
        if (result.isPresent()) {
            this.latestIndex = result;
            if (this.callback != null) {
                this.callback.accept(result);
            }
        }
    }

    public JsonObject getModificationObject(String namespace) {
        if (!this.latestIndex.isPresent()) {
            return null;
        }
        for (JsonElement jsonElement : this.latestIndex.get()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.get("namespace").getAsString().equals(namespace)) {
                return jsonObject;
            }
        }
        return null;
    }
}
