package net.labymod.v26_1_1.mixins.network.protocol.common.custom;

import net.labymod.api.Laby;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import net.labymod.v26_1_1.client.multiplayer.server.LabyModCustomPacketPayload;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/network/protocol/common/custom/MixinClientPacketListenerCustomPayload.class */
@Mixin({ClientPacketListener.class})
public class MixinClientPacketListenerCustomPayload {
    @Inject(method = {"handleUnknownCustomPayload"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$onHandleCustomPayload(CustomPacketPayload payload, CallbackInfo ci) {
        if (payload instanceof LabyModCustomPacketPayload) {
            LabyModCustomPacketPayload labyModCustomPacketPayload = (LabyModCustomPacketPayload) payload;
            Identifier id = payload.type().id();
            boolean handled = Laby.references().serverController().handleCustomPayload(PayloadChannelIdentifier.create(id.getNamespace(), id.getPath()), labyModCustomPacketPayload.getBuffer());
            if (handled) {
                ci.cancel();
            }
        }
    }
}
