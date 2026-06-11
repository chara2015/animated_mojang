package net.labymod.v1_20_1.mixins.server.network;

import net.labymod.api.Laby;
import net.labymod.api.event.Phase;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.server.event.IntegratedServerPlayerDisconnectEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/server/network/MixinServerGamePacketListenerImpl.class */
@Mixin({aiy.class})
public class MixinServerGamePacketListenerImpl {

    @Shadow
    public aig b;

    @Insert(method = {"onDisconnect"}, at = @At("HEAD"))
    private void labyMod$fireDisconnectEventPre(sw reason, InsertInfo ci) {
        GameProfile profile = this.b.fM();
        Laby.fireEvent(new IntegratedServerPlayerDisconnectEvent(Phase.PRE, profile));
    }

    @Insert(method = {"onDisconnect"}, at = @At("TAIL"))
    private void labyMod$fireDisconnectEventPost(sw reason, InsertInfo ci) {
        GameProfile profile = this.b.fM();
        Laby.fireEvent(new IntegratedServerPlayerDisconnectEvent(Phase.POST, profile));
    }
}
