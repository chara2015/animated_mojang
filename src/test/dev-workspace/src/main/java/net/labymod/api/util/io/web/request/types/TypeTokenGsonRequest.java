package net.labymod.api.util.io.web.request.types;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import net.labymod.api.util.io.web.WebInputStream;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/request/types/TypeTokenGsonRequest.class */
public class TypeTokenGsonRequest<T> extends AbstractGsonRequest<T, TypeTokenGsonRequest<T>> {
    protected final TypeToken<T> typeToken;

    protected TypeTokenGsonRequest(@NotNull TypeToken<T> typeToken, @NotNull Supplier<Gson> gsonSupplier) {
        super(gsonSupplier);
        Objects.requireNonNull(typeToken, "Type cannot be null");
        this.typeToken = typeToken;
    }

    public static <T> TypeTokenGsonRequest<T> of(@NotNull TypeToken<T> type) {
        return of(type, (Supplier<Gson>) () -> {
            return DEFAULT_GSON;
        });
    }

    public static <T> TypeTokenGsonRequest<T> of(@NotNull TypeToken<T> type, @NotNull Gson gson) {
        return of(type, (Supplier<Gson>) () -> {
            return gson;
        });
    }

    public static <T> TypeTokenGsonRequest<T> of(@NotNull TypeToken<T> type, @NotNull Supplier<Gson> gsonSupplier) {
        return new TypeTokenGsonRequest<>(type, gsonSupplier);
    }

    public static <T> TypeTokenGsonRequest<List<T>> ofList(Class<T> type) {
        return ofList(type, () -> {
            return DEFAULT_GSON;
        });
    }

    public static <T> TypeTokenGsonRequest<List<T>> ofList(Class<T> type, Supplier<Gson> gsonSupplier) {
        return of(TypeToken.getParameterized(List.class, new Type[]{type}), gsonSupplier);
    }

    @Override // net.labymod.api.util.io.web.request.types.AbstractGsonRequest
    protected T handleGson(WebInputStream webInputStream) throws IOException {
        return (T) this.gsonSupplier.get().fromJson(readString(webInputStream), this.typeToken.getType());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.labymod.api.util.io.web.request.AbstractRequest
    public TypeTokenGsonRequest<T> prepareCopy() {
        return new TypeTokenGsonRequest<>(this.typeToken, this.gsonSupplier);
    }

    public TypeToken<T> getTypeToken() {
        return this.typeToken;
    }
}
