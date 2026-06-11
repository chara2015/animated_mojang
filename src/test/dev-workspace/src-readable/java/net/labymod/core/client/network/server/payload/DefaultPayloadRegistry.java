package net.labymod.core.client.network.server.payload;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Singleton;
import net.labymod.api.client.network.server.payload.PayloadRegistry;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.EventBus;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/network/server/payload/DefaultPayloadRegistry.class */
@Singleton
@Implements(PayloadRegistry.class)
public class DefaultPayloadRegistry implements PayloadRegistry {
    private final Set<ResourceLocation> payloadChannels = new HashSet();

    public void initialize(EventBus eventBus) {
        eventBus.registerListener(this);
    }

    @Override // net.labymod.api.client.network.server.payload.PayloadRegistry
    public void registerPayloadChannel(ResourceLocation id) {
        if (this.payloadChannels.contains(id)) {
            return;
        }
        this.payloadChannels.add(id);
    }

    @Override // net.labymod.api.client.network.server.payload.PayloadRegistry
    public void unregisterPayloadChannel(ResourceLocation id) {
        this.payloadChannels.remove(id);
    }

    @Override // net.labymod.api.client.network.server.payload.PayloadRegistry
    public boolean canProcessPayload(ResourceLocation id) {
        return this.payloadChannels.contains(id);
    }

    @Override // net.labymod.api.client.network.server.payload.PayloadRegistry
    public Collection<ResourceLocation> getRegisteredChannels() {
        return Collections.unmodifiableCollection(this.payloadChannels);
    }
}
