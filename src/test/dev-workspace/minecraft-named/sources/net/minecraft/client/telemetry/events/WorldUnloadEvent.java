package net.minecraft.client.telemetry.events;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import net.minecraft.client.telemetry.TelemetryEventSender;
import net.minecraft.client.telemetry.TelemetryEventType;
import net.minecraft.client.telemetry.TelemetryProperty;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/telemetry/events/WorldUnloadEvent.class */
public class WorldUnloadEvent {
    private static final int NOT_TRACKING_TIME = -1;
    private Optional<Instant> worldLoadedTime = Optional.empty();
    private long totalTicks;
    private long lastGameTime;

    public void onPlayerInfoReceived() {
        this.lastGameTime = -1L;
        if (this.worldLoadedTime.isEmpty()) {
            this.worldLoadedTime = Optional.of(Instant.now());
        }
    }

    public void setTime(long $$0) {
        if (this.lastGameTime != -1) {
            this.totalTicks += Math.max(0L, $$0 - this.lastGameTime);
        }
        this.lastGameTime = $$0;
    }

    private int getTimeInSecondsSinceLoad(Instant $$0) {
        Duration $$1 = Duration.between($$0, Instant.now());
        return (int) $$1.toSeconds();
    }

    public void send(TelemetryEventSender $$0) {
        this.worldLoadedTime.ifPresent($$1 -> {
            $$0.send(TelemetryEventType.WORLD_UNLOADED, $$1 -> {
                $$1.put(TelemetryProperty.SECONDS_SINCE_LOAD, Integer.valueOf(getTimeInSecondsSinceLoad($$1)));
                $$1.put(TelemetryProperty.TICKS_SINCE_LOAD, Integer.valueOf((int) this.totalTicks));
            });
        });
    }
}
