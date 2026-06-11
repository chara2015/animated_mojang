package net.labymod.api.util.io.web.request.types;

import com.google.gson.Gson;
import java.util.Objects;
import java.util.function.Supplier;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.AbstractRequest;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.request.types.AbstractGsonRequest;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/request/types/AbstractGsonRequest.class */
public abstract class AbstractGsonRequest<T, R extends AbstractGsonRequest<T, ?>> extends AbstractRequest<T, R> {
    protected static final Gson DEFAULT_GSON = GsonUtil.DEFAULT_GSON;
    protected final Supplier<Gson> gsonSupplier;

    protected AbstractGsonRequest(@NotNull Supplier<Gson> gsonSupplier) {
        Objects.requireNonNull(gsonSupplier, "Gson supplier cannot be null");
        this.gsonSupplier = gsonSupplier;
    }

    @Override // net.labymod.api.util.io.web.request.AbstractRequest
    @ApiStatus.Internal
    protected T handle(Response<T> response, WebInputStream inputStream) throws Exception {
        return handleGson(inputStream);
    }

    protected T handleGson(WebInputStream inputStream) throws Exception {
        throw new UnsupportedOperationException("AbstractGsonRequest#handleGson is not implemented");
    }
}
