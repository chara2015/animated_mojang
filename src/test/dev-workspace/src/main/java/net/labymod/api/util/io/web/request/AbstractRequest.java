package net.labymod.api.util.io.web.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import net.labymod.api.BuildData;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.function.ThrowableConsumer;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.AbstractRequest;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.WebResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/request/AbstractRequest.class */
public abstract class AbstractRequest<T, R extends AbstractRequest<T, ?>> implements Request<T> {
    private static final String DEFAULT_USER_AGENT = BuildData.getUserAgent();
    private static final int DEFAULT_READ_TIMEOUT = 5000;
    private static final int DEFAULT_CONNECT_TIMEOUT = 2000;
    protected URL url;
    protected String authorization;
    protected String contentType;
    protected ThrowableConsumer<OutputStream, IOException> write;
    protected Map<String, String> headers = new HashMap();
    protected Request.Method method = Request.Method.GET;
    protected String userAgent = DEFAULT_USER_AGENT;
    protected int readTimeout = DEFAULT_READ_TIMEOUT;
    protected int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    protected boolean async = false;
    protected boolean read = true;
    protected boolean handleErrorStream = false;

    protected abstract R prepareCopy();

    protected abstract T handle(Response<T> response, WebInputStream webInputStream) throws Exception;

    @Override // net.labymod.api.util.io.web.request.Request
    public /* bridge */ /* synthetic */ Request form(List list) {
        return form((List<FormData>) list);
    }

    @Override // net.labymod.api.util.io.web.request.Request
    public /* bridge */ /* synthetic */ Request body(Map map) {
        return body((Map<String, String>) map);
    }

    @Override // net.labymod.api.util.io.web.request.Request
    public R url(@NotNull String str, Object... objArr) {
        Objects.requireNonNull(str, "URL cannot be null");
        if (objArr.length > 0) {
            str = String.format(Locale.ROOT, str, objArr);
        }
        try {
            return (R) url(new URL(str));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid url: " + str);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.util.io.web.request.Request
    public R url(@NotNull URL url) {
        Objects.requireNonNull(url, "URL cannot be null");
        this.url = url;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.util.io.web.request.Request
    public R authorization(@NotNull String authorizationType, @NotNull String authorization) {
        Objects.requireNonNull(authorizationType, "Authorization type cannot be null");
        Objects.requireNonNull(authorization, "Authorization cannot be null");
        this.authorization = authorizationType + " " + authorization;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.util.io.web.request.Request
    public R userAgent(@Nullable String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.util.io.web.request.Request
    public R contentType(@Nullable String contentType) {
        this.contentType = contentType;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.util.io.web.request.Request
    public R readTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.util.io.web.request.Request
    public R connectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.util.io.web.request.Request
    public R addHeader(String key, Object value) {
        this.headers.put(key, value.toString());
        return this;
    }

    @Override // net.labymod.api.util.io.web.request.Request
    public R async() {
        return (R) async(true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.util.io.web.request.Request
    public R async(boolean value) {
        this.async = value;
        return this;
    }

    @Override // net.labymod.api.util.io.web.request.Request
    public R handleErrorStream() {
        return (R) handleErrorStream(true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.util.io.web.request.Request
    public R handleErrorStream(boolean value) {
        this.handleErrorStream = value;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.util.io.web.request.Request
    public R read(boolean read) {
        this.read = read;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.util.io.web.request.Request
    public R write(Object write) {
        this.write = outputStream -> {
            if (write instanceof String) {
                outputStream.write(((String) write).getBytes(StandardCharsets.UTF_8));
            } else if (write instanceof byte[]) {
                outputStream.write((byte[]) write);
            } else {
                if (write instanceof InputStream) {
                    IOUtil.writeBytes((InputStream) write, outputStream);
                    return;
                }
                throw new IllegalArgumentException("Unsupported write type: " + write.getClass().getName());
            }
        };
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.util.io.web.request.Request
    public R body(Map<String, String> body) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : body.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        write((Object) builder.toString());
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.util.io.web.request.Request
    public R form(FormData... formDataArr) {
        if (formDataArr.length == 0) {
            return this;
        }
        return (R) form((List<FormData>) Arrays.asList(formDataArr));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.util.io.web.request.Request
    public R form(List<FormData> formData) {
        this.contentType = "multipart/form-data; boundary=" + FormData.BOUNDARY;
        long contentLength = 0;
        for (FormData data : formData) {
            long contentLength2 = contentLength + ((long) FormData.BOUNDARY_BYTES.length) + ((long) data.getContentDispositionHeader().getBytes(StandardCharsets.UTF_8).length) + ((long) FormData.NEW_LINE_BYTES.length);
            if (data.getContentType() != null) {
                contentLength2 = contentLength2 + ((long) data.getContentTypeHeader().getBytes(StandardCharsets.UTF_8).length) + ((long) FormData.NEW_LINE_BYTES.length);
            }
            contentLength = contentLength2 + ((long) FormData.NEW_LINE_BYTES.length) + data.getLength() + ((long) FormData.NEW_LINE_BYTES.length);
        }
        addHeader("Content-Length", (Object) Long.valueOf(contentLength + ((long) FormData.BOUNDARY_END_BYTES.length)));
        this.write = outputStream -> {
            Iterator i$ = formData.iterator();
            while (i$.hasNext()) {
                FormData data2 = (FormData) i$.next();
                outputStream.write(FormData.BOUNDARY_BYTES);
                outputStream.write(data2.getContentDispositionHeader().getBytes(StandardCharsets.UTF_8));
                outputStream.write(FormData.NEW_LINE_BYTES);
                if (data2.getContentType() != null) {
                    outputStream.write(data2.getContentTypeHeader().getBytes(StandardCharsets.UTF_8));
                    outputStream.write(FormData.NEW_LINE_BYTES);
                }
                outputStream.write(FormData.NEW_LINE_BYTES);
                IOUtil.writeBytes(data2.getValue(), outputStream);
                outputStream.write(FormData.NEW_LINE_BYTES);
            }
            outputStream.write(FormData.BOUNDARY_END_BYTES);
        };
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.util.io.web.request.Request
    public R json(Object body) {
        if (this.contentType == null) {
            this.contentType = "application/json";
        }
        write((Object) GsonUtil.DEFAULT_GSON.toJson(body));
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.util.io.web.request.Request
    public R method(@NotNull Request.Method method) {
        Objects.requireNonNull(method, "Method cannot be null");
        this.method = method;
        return this;
    }

    @Override // net.labymod.api.util.io.web.request.Request
    public void execute(Callback<T> callback) {
        WebResolver.resolve(this, callback);
    }

    @Override // net.labymod.api.util.io.web.request.Request
    public Response<T> executeSync() {
        return WebResolver.resolveSync(this);
    }

    @Override // net.labymod.api.util.io.web.request.Request
    public R copy() {
        R r = (R) prepareCopy();
        r.headers = new HashMap(this.headers);
        r.method = this.method;
        r.url = this.url;
        r.authorization = this.authorization;
        r.userAgent = this.userAgent;
        r.contentType = this.contentType;
        r.write = this.write;
        r.readTimeout = this.readTimeout;
        r.connectTimeout = this.connectTimeout;
        r.async = this.async;
        r.handleErrorStream = this.handleErrorStream;
        r.read = this.read;
        return r;
    }

    protected WebResolver.WebRequest<T> build() {
        return new WebResolver.WebRequest<>(this);
    }

    protected final String readString(WebInputStream inputStream) throws IOException {
        return IOUtil.toString(inputStream, StandardCharsets.UTF_8);
    }

    protected T onException(Exception exception) throws Exception {
        return null;
    }
}
