package net.labymod.core.client.network.server;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.network.server.PingProtectionService;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ServerInfo;
import net.labymod.api.client.network.server.ServerPinger;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig;
import net.labymod.api.models.Implements;
import net.labymod.api.util.Lazy;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.network.server.ping.LegacyServerPing;
import net.labymod.core.client.network.server.ping.ServerPing;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/server/DefaultServerPinger.class */
@Singleton
@Implements(ServerPinger.class)
public class DefaultServerPinger implements ServerPinger {
    private static final Logging LOGGER = Logging.create((Class<?>) DefaultServerPinger.class);
    private final ExecutorService executorService = LabyExecutors.newFixedThreadPool(8, "ServerPinger#%d");
    private final Map<ServerAddress, PendingRequest> pendingRequests = new ConcurrentHashMap();
    private final List<PingProtectionService> protectionServices = new CopyOnWriteArrayList();
    private final Lazy<ServerListConfig> serverListConfig = Lazy.of(() -> {
        return Laby.labyAPI().config().multiplayer().serverList();
    });

    public DefaultServerPinger() {
        registerPingProtectionService(new ProxyPingProtectionService());
        registerPingProtectionService(new LabyNetPingProtectionService());
    }

    @Override // net.labymod.api.client.network.server.ServerPinger
    public ServerInfo pingServer(String name, ServerAddress address, int timeout, boolean protectIp) throws IOException {
        ServerListConfig serverListConfig = this.serverListConfig.get();
        boolean protectionEnabled = protectIp && serverListConfig.proxyServerPing().get().booleanValue();
        if (protectionEnabled) {
            for (PingProtectionService service : this.protectionServices) {
                if (service.supports(address)) {
                    try {
                        ServerInfo serverInfo = service.ping(name, address, timeout);
                        serverInfo.setProxied(true);
                        return serverInfo;
                    } catch (IOException e) {
                        LOGGER.debug("Protection service {} unreachable for {}", service, address);
                    }
                }
            }
            boolean strictMode = serverListConfig.strictProxyMode().get().booleanValue();
            if (strictMode) {
                LOGGER.debug("All protection services failed for {}, strict mode enabled", address);
                return ServerInfo.failed(name, address, ServerInfo.Status.PROXY_UNAVAILABLE);
            }
            LOGGER.debug("All protection services failed for {}, falling back to direct ping", address);
        }
        return pingDirectly(name, address, timeout);
    }

    @Override // net.labymod.api.client.network.server.ServerPinger
    public CompletableFuture<ServerInfo> pingServerAsync(String name, ServerAddress address, int timeout, boolean protectIp) {
        CompletableFuture<ServerInfo> future = new CompletableFuture<>();
        PendingRequest cachedRequest = this.pendingRequests.get(address);
        if (cachedRequest != null) {
            cachedRequest.futures.add(future);
            return future;
        }
        PendingRequest request = new PendingRequest(future);
        this.pendingRequests.put(address, request);
        this.executorService.execute(() -> {
            try {
                ServerInfo serverInfo = pingServer(name, address, timeout, protectIp);
                this.pendingRequests.remove(address);
                request.complete(serverInfo);
            } catch (Exception exception) {
                this.pendingRequests.remove(address);
                request.completeExceptionally(exception);
            }
        });
        return future;
    }

    @Override // net.labymod.api.client.network.server.ServerPinger
    public ResourceLocation getDefaultServerIcon() {
        return Textures.DEFAULT_SERVER_ICON;
    }

    public void registerPingProtectionService(PingProtectionService service) {
        this.protectionServices.add(service);
    }

    private ServerInfo pingDirectly(String name, ServerAddress address, int timeout) throws IOException {
        int clientVersion = Laby.labyAPI().minecraft().getProtocolVersion();
        try {
            ServerPing ping = new ServerPing(name, address, null, timeout, clientVersion);
            try {
                ServerInfo serverInfoRetrieveServerInfo = ping.retrieveServerInfo();
                ping.close();
                return serverInfoRetrieveServerInfo;
            } finally {
            }
        } catch (EOFException e) {
            LegacyServerPing legacyServerPing = new LegacyServerPing(name, address, null, timeout, clientVersion);
            try {
                ServerInfo serverInfoRetrieveServerInfo2 = legacyServerPing.retrieveServerInfo();
                legacyServerPing.close();
                return serverInfoRetrieveServerInfo2;
            } catch (Throwable th) {
                try {
                    legacyServerPing.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/server/DefaultServerPinger$PendingRequest.class */
    private static class PendingRequest {
        private final List<CompletableFuture<ServerInfo>> futures = new ArrayList();

        private PendingRequest(CompletableFuture<ServerInfo> future) {
            this.futures.add(future);
        }

        private void complete(ServerInfo serverInfo) {
            for (CompletableFuture<ServerInfo> future : this.futures) {
                future.complete(serverInfo);
            }
        }

        private void completeExceptionally(Throwable throwable) {
            for (CompletableFuture<ServerInfo> future : this.futures) {
                future.completeExceptionally(throwable);
            }
        }
    }
}
