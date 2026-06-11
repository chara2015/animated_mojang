package net.labymod.core.util.io.web.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.loader.LabyModLoader;
import net.labymod.api.models.Implements;
import net.labymod.api.util.StringUtil;
import net.labymod.api.util.collection.map.CaseInsensitiveStringHashMap;
import net.labymod.api.util.function.ThrowableConsumer;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.exception.WebRequestException;
import net.labymod.api.util.io.web.request.Callback;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.request.WebResolver;
import net.labymod.api.util.io.web.request.types.InputStreamRequest;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.loader.DefaultLabyModLoader;
import net.labymod.core.util.logging.DefaultLoggingFactory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/io/web/connection/DefaultWebResolver.class */
@Singleton
@Implements(WebResolver.class)
public class DefaultWebResolver extends WebResolver {
    private static final String USER_AGENT_KEY = "User-Agent";
    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String AUTHORIZATION_KEY = "Authorization";
    private static final String RETRY_AFTER_KEY = "retry-after";
    private static final Executor EXECUTOR = LabyExecutors.newVirtualThreadExecutor("WebResolver#");
    private static final Logging LOGGER = DefaultLoggingFactory.createLogger("WebResolver");
    private static DefaultWebResolver connectionResolver = null;

    @Inject
    public DefaultWebResolver() {
        setInstance(this);
    }

    public static DefaultWebResolver instance() {
        if (connectionResolver == null) {
            connectionResolver = new DefaultWebResolver();
        }
        return connectionResolver;
    }

    private static void setInstance(DefaultWebResolver connectionResolver2) {
        connectionResolver = connectionResolver2;
    }

    @Override // net.labymod.api.util.io.web.request.WebResolver
    public <T> void resolveConnection(Request<T> request, Callback<T> callback) {
        WebResolver.WebRequest<T> webRequest = webRequest(request);
        if (webRequest.isAsync()) {
            EXECUTOR.execute(() -> {
                callback.accept(resolve(webRequest));
            });
        } else {
            callback.accept(resolve(webRequest));
        }
    }

    @Override // net.labymod.api.util.io.web.request.WebResolver
    public <T> Response<T> resolveConnection(Request<T> request) {
        WebResolver.WebRequest<T> webRequest = webRequest(request);
        if (webRequest.isAsync()) {
            throw new IllegalStateException("You cannot create an async connection in a sync-only context!");
        }
        return resolve(webRequest);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> Response<T> resolve(WebResolver.WebRequest<T> webRequest) {
        Exception runtimeException;
        HttpURLConnection httpURLConnection;
        String urlString;
        Response<T> response = (Response<T>) Response.of((WebResolver.WebRequest) webRequest);
        try {
            httpURLConnection = (HttpURLConnection) webRequest.getUrl().openConnection();
            webRequest.getMethod().setRequestMethod(httpURLConnection);
            httpURLConnection.setDoInput(webRequest.doRead());
            httpURLConnection.setDoOutput(webRequest.doWrite());
            for (Map.Entry<String, String> entry : webRequest.getHeaders().entrySet()) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            String userAgent = webRequest.getUserAgent();
            if (userAgent != null) {
                httpURLConnection.setRequestProperty(USER_AGENT_KEY, userAgent);
            }
            String contentType = webRequest.getContentType();
            if (contentType != null) {
                httpURLConnection.setRequestProperty(CONTENT_TYPE_KEY, contentType);
            }
            String authorization = webRequest.getAuthorization();
            urlString = webRequest.getUrlString();
            if (authorization != null) {
                httpURLConnection.setRequestProperty(AUTHORIZATION_KEY, authorization);
            }
            httpURLConnection.setReadTimeout(webRequest.getReadTimeout());
            httpURLConnection.setConnectTimeout(webRequest.getConnectTimeout());
            ThrowableConsumer<OutputStream, IOException> output = webRequest.getOutput();
            logHttpRequest(webRequest.getMethod().name(), urlString);
            if (output != null) {
                OutputStream outputStream = httpURLConnection.getOutputStream();
                try {
                    output.accept(outputStream);
                    if (outputStream != null) {
                        outputStream.close();
                    }
                } finally {
                }
            } else {
                httpURLConnection.connect();
            }
        } catch (Throwable th) {
            if (th instanceof Exception) {
                runtimeException = (Exception) th;
            } else {
                runtimeException = new RuntimeException(th);
            }
            setException(webRequest, response, runtimeException);
        }
        if (!webRequest.doRead()) {
            return response;
        }
        int responseCode = httpURLConnection.getResponseCode();
        response.setResponseCode(responseCode);
        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        if (headerFields != null) {
            CaseInsensitiveStringHashMap caseInsensitiveStringHashMap = new CaseInsensitiveStringHashMap();
            for (Map.Entry<String, List<String>> entry2 : headerFields.entrySet()) {
                List<String> value = entry2.getValue();
                if (value != null && !value.isEmpty()) {
                    caseInsensitiveStringHashMap.put(entry2.getKey(), (String) value.getFirst());
                }
            }
            response.setResponseHeaders(caseInsensitiveStringHashMap);
        }
        if (responseCode == 301) {
            LOGGER.info(urlString + " has been redirected to " + response.getHeaders().get("Location") + " (" + StringUtil.join(response.getHeaders()) + ")", new Object[0]);
        }
        if (webRequest.getMethod() == Request.Method.HEAD) {
            return response;
        }
        boolean zDoHandleErrorStream = webRequest.doHandleErrorStream();
        if (!zDoHandleErrorStream) {
            InputStream errorStream = httpURLConnection.getErrorStream();
            if (errorStream != null) {
                try {
                    String string = IOUtil.toString(errorStream, StandardCharsets.UTF_8);
                    if (!response.getHeaders().containsKey(RETRY_AFTER_KEY)) {
                        setException(webRequest, response, new WebRequestException(urlString, responseCode, string));
                        if (errorStream != null) {
                            errorStream.close();
                        }
                        return response;
                    }
                    String str = response.getHeaders().get(RETRY_AFTER_KEY);
                    long epochMilli = -1;
                    try {
                        epochMilli = Long.parseLong(str);
                    } catch (NumberFormatException e) {
                        try {
                            epochMilli = ZonedDateTime.parse(str, DateTimeFormatter.RFC_1123_DATE_TIME).toInstant().toEpochMilli();
                        } catch (Exception e2) {
                        }
                    }
                    setException(webRequest, response, new WebRequestException(urlString, responseCode, string, epochMilli));
                    if (errorStream != null) {
                        errorStream.close();
                    }
                    return response;
                } finally {
                }
            }
            if (errorStream != null) {
                errorStream.close();
            }
        }
        InputStream inputStream = null;
        if (zDoHandleErrorStream) {
            inputStream = httpURLConnection.getErrorStream();
        }
        if (inputStream == null) {
            inputStream = httpURLConnection.getInputStream();
        }
        if (inputStream == null) {
            setException(webRequest, response, new WebRequestException(urlString, responseCode, "InputStream is null"));
            return response;
        }
        WebInputStream webInputStream = new WebInputStream(inputStream, httpURLConnection.getContentLength());
        try {
            response.set(handle(webRequest, response, webInputStream));
            webInputStream.close();
            try {
                if (!(webRequest.request() instanceof InputStreamRequest)) {
                    inputStream.close();
                }
            } catch (Exception e3) {
            }
            return response;
        } finally {
        }
    }

    @Override // net.labymod.api.util.io.web.request.WebResolver
    public <T> WebResolver.WebRequest<T> webRequest(Request<T> request) {
        return super.webRequest(request);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> void setException(WebResolver.WebRequest<T> request, Response<T> response, Exception exception) {
        try {
            Object objOnException = onException(request, exception);
            if (objOnException == null) {
                response.setException(exception);
            } else {
                response.set(objOnException);
            }
        } catch (Exception e) {
            response.setException(exception);
        }
    }

    private void logHttpRequest(String method, String url) {
        LabyModLoader loader = DefaultLabyModLoader.getInstance();
        if (loader != null && loader.isLabyModDevelopmentEnvironment() && !loader.isAddonDevelopmentEnvironment()) {
            LOGGER.info("[{}] Sending request to {}...", method, url);
        }
    }
}
