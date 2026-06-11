package net.labymod.api.util.io.web.request.types;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Supplier;
import net.labymod.api.util.io.web.WebInputStream;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/request/types/GsonRequest.class */
public class GsonRequest<T> extends AbstractGsonRequest<T, GsonRequest<T>> {
    protected final Class<T> type;

    protected GsonRequest(@NotNull Class<T> type, @NotNull Supplier<Gson> gsonSupplier) {
        super(gsonSupplier);
        Objects.requireNonNull(type, "Type cannot be null");
        this.type = type;
    }

    protected GsonRequest(@NotNull Class<T> type) {
        this(type, () -> {
            return DEFAULT_GSON;
        });
    }

    public static <T> GsonRequest<T> of(@NotNull Class<T> type) {
        return new GsonRequest<>(type);
    }

    public static <T> GsonRequest<T> of(@NotNull Class<T> type, @NotNull Gson gson) {
        return new GsonRequest<>(type, () -> {
            return gson;
        });
    }

    public static <T> GsonRequest<T> of(@NotNull Class<T> type, @NotNull Supplier<Gson> gsonSupplier) {
        return new GsonRequest<>(type, gsonSupplier);
    }

    @Override // net.labymod.api.util.io.web.request.types.AbstractGsonRequest
    protected T handleGson(WebInputStream webInputStream) throws IOException {
        return (T) this.gsonSupplier.get().fromJson(readString(webInputStream), this.type);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.labymod.api.util.io.web.request.AbstractRequest
    public GsonRequest<T> prepareCopy() {
        return new GsonRequest<>(this.type, this.gsonSupplier);
    }

    public Class<T> getType() {
        return this.type;
    }
}
