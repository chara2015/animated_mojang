package net.minecraft.util.profiling.jfr.event;

import jdk.jfr.Category;
import jdk.jfr.Event;
import jdk.jfr.EventType;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.Period;
import jdk.jfr.StackTrace;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.util.profiling.jfr.JfrProfiler;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/event/ClientFpsEvent.class */
@Category({JfrProfiler.ROOT_CATEGORY, JfrProfiler.TICK_CATEGORY})
@Period("1 s")
@Label("Client fps")
@StackTrace(false)
@DontObfuscate
@Name(ClientFpsEvent.EVENT_NAME)
public class ClientFpsEvent extends Event {
    public static final String EVENT_NAME = "minecraft.ClientFps";
    public static final EventType TYPE = EventType.getEventType(ClientFpsEvent.class);

    @Name(Fields.FPS)
    @Label("Client fps")
    public final int fps;

    public ClientFpsEvent(int $$0) {
        this.fps = $$0;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/event/ClientFpsEvent$Fields.class */
    public static class Fields {
        public static final String FPS = "fps";

        private Fields() {
        }
    }
}
