package net.labymod.api.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.function.LongConsumer;
import java.util.function.ToLongFunction;
import net.labymod.api.Laby;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.util.io.web.result.ResultCallback;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/JsonFileCache.class */
public interface JsonFileCache<T extends JsonElement> {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/JsonFileCache$Factory.class */
    @Referenceable
    public interface Factory {
        @NotNull
        <T extends JsonElement> JsonFileCache<T> create(Path path, String str, String str2, Class<T> cls);

        @NotNull
        <T extends JsonElement> JsonFileCache<T> create(@NotNull Path path, @NotNull Request<T> request, @NotNull String str);
    }

    void read(boolean z, ResultCallback<T> resultCallback);

    void update(boolean z, ResultCallback<T> resultCallback);

    void download(boolean z, ResultCallback<T> resultCallback);

    boolean isUpToDate();

    JsonFileCache<T> readLastModifiedLongFromHeader(String str);

    JsonFileCache<T> readLastModifiedLongFromHeader(String str, LongConsumer longConsumer);

    JsonFileCache<T> readLastModifiedDateFromHeader(String str, SimpleDateFormat simpleDateFormat);

    JsonFileCache<T> readLastModifiedDateFromHeader(String str, SimpleDateFormat simpleDateFormat, LongConsumer longConsumer);

    JsonFileCache<T> readLastModifiedFromUrl(String str, ToLongFunction<Result<String>> toLongFunction);

    JsonFileCache<T> readLastModifiedFromUrl(String str, ToLongFunction<Result<String>> toLongFunction, LongConsumer longConsumer);

    JsonFileCache<T> setLastModified(long j);

    <R> R deserialize(Class<R> cls);

    Result<JsonObject> getJsonObject();

    static <T extends JsonElement> JsonFileCache<T> create(@NotNull Path path, @NotNull String url, @NotNull String name, @NotNull Class<T> cls) {
        return Laby.references().jsonFileCacheFactory().create(path, url, name, cls);
    }

    static <T extends JsonElement> JsonFileCache<T> create(@NotNull Path path, @NotNull Request<T> request, @NotNull String name) {
        return Laby.references().jsonFileCacheFactory().create(path, request, name);
    }
}
