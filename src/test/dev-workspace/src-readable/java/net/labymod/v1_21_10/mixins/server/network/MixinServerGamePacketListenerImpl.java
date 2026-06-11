package net.labymod.v1_21_10.mixins.server.network;

import net.labymod.api.Laby;
import net.labymod.api.event.Phase;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.server.event.IntegratedServerPlayerDisconnectEvent;
import net.labymod.api.util.CastUtil;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/server/network/MixinServerGamePacketListenerImpl.class */
@Mixin({axz.class})
public class MixinServerGamePacketListenerImpl {

    @Shadow
    public awy f;

    @Insert(method = {"onDisconnect"}, at = @At("HEAD"))
    private void labyMod$fireDisconnectEventPre(xx reason, InsertInfo ci) {
        GameProfile profile = (GameProfile) CastUtil.cast(this.f.gz());
        Laby.fireEvent(new IntegratedServerPlayerDisconnectEvent(Phase.PRE, profile));
    }

    @Insert(method = {"onDisconnect"}, at = @At("TAIL"))
    private void labyMod$fireDisconnectEventPost(xx reason, InsertInfo ci) {
        GameProfile profile = (GameProfile) CastUtil.cast(this.f.gz());
        Laby.fireEvent(new IntegratedServerPlayerDisconnectEvent(Phase.POST, profile));
    }
}
