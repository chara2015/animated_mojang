package net.minecraft.util.profiling.jfr.event;

import jdk.jfr.Category;
import jdk.jfr.Event;
import jdk.jfr.EventType;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.Period;
import jdk.jfr.StackTrace;
import jdk.jfr.Timespan;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.util.profiling.jfr.JfrProfiler;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/event/ServerTickTimeEvent.class */
@Category({JfrProfiler.ROOT_CATEGORY, JfrProfiler.TICK_CATEGORY})
@Period("1 s")
@Label("Server Tick Time")
@StackTrace(false)
@DontObfuscate
@Name(ServerTickTimeEvent.EVENT_NAME)
public class ServerTickTimeEvent extends Event {
    public static final String EVENT_NAME = "minecraft.ServerTickTime";
    public static final EventType TYPE = EventType.getEventType(ServerTickTimeEvent.class);

    @Name(Fields.AVERAGE_TICK_DURATION)
    @Label("Average Server Tick Duration")
    @Timespan
    public final long averageTickDurationNanos;

    public ServerTickTimeEvent(float $$0) {
        this.averageTickDurationNanos = (long) (1000000.0f * $$0);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/event/ServerTickTimeEvent$Fields.class */
    public static class Fields {
        public static final String AVERAGE_TICK_DURATION = "averageTickDuration";

        private Fields() {
        }
    }
}
