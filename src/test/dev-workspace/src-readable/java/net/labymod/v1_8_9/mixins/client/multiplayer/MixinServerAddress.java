package net.labymod.v1_8_9.mixins.client.multiplayer;

import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.core.client.network.server.DefaultAbstractServerAddressResolver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/multiplayer/MixinServerAddress.class */
@Mixin({bdd.class})
public class MixinServerAddress {
    @Overwrite
    private static String[] b(String address) {
        ServerAddress serverAddress = DefaultAbstractServerAddressResolver.getServerAddress(address);
        return new String[]{serverAddress.getHost(), Integer.toString(serverAddress.getPort())};
    }
}
