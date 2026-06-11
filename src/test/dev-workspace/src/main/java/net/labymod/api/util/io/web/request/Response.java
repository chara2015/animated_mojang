package net.labymod.api.util.io.web.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.labymod.api.util.io.web.request.WebResolver;
import net.labymod.api.util.io.web.result.Result;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/request/Response.class */
public class Response<T> extends Result<T> {
    private static final Map<String, String> DEFAULT_HEADERS = Collections.unmodifiableMap(new HashMap());
    private final WebResolver.WebRequest<T> request;
    private int responseCode;
    private Map<String, String> headers;

    protected Response(@NotNull WebResolver.WebRequest<T> request) {
        super(null, null);
        this.headers = DEFAULT_HEADERS;
        Objects.requireNonNull(request, "Request cannot be null");
        this.request = request;
        this.responseCode = -1;
    }

    public static <T> Response<T> of(@NotNull WebResolver.WebRequest<T> request) {
        return new Response<>(request);
    }

    @NotNull
    public WebResolver.WebRequest<T> connectionRequest() {
        return this.request;
    }

    public int getStatusCode() {
        return this.responseCode;
    }

    @NotNull
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public void setResponseCode(int responseCode) {
        if (!isEmpty()) {
            throw new IllegalStateException("Response code cannot be set if the result is not empty");
        }
        this.responseCode = responseCode;
    }

    public void setResponseHeaders(@NotNull Map<String, String> headers) {
        Objects.requireNonNull(headers, "Headers cannot be null");
        if (!isEmpty()) {
            throw new IllegalStateException("Response headers cannot be set if the result is not empty");
        }
        this.headers = Collections.unmodifiableMap(headers);
    }

    @Deprecated
    @NotNull
    public Map<String, String> getResponseHeaders() {
        return getHeaders();
    }

    @Deprecated
    public int getResponseCode() {
        return getStatusCode();
    }
}
