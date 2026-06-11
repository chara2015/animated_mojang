package net.labymod.v1_12_2.mixins.client.network.play.client;

import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/network/play/client/MixinCPacketResourcePackStatus.class */
@Mixin({lt.class})
public class MixinCPacketResourcePackStatus {
    @Inject(method = {"<init>(Lnet/minecraft/network/play/client/CPacketResourcePackStatus$Action;)V"}, at = {@At("TAIL")})
    private void labyMod$disableCustomFont(a action, CallbackInfo ci) {
        if (action == a.a) {
            LabyMod.references().clientNetworkPacketListener().onLoadServerResourcePack();
        }
    }
}
