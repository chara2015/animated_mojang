package net.labymod.core.client.worldsharing.network;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.worldsharing.network.NetworkEvent;
import net.labymod.core.client.worldsharing.network.events.JoinWorldEvent;
import net.labymod.core.client.worldsharing.network.events.LocalServerInviteEvent;
import net.labymod.core.client.worldsharing.network.events.TunnelRequestEvent;
import net.labymod.core.client.worldsharing.network.events.WorldConfigEvent;
import net.labymod.core.client.worldsharing.network.events.WorldInviteEvent;
import net.labymod.core.client.worldsharing.network.events.WorldReadyEvent;
import net.labymod.core.client.worldsharing.network.events.WorldSharedEvent;
import net.labymod.core.client.worldsharing.network.events.WorldStopEvent;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/network/EventCodec.class */
@Singleton
public class EventCodec {
    private final Map<String, Class<? extends NetworkEvent>> subjectToEvents = new HashMap();
    private final Map<Class<? extends NetworkEvent>, String> eventToSubjects = new HashMap();
    protected static final String NAMESPACE = "world";
    private static final Gson GSON = new Gson();
    private static final Logging LOGGER = Logging.getLogger();
    protected static final EventCodec INSTANCE = new EventCodec();

    private EventCodec() {
        register(WorldSharedEvent.class);
        register(WorldConfigEvent.class);
        register(WorldStopEvent.class);
        register(TunnelRequestEvent.class);
        register(WorldReadyEvent.class);
        register(JoinWorldEvent.Request.class);
        register(JoinWorldEvent.Response.class);
        register(LocalServerInviteEvent.class);
        register(WorldInviteEvent.class);
    }

    private void register(Class<? extends NetworkEvent> event) {
        NetworkEvent.Subject subjectAnnotation = (NetworkEvent.Subject) event.getAnnotation(NetworkEvent.Subject.class);
        if (subjectAnnotation == null) {
            LOGGER.warn("Could not register network event class '{}': Missing @NetworkEvent.Subject annotation", event.getName());
            return;
        }
        String subject = "world:" + subjectAnnotation.value();
        if (this.subjectToEvents.containsKey(subject)) {
            LOGGER.warn("Could not register network event class '{}': Subject '{}' is already registered to '{}'", event.getName(), subject, this.subjectToEvents.get(subject).getName());
        } else {
            this.subjectToEvents.put(subject, event);
            this.eventToSubjects.put(event, subject);
        }
    }

    @Nullable
    public final String subjectFor(NetworkEvent event) {
        return this.eventToSubjects.get(event.getClass());
    }

    public byte[] encode(NetworkEvent event) {
        return GSON.toJson(event).getBytes(StandardCharsets.UTF_8);
    }

    @Nullable
    public NetworkEvent decode(String subject, byte[] payload) {
        Class<? extends NetworkEvent> eventClass = this.subjectToEvents.get(subject);
        if (eventClass == null) {
            return null;
        }
        try {
            return (NetworkEvent) GSON.fromJson(new String(payload, StandardCharsets.UTF_8), eventClass);
        } catch (JsonSyntaxException e) {
            LOGGER.warn("Could not decode event with subject '{}': {}", subject, e);
            return null;
        }
    }
}
