package net.minecraft.client.telemetry.events;

import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import net.minecraft.client.telemetry.TelemetryEventSender;
import net.minecraft.client.telemetry.TelemetryEventType;
import net.minecraft.client.telemetry.TelemetryProperty;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/telemetry/events/GameLoadTimesEvent.class */
public class GameLoadTimesEvent {
    public static final GameLoadTimesEvent INSTANCE = new GameLoadTimesEvent(Ticker.systemTicker());
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Ticker timeSource;
    private final Map<TelemetryProperty<Measurement>, Stopwatch> measurements = new HashMap();
    private OptionalLong bootstrapTime = OptionalLong.empty();

    protected GameLoadTimesEvent(Ticker $$0) {
        this.timeSource = $$0;
    }

    public synchronized void beginStep(TelemetryProperty<Measurement> $$0) {
        beginStep($$0, $$02 -> {
            return Stopwatch.createStarted(this.timeSource);
        });
    }

    public synchronized void beginStep(TelemetryProperty<Measurement> $$0, Stopwatch $$1) {
        beginStep($$0, $$12 -> {
            return $$1;
        });
    }

    private synchronized void beginStep(TelemetryProperty<Measurement> $$0, Function<TelemetryProperty<Measurement>, Stopwatch> $$1) {
        this.measurements.computeIfAbsent($$0, $$1);
    }

    public synchronized void endStep(TelemetryProperty<Measurement> $$0) {
        Stopwatch $$1 = this.measurements.get($$0);
        if ($$1 == null) {
            LOGGER.warn("Attempted to end step for {} before starting it", $$0.id());
        } else if ($$1.isRunning()) {
            $$1.stop();
        }
    }

    public void send(TelemetryEventSender $$0) {
        $$0.send(TelemetryEventType.GAME_LOAD_TIMES, $$02 -> {
            synchronized (this) {
                this.measurements.forEach(($$1, $$2) -> {
                    if (!$$2.isRunning()) {
                        long $$3 = $$2.elapsed(TimeUnit.MILLISECONDS);
                        $$02.put($$1, new Measurement((int) $$3));
                    } else {
                        LOGGER.warn("Measurement {} was discarded since it was still ongoing when the event {} was sent.", $$1.id(), TelemetryEventType.GAME_LOAD_TIMES.id());
                    }
                });
                this.bootstrapTime.ifPresent($$12 -> {
                    $$02.put(TelemetryProperty.LOAD_TIME_BOOTSTRAP_MS, new Measurement((int) $$12));
                });
                this.measurements.clear();
            }
        });
    }

    public synchronized void setBootstrapTime(long $$0) {
        this.bootstrapTime = OptionalLong.of($$0);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/telemetry/events/GameLoadTimesEvent$Measurement.class */
    public static final class Measurement extends Record {
        private final int millis;
        public static final Codec<Measurement> CODEC = Codec.INT.xmap((v1) -> {
            return new Measurement(v1);
        }, $$0 -> {
            return Integer.valueOf($$0.millis);
        });

        public Measurement(int $$0) {
            this.millis = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Measurement.class), Measurement.class, "millis", "FIELD:Lnet/minecraft/client/telemetry/events/GameLoadTimesEvent$Measurement;->millis:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Measurement.class), Measurement.class, "millis", "FIELD:Lnet/minecraft/client/telemetry/events/GameLoadTimesEvent$Measurement;->millis:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Measurement.class, Object.class), Measurement.class, "millis", "FIELD:Lnet/minecraft/client/telemetry/events/GameLoadTimesEvent$Measurement;->millis:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int millis() {
            return this.millis;
        }
    }
}
