package net.labymod.api.client.network.server.payload;

import java.util.Collection;
import java.util.function.Consumer;
import net.labymod.api.Namespaces;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/payload/PayloadRegistry.class */
@Referenceable
public interface PayloadRegistry {
    public static final ResourceLocation MINECRAFT_REGISTER = ResourceLocation.create(Namespaces.MINECRAFT, "register");
    public static final ResourceLocation MINECRAFT_UNREGISTER = ResourceLocation.create(Namespaces.MINECRAFT, "unregister");

    void registerPayloadChannel(ResourceLocation resourceLocation);

    void unregisterPayloadChannel(ResourceLocation resourceLocation);

    boolean canProcessPayload(ResourceLocation resourceLocation);

    Collection<ResourceLocation> getRegisteredChannels();

    default void registerPayloadChannel(PayloadChannelIdentifier id) {
        registerPayloadChannel(ResourceLocation.create(id.getNamespace(), id.getPath()));
    }

    default void forEachRegisteredChannel(Consumer<ResourceLocation> consumer) {
        for (ResourceLocation registeredChannel : getRegisteredChannels()) {
            if (!isReservedChannel(registeredChannel)) {
                consumer.accept(registeredChannel);
            }
        }
    }

    default boolean isReservedChannel(ResourceLocation id) {
        return MINECRAFT_REGISTER.equals(id) || MINECRAFT_UNREGISTER.equals(id);
    }
}
