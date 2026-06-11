package net.labymod.v1_21.mixins.network.protocol.common.custom;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.core.main.LabyMod;
import net.labymod.v1_21.client.multiplayer.server.LabyModCustomPacketPayload;
import net.labymod.v1_21.client.multiplayer.server.payload.CustomPayloadHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vw;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/network/protocol/common/custom/MixinCustomPacketPayload.class */
@Mixin(targets = {"net/minecraft/network/protocol/common/custom/CustomPacketPayload$1"})
public abstract class MixinCustomPacketPayload<B extends vw> implements yx<B, aaj> {
    @Inject(method = {"writeCap(Lnet/minecraft/network/FriendlyByteBuf;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$Type;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;)V"}, at = {@At("HEAD")}, cancellable = true)
    private <T extends aaj> void labyMod$encodeCustomPayload(B buffer, b<T> type, T payload, CallbackInfo ci) {
        if (!(payload instanceof LabyModCustomPacketPayload)) {
            return;
        }
        LabyModCustomPacketPayload labyModPayload = (LabyModCustomPacketPayload) payload;
        akr channelId = type.a();
        yx<vw, LabyModCustomPacketPayload> codec = CustomPayloadHolder.findOrRegisterCodec(channelId);
        buffer.a(channelId);
        if (codec == null) {
            if (IdeUtil.RUNNING_IN_IDE) {
                IdeUtil.LOGGER.error("Custom payload (\"{}\") could not be sent because its channel was not registered", channelId);
            }
            yx<vw, aak> discardedCodec = aak.a(channelId, 1048576);
            discardedCodec.encode(buffer, new aak(channelId));
        } else {
            codec.encode(buffer, labyModPayload);
        }
        ci.cancel();
    }

    @Inject(method = {"decode(Lnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$decodeCustomPayload(B buffer, CallbackInfoReturnable<aaj> cir) {
        vw copiedBuffer = new vw(buffer.copy());
        ResourceLocation resourceLocationQ = copiedBuffer.q();
        LabyMod.references().clientNetworkPacketListener().onPayloadReceive(resourceLocationQ, copiedBuffer.copy());
        yx<vw, LabyModCustomPacketPayload> codec = CustomPayloadHolder.findOrRegisterCodec((akr) resourceLocationQ);
        if (codec != null) {
            cir.setReturnValue((aaj) codec.decode(copiedBuffer));
            buffer.x();
        }
    }
}
