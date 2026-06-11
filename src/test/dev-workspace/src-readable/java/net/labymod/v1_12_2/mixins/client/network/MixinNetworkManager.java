package net.labymod.v1_12_2.mixins.client.network;

import io.netty.channel.Channel;
import net.labymod.core.client.multiplayer.NettyConnection;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/network/MixinNetworkManager.class */
@Mixin({gw.class})
@Implements({@Interface(iface = NettyConnection.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinNetworkManager implements NettyConnection {

    @Shadow
    private Channel k;

    @Intrinsic
    public Channel labyMod$getChannel() {
        return this.k;
    }
}
