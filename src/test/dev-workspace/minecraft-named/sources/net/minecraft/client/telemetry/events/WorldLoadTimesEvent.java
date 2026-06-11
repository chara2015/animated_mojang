package net.minecraft.client.telemetry.events;

import java.time.Duration;
import net.minecraft.client.telemetry.TelemetryEventSender;
import net.minecraft.client.telemetry.TelemetryEventType;
import net.minecraft.client.telemetry.TelemetryProperty;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/telemetry/events/WorldLoadTimesEvent.class */
public class WorldLoadTimesEvent {
    private final boolean newWorld;
    private final Duration worldLoadDuration;

    public WorldLoadTimesEvent(boolean $$0, Duration $$1) {
        this.worldLoadDuration = $$1;
        this.newWorld = $$0;
    }

    public void send(TelemetryEventSender $$0) {
        if (this.worldLoadDuration != null) {
            $$0.send(TelemetryEventType.WORLD_LOAD_TIMES, $$02 -> {
                $$02.put(TelemetryProperty.WORLD_LOAD_TIME_MS, Integer.valueOf((int) this.worldLoadDuration.toMillis()));
                $$02.put(TelemetryProperty.NEW_WORLD, Boolean.valueOf(this.newWorld));
            });
        }
    }
}
