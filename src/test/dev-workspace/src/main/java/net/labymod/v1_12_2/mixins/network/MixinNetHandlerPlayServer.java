package net.labymod.v1_12_2.mixins.network;

import net.labymod.api.Laby;
import net.labymod.api.event.Phase;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.server.event.IntegratedServerPlayerDisconnectEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/network/MixinNetHandlerPlayServer.class */
@Mixin({pa.class})
public class MixinNetHandlerPlayServer {

    @Shadow
    public oq b;

    @Insert(method = {"onDisconnect"}, at = @At("HEAD"))
    private void labyMod$fireDisconnectEventPre(hh reason, InsertInfo ci) {
        GameProfile profile = this.b.da();
        Laby.fireEvent(new IntegratedServerPlayerDisconnectEvent(Phase.PRE, profile));
    }

    @Insert(method = {"onDisconnect"}, at = @At("TAIL"))
    private void labyMod$fireDisconnectEventPost(hh reason, InsertInfo ci) {
        GameProfile profile = this.b.da();
        Laby.fireEvent(new IntegratedServerPlayerDisconnectEvent(Phase.POST, profile));
    }
}
