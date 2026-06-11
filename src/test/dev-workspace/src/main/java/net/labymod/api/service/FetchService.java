package net.labymod.api.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.io.web.WebResponse;
import net.labymod.api.util.io.web.exception.WebRequestException;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.TypeTokenGsonRequest;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/service/FetchService.class */
public abstract class FetchService<T> extends Service implements WebResponse<T> {
    private final String url;
    private final TypeToken<T> typeToken;

    public FetchService(String url, Type type) {
        this(url, TypeToken.get(type));
    }

    public FetchService(String url, Class<T> type) {
        this(url, TypeToken.get(type));
    }

    public FetchService(String url, TypeToken<T> typeToken) {
        this.url = url;
        this.typeToken = typeToken;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.service.Service
    protected void prepare() {
        ((TypeTokenGsonRequest) ((TypeTokenGsonRequest) Request.ofGson(this.typeToken, getGson()).url(this.url, new Object[0])).async()).execute(response -> {
            if (response.hasException()) {
                failed(response.exception());
                return;
            }
            try {
                success(response.get());
            } catch (Exception exception) {
                failed(new WebRequestException(exception));
            }
        });
    }

    protected Gson getGson() {
        return GsonUtil.DEFAULT_GSON;
    }
}
