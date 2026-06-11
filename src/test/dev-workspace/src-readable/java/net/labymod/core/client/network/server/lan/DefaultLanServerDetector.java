package net.labymod.core.client.network.server.lan;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import net.labymod.api.client.network.server.lan.LanServerCallback;
import net.labymod.api.client.network.server.lan.LanServerDetector;
import net.labymod.api.models.Implements;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/server/lan/DefaultLanServerDetector.class */
@Implements(LanServerDetector.class)
public class DefaultLanServerDetector implements LanServerDetector {
    private static final Logging LOGGER = Logging.create((Class<?>) DefaultLanServerDetector.class);
    private static final AtomicInteger THREAD_ID = new AtomicInteger();
    private static final String MULTICAST_ADDRESS = "224.0.2.60";
    private static final int MULTICAST_PORT = 4445;
    private static final int SOCKET_TIMEOUT = 5000;
    private InetAddress multicastAddress;
    private MulticastSocket socket;
    private final Collection<ScheduledFuture<?>> tasks = new HashSet();
    private final LanServerDetectionTask detectionTask = new LanServerDetectionTask();
    private final ScheduledExecutorService executor = LabyExecutors.newScheduledThreadPool(2, "LanServerDetector-" + THREAD_ID.incrementAndGet() + "-%d");

    @Override // net.labymod.api.client.network.server.lan.LanServerDetector
    @NotNull
    public InetAddress broadCastAddress() {
        return this.multicastAddress;
    }

    @Override // net.labymod.api.client.network.server.lan.LanServerDetector
    public void terminateDetectionTask() {
        if (this.socket != null) {
            this.socket.close();
            this.socket = null;
        }
        if (!this.tasks.isEmpty()) {
            for (ScheduledFuture<?> task : this.tasks) {
                task.cancel(true);
            }
            this.tasks.clear();
        }
    }

    @Override // net.labymod.api.client.network.server.lan.LanServerDetector
    public void startDetectionTask(@NotNull LanServerCallback callback) {
        try {
            terminateDetectionTask();
            this.multicastAddress = InetAddress.getByName(MULTICAST_ADDRESS);
            this.socket = new MulticastSocket(MULTICAST_PORT);
            this.socket.setSoTimeout(SOCKET_TIMEOUT);
            this.socket.joinGroup(new InetSocketAddress(this.multicastAddress, 0), null);
            this.detectionTask.setSocket(this.socket);
            this.detectionTask.setCallback(callback);
            this.tasks.add(this.executor.scheduleWithFixedDelay(this.detectionTask, 1L, 1L, TimeUnit.MILLISECONDS));
            Collection<ScheduledFuture<?>> collection = this.tasks;
            ScheduledExecutorService scheduledExecutorService = this.executor;
            LanServerDetectionTask lanServerDetectionTask = this.detectionTask;
            Objects.requireNonNull(lanServerDetectionTask);
            collection.add(scheduledExecutorService.scheduleAtFixedRate(lanServerDetectionTask::handleRemoval, 1L, 1L, TimeUnit.SECONDS));
        } catch (Exception exception) {
            LOGGER.error("Failed to initialize LanServerDetector, skipping lan server collection", exception);
        }
    }

    @Override // net.labymod.api.client.network.server.lan.LanServerDetector
    public void reset() {
        this.detectionTask.reset();
    }

    @Override // net.labymod.api.client.network.server.lan.LanServerDetector
    public void close() {
        terminateDetectionTask();
        this.executor.shutdownNow();
    }
}
