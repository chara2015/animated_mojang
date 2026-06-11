package net.labymod.api.event.client.network.server;

import java.nio.charset.StandardCharsets;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.debug.DebugRegistry;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/network/server/NetworkPayloadEvent.class */
public class NetworkPayloadEvent extends DefaultCancellable implements Event {
    private static final Logging LOGGER = Logging.getLogger();
    private final Side side;
    private ResourceLocation identifier;
    private byte[] payload;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/network/server/NetworkPayloadEvent$Side.class */
    public enum Side {
        RECEIVE,
        SEND
    }

    private NetworkPayloadEvent(Side side, ResourceLocation identifier, byte[] payload) {
        this.side = side;
        this.identifier = identifier;
        this.payload = payload;
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public static NetworkPayloadEvent createReceive(ResourceLocation identifier, byte[] payload) {
        if (DebugRegistry.PAYLOAD.isEnabled()) {
            LOGGER.info("[CustomPayload] [IN] {}: {}", identifier, new String(payload, StandardCharsets.UTF_8));
        }
        return new NetworkPayloadEvent(Side.RECEIVE, identifier, payload);
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public static NetworkPayloadEvent createSend(ResourceLocation identifier, byte[] payload) {
        if (DebugRegistry.PAYLOAD.isEnabled()) {
            LOGGER.info("[CustomPayload] [OUT] {}: {}", identifier, new String(payload, StandardCharsets.UTF_8));
        }
        return new NetworkPayloadEvent(Side.SEND, identifier, payload);
    }

    public Side side() {
        return this.side;
    }

    public ResourceLocation identifier() {
        return this.identifier;
    }

    public void setIdentifier(ResourceLocation identifier) {
        this.identifier = identifier;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }
}
