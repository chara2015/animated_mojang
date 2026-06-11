package net.labymod.v1_21_10.mixins.network.protocol.common.custom;

import net.labymod.api.Laby;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.v1_21_10.client.multiplayer.server.LabyModCustomPacketPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/network/protocol/common/custom/MixinClientPacketListenerCustomPayload.class */
@Mixin({gzo.class})
public class MixinClientPacketListenerCustomPayload {
    @Inject(method = {"handleUnknownCustomPayload"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$onHandleCustomPayload(abt payload, CallbackInfo ci) {
        if (payload instanceof LabyModCustomPacketPayload) {
            LabyModCustomPacketPayload labyModCustomPacketPayload = (LabyModCustomPacketPayload) payload;
            amj id = payload.a().a();
            boolean handled = Laby.references().serverController().handleCustomPayload(PayloadChannelIdentifier.create(id.b(), id.a()), labyModCustomPacketPayload.getBuffer());
            if (handled) {
                ci.cancel();
            }
        }
    }
}
