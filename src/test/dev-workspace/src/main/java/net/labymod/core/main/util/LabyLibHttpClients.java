package net.labymod.core.main.util;

import java.io.IOException;
import javax.inject.Singleton;
import net.laby.lib.http.HttpClient;
import net.laby.lib.http.cache.CacheStore;
import net.laby.lib.http.cache.CachingHttpClient;
import net.laby.lib.http.cache.FileSystemCacheStore;
import net.laby.lib.http.cache.InMemoryCacheStore;
import net.laby.lib.http.impl.jdk.JdkHttpClient;
import net.laby.lib.http.validation.BodyDeserializer;
import net.laby.lib.http.validation.ResponseValidator;
import net.laby.lib.http.validation.ValidatedHttpClient;
import net.labymod.api.Constants;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/util/LabyLibHttpClients.class */
@Singleton
@Referenceable
public final class LabyLibHttpClients {
    private final HttpClient httpClient = new JdkHttpClient();
    private final HttpClient cachingHttpClient = new CachingHttpClient(this.httpClient, createStore());

    public HttpClient httpClient() {
        return this.httpClient;
    }

    public HttpClient cachingHttpClient() {
        return this.cachingHttpClient;
    }

    public ValidatedHttpClient createValidatedHttpClient(BodyDeserializer deserializer) {
        return new ValidatedHttpClient(this.cachingHttpClient, new ResponseValidator(deserializer));
    }

    private CacheStore createStore() {
        try {
            return new FileSystemCacheStore(Constants.Files.FILE_CACHE.resolve("labymod_remote"));
        } catch (IOException e) {
            return new InMemoryCacheStore();
        }
    }
}
