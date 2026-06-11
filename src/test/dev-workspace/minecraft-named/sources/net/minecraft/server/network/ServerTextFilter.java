package net.minecraft.server.network;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.FilterMask;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.LenientJsonParser;
import net.minecraft.util.StringUtil;
import net.minecraft.util.Util;
import net.minecraft.util.thread.ConsecutiveExecutor;
import net.minecraft.world.level.block.LevelEvent;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/network/ServerTextFilter.class */
public abstract class ServerTextFilter implements AutoCloseable {
    protected static final Logger LOGGER = LogUtils.getLogger();
    private static final AtomicInteger WORKER_COUNT = new AtomicInteger(1);
    private static final ThreadFactory THREAD_FACTORY = $$0 -> {
        Thread $$1 = new Thread($$0);
        $$1.setName("Chat-Filter-Worker-" + WORKER_COUNT.getAndIncrement());
        return $$1;
    };
    private final URL chatEndpoint;
    private final MessageEncoder chatEncoder;
    final IgnoreStrategy chatIgnoreStrategy;
    final ExecutorService workerPool;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/network/ServerTextFilter$MessageEncoder.class */
    @FunctionalInterface
    protected interface MessageEncoder {
        JsonObject encode(GameProfile gameProfile, String str);
    }

    protected abstract FilteredText filterText(String str, IgnoreStrategy ignoreStrategy, JsonObject jsonObject);

    protected abstract void setAuthorizationProperty(HttpURLConnection httpURLConnection);

    protected static ExecutorService createWorkerPool(int $$0) {
        return Executors.newFixedThreadPool($$0, THREAD_FACTORY);
    }

    protected ServerTextFilter(URL $$0, MessageEncoder $$1, IgnoreStrategy $$2, ExecutorService $$3) {
        this.chatIgnoreStrategy = $$2;
        this.workerPool = $$3;
        this.chatEndpoint = $$0;
        this.chatEncoder = $$1;
    }

    protected static URL getEndpoint(URI $$0, JsonObject $$1, String $$2, String $$3) throws MalformedURLException {
        String $$4 = getEndpointFromConfig($$1, $$2, $$3);
        return $$0.resolve("/" + $$4).toURL();
    }

    protected static String getEndpointFromConfig(JsonObject $$0, String $$1, String $$2) {
        return $$0 != null ? GsonHelper.getAsString($$0, $$1, $$2) : $$2;
    }

    public static ServerTextFilter createFromConfig(DedicatedServerProperties $$0) {
        String $$1 = $$0.textFilteringConfig;
        if (StringUtil.isBlank($$1)) {
            return null;
        }
        switch ($$0.textFilteringVersion) {
            case 0:
                break;
            case 1:
                break;
            default:
                LOGGER.warn("Could not create text filter - unsupported text filtering version used");
                break;
        }
        return null;
    }

    protected CompletableFuture<FilteredText> requestMessageProcessing(GameProfile $$0, String $$1, IgnoreStrategy $$2, Executor $$3) {
        if ($$1.isEmpty()) {
            return CompletableFuture.completedFuture(FilteredText.EMPTY);
        }
        return CompletableFuture.supplyAsync(() -> {
            JsonObject $$32 = this.chatEncoder.encode($$0, $$1);
            try {
                JsonObject $$4 = processRequestResponse($$32, this.chatEndpoint);
                return filterText($$1, $$2, $$4);
            } catch (Exception $$5) {
                LOGGER.warn("Failed to validate message '{}'", $$1, $$5);
                return FilteredText.fullyFiltered($$1);
            }
        }, $$3);
    }

    protected FilterMask parseMask(String $$0, JsonArray $$1, IgnoreStrategy $$2) {
        if ($$1.isEmpty()) {
            return FilterMask.PASS_THROUGH;
        }
        if ($$2.shouldIgnore($$0, $$1.size())) {
            return FilterMask.FULLY_FILTERED;
        }
        FilterMask $$3 = new FilterMask($$0.length());
        for (int $$4 = 0; $$4 < $$1.size(); $$4++) {
            $$3.setFiltered($$1.get($$4).getAsInt());
        }
        return $$3;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.workerPool.shutdownNow();
    }

    protected void drainStream(InputStream $$0) throws IOException {
        byte[] $$1 = new byte[1024];
        while ($$0.read($$1) != -1) {
        }
    }

    private JsonObject processRequestResponse(JsonObject $$0, URL $$1) throws IOException {
        HttpURLConnection $$2 = makeRequest($$0, $$1);
        InputStream $$3 = $$2.getInputStream();
        try {
            if ($$2.getResponseCode() == 204) {
                JsonObject jsonObject = new JsonObject();
                if ($$3 != null) {
                    $$3.close();
                }
                return jsonObject;
            }
            try {
                JsonObject asJsonObject = LenientJsonParser.parse(new InputStreamReader($$3, StandardCharsets.UTF_8)).getAsJsonObject();
                drainStream($$3);
                if ($$3 != null) {
                    $$3.close();
                }
                return asJsonObject;
            } catch (Throwable th) {
                drainStream($$3);
                throw th;
            }
        } catch (Throwable th2) {
            if ($$3 != null) {
                try {
                    $$3.close();
                } catch (Throwable th3) {
                    th2.addSuppressed(th3);
                }
            }
            throw th2;
        }
    }

    protected HttpURLConnection makeRequest(JsonObject $$0, URL $$1) throws IOException {
        HttpURLConnection $$2 = getURLConnection($$1);
        setAuthorizationProperty($$2);
        OutputStreamWriter $$3 = new OutputStreamWriter($$2.getOutputStream(), StandardCharsets.UTF_8);
        try {
            JsonWriter $$4 = new JsonWriter($$3);
            try {
                Streams.write($$0, $$4);
                $$4.close();
                $$3.close();
                int $$5 = $$2.getResponseCode();
                if ($$5 < 200 || $$5 >= 300) {
                    throw new RequestFailedException($$5 + " " + $$2.getResponseMessage());
                }
                return $$2;
            } finally {
            }
        } catch (Throwable th) {
            try {
                $$3.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    protected int connectionReadTimeout() {
        return LevelEvent.PARTICLES_SHOOT_SMOKE;
    }

    protected HttpURLConnection getURLConnection(URL $$0) throws IOException {
        HttpURLConnection $$1 = (HttpURLConnection) $$0.openConnection();
        $$1.setConnectTimeout(ServerCommonPacketListenerImpl.LATENCY_CHECK_INTERVAL);
        $$1.setReadTimeout(connectionReadTimeout());
        $$1.setUseCaches(false);
        $$1.setDoOutput(true);
        $$1.setDoInput(true);
        $$1.setRequestMethod("POST");
        $$1.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        $$1.setRequestProperty("Accept", "application/json");
        $$1.setRequestProperty("User-Agent", "Minecraft server" + SharedConstants.getCurrentVersion().name());
        return $$1;
    }

    public TextFilter createContext(GameProfile $$0) {
        return new PlayerContext($$0);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/network/ServerTextFilter$RequestFailedException.class */
    protected static class RequestFailedException extends RuntimeException {
        protected RequestFailedException(String $$0) {
            super($$0);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/network/ServerTextFilter$PlayerContext.class */
    protected class PlayerContext implements TextFilter {
        protected final GameProfile profile;
        protected final Executor streamExecutor;

        protected PlayerContext(GameProfile $$1) {
            this.profile = $$1;
            ConsecutiveExecutor $$2 = new ConsecutiveExecutor(ServerTextFilter.this.workerPool, "chat stream for " + $$1.name());
            Objects.requireNonNull($$2);
            this.streamExecutor = $$2::schedule;
        }

        @Override // net.minecraft.server.network.TextFilter
        public CompletableFuture<List<FilteredText>> processMessageBundle(List<String> $$0) {
            List<CompletableFuture<FilteredText>> $$1 = (List) $$0.stream().map($$02 -> {
                return ServerTextFilter.this.requestMessageProcessing(this.profile, $$02, ServerTextFilter.this.chatIgnoreStrategy, this.streamExecutor);
            }).collect(ImmutableList.toImmutableList());
            return Util.sequenceFailFast($$1).exceptionally($$03 -> {
                return ImmutableList.of();
            });
        }

        @Override // net.minecraft.server.network.TextFilter
        public CompletableFuture<FilteredText> processStreamMessage(String $$0) {
            return ServerTextFilter.this.requestMessageProcessing(this.profile, $$0, ServerTextFilter.this.chatIgnoreStrategy, this.streamExecutor);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/network/ServerTextFilter$IgnoreStrategy.class */
    @FunctionalInterface
    public interface IgnoreStrategy {
        public static final IgnoreStrategy NEVER_IGNORE = ($$0, $$1) -> {
            return false;
        };
        public static final IgnoreStrategy IGNORE_FULLY_FILTERED = ($$0, $$1) -> {
            return $$0.length() == $$1;
        };

        boolean shouldIgnore(String str, int i);

        static IgnoreStrategy ignoreOverThreshold(int $$0) {
            return ($$1, $$2) -> {
                return $$2 >= $$0;
            };
        }

        static IgnoreStrategy select(int $$0) {
            switch ($$0) {
                case -1:
                    return NEVER_IGNORE;
                case 0:
                    return IGNORE_FULLY_FILTERED;
                default:
                    return ignoreOverThreshold($$0);
            }
        }
    }
}
