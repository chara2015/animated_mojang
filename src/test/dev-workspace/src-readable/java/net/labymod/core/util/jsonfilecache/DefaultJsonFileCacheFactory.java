package net.labymod.core.util.jsonfilecache;

import com.google.gson.JsonElement;
import java.nio.file.Path;
import javax.inject.Singleton;
import net.labymod.api.models.Implements;
import net.labymod.api.util.JsonFileCache;
import net.labymod.api.util.io.web.request.Request;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/jsonfilecache/DefaultJsonFileCacheFactory.class */
@Singleton
@Implements(JsonFileCache.Factory.class)
public class DefaultJsonFileCacheFactory implements JsonFileCache.Factory {
    private static final DefaultJsonFileCacheFactory INSTANCE = new DefaultJsonFileCacheFactory();

    public static <T extends JsonElement> JsonFileCache<T> createJsonFileCache(Path path, String url, String name, Class<T> cls) {
        return INSTANCE.create(path, url, name, cls);
    }

    public static <T extends JsonElement> JsonFileCache<T> createJsonFileCache(Path path, Request<T> request, String name) {
        return INSTANCE.create(path, request, name);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [net.labymod.api.util.io.web.request.AbstractRequest, net.labymod.api.util.io.web.request.Request] */
    @Override // net.labymod.api.util.JsonFileCache.Factory
    @NotNull
    public <T extends JsonElement> JsonFileCache<T> create(Path path, String url, String name, Class<T> cls) {
        return new DefaultJsonFileCache(path, Request.ofGson(cls).url(url, new Object[0]), name);
    }

    @Override // net.labymod.api.util.JsonFileCache.Factory
    @NotNull
    public <T extends JsonElement> JsonFileCache<T> create(@NotNull Path path, @NotNull Request<T> request, @NotNull String name) {
        return new DefaultJsonFileCache(path, request, name);
    }
}
