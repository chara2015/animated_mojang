package net.labymod.api.util.io.web.request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.labymod.api.util.io.web.request.types.FileRequest;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.io.web.request.types.InputStreamRequest;
import net.labymod.api.util.io.web.request.types.StringRequest;
import net.labymod.api.util.io.web.request.types.TypeTokenGsonRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/request/Request.class */
public interface Request<T> {
    Request<T> url(@NotNull String str, Object... objArr);

    Request<T> url(@NotNull URL url);

    Request<T> authorization(@NotNull String str, @NotNull String str2);

    Request<T> userAgent(@Nullable String str);

    Request<T> contentType(@Nullable String str);

    Request<T> readTimeout(int i);

    Request<T> connectTimeout(int i);

    Request<T> addHeader(String str, Object obj);

    Request<T> async();

    Request<T> async(boolean z);

    Request<T> handleErrorStream();

    Request<T> handleErrorStream(boolean z);

    Request<T> read(boolean z);

    Request<T> write(Object obj);

    Request<T> body(Map<String, String> map);

    Request<T> form(FormData... formDataArr);

    Request<T> form(List<FormData> list);

    Request<T> json(Object obj);

    Request<T> method(@NotNull Method method);

    void execute(Callback<T> callback);

    Response<T> executeSync();

    Request<T> copy();

    static <T> GsonRequest<T> ofGson(@NotNull Class<T> type) {
        return GsonRequest.of(type);
    }

    static <T> GsonRequest<T> ofGson(@NotNull Class<T> type, @NotNull Gson gson) {
        return GsonRequest.of(type, gson);
    }

    static <T> GsonRequest<T> ofGson(@NotNull Class<T> type, @NotNull Supplier<Gson> gsonSupplier) {
        return GsonRequest.of(type, gsonSupplier);
    }

    static <T> TypeTokenGsonRequest<T> ofGson(@NotNull TypeToken<T> typeToken) {
        return TypeTokenGsonRequest.of(typeToken);
    }

    static <T> TypeTokenGsonRequest<T> ofGson(@NotNull TypeToken<T> typeToken, @NotNull Gson gson) {
        return TypeTokenGsonRequest.of(typeToken, gson);
    }

    static <T> TypeTokenGsonRequest<T> ofGson(@NotNull TypeToken<T> typeToken, @NotNull Supplier<Gson> gsonSupplier) {
        return TypeTokenGsonRequest.of(typeToken, gsonSupplier);
    }

    static <T> TypeTokenGsonRequest<List<T>> ofGsonList(@NotNull Class<T> type) {
        return TypeTokenGsonRequest.ofList(type);
    }

    static <T> TypeTokenGsonRequest<List<T>> ofGsonList(@NotNull Class<T> type, @NotNull Supplier<Gson> gsonSupplier) {
        return TypeTokenGsonRequest.ofList(type, gsonSupplier);
    }

    static InputStreamRequest ofInputStream() {
        return InputStreamRequest.create();
    }

    static StringRequest ofString() {
        return StringRequest.create();
    }

    static FileRequest ofFile(@NotNull Path path) {
        return FileRequest.of(path);
    }

    static FileRequest ofFile(@NotNull Path path, Consumer<Double> percentageConsumer) {
        return FileRequest.of(path, percentageConsumer);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/request/Request$Method.class */
    public enum Method {
        GET,
        HEAD,
        POST,
        PUT,
        DELETE,
        CONNECT,
        OPTIONS,
        TRACE,
        PATCH;

        public void setRequestMethod(HttpURLConnection connection) throws IOException {
            if (this == PATCH) {
                connection.setRequestMethod(POST.name());
                connection.setRequestProperty("X-HTTP-Method-Override", name());
            } else {
                connection.setRequestMethod(name());
            }
        }
    }
}
