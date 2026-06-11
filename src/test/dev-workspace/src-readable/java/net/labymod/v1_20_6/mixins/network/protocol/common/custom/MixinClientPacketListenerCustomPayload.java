package net.labymod.v1_20_6.mixins.network.protocol.common.custom;

import net.labymod.api.Laby;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.v1_20_6.client.multiplayer.server.LabyModCustomPacketPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/network/protocol/common/custom/MixinClientPacketListenerCustomPayload.class */
@Mixin({fxy.class})
public class MixinClientPacketListenerCustomPayload {
    @Inject(method = {"handleUnknownCustomPayload"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$onHandleCustomPayload(aax payload, CallbackInfo ci) {
        if (payload instanceof LabyModCustomPacketPayload) {
            LabyModCustomPacketPayload labyModCustomPacketPayload = (LabyModCustomPacketPayload) payload;
            alf id = payload.a().a();
            boolean handled = Laby.references().serverController().handleCustomPayload(PayloadChannelIdentifier.create(id.b(), id.a()), labyModCustomPacketPayload.getBuffer());
            if (handled) {
                ci.cancel();
            }
        }
    }
}
