package net.labymod.v1_20_4.mixins.server.network;

import net.labymod.api.Laby;
import net.labymod.api.event.Phase;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.server.event.IntegratedServerPlayerDisconnectEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/server/network/MixinServerGamePacketListenerImpl.class */
@Mixin({aoc.class})
public class MixinServerGamePacketListenerImpl {

    @Shadow
    public ane e;

    @Insert(method = {"onDisconnect"}, at = @At("HEAD"))
    private void labyMod$fireDisconnectEventPre(vf reason, InsertInfo ci) {
        GameProfile profile = this.e.fR();
        Laby.fireEvent(new IntegratedServerPlayerDisconnectEvent(Phase.PRE, profile));
    }

    @Insert(method = {"onDisconnect"}, at = @At("TAIL"))
    private void labyMod$fireDisconnectEventPost(vf reason, InsertInfo ci) {
        GameProfile profile = this.e.fR();
        Laby.fireEvent(new IntegratedServerPlayerDisconnectEvent(Phase.POST, profile));
    }
}
