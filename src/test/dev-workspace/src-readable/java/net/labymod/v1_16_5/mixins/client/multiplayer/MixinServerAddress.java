package net.labymod.v1_16_5.mixins.client.multiplayer;

import com.mojang.datafixers.util.Pair;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.core.client.network.server.DefaultAbstractServerAddressResolver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/multiplayer/MixinServerAddress.class */
@Mixin({dwy.class})
public abstract class MixinServerAddress {
    @Overwrite
    private static Pair<String, Integer> b(String address) {
        ServerAddress serverAddress = DefaultAbstractServerAddressResolver.getServerAddress(address);
        return Pair.of(serverAddress.getHost(), Integer.valueOf(serverAddress.getPort()));
    }
}
