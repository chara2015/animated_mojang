package net.labymod.v1_21_8.mixins.network.protocol.common.custom;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.core.main.LabyMod;
import net.labymod.v1_21_8.client.multiplayer.server.LabyModCustomPacketPayload;
import net.labymod.v1_21_8.client.multiplayer.server.payload.CustomPayloadHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wg;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/network/protocol/common/custom/MixinCustomPacketPayload.class */
@Mixin(targets = {"net/minecraft/network/protocol/common/custom/CustomPacketPayload$1"})
public abstract class MixinCustomPacketPayload<B extends wg> implements zm<B, abe> {
    @Inject(method = {"writeCap(Lnet/minecraft/network/FriendlyByteBuf;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$Type;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;)V"}, at = {@At("HEAD")}, cancellable = true)
    private <T extends abe> void labyMod$encodeCustomPayload(B buffer, b<T> type, T payload, CallbackInfo ci) {
        if (!(payload instanceof LabyModCustomPacketPayload)) {
            return;
        }
        LabyModCustomPacketPayload labyModPayload = (LabyModCustomPacketPayload) payload;
        ame channelId = type.a();
        zm<wg, LabyModCustomPacketPayload> codec = CustomPayloadHolder.findOrRegisterCodec(channelId);
        buffer.a(channelId);
        if (codec == null) {
            if (IdeUtil.RUNNING_IN_IDE) {
                IdeUtil.LOGGER.error("Custom payload (\"{}\") could not be sent because its channel was not registered", channelId);
            }
            zm<wg, abf> discardedCodec = abf.a(channelId, 1048576);
            discardedCodec.encode(buffer, new abf(channelId));
        } else {
            codec.encode(buffer, labyModPayload);
        }
        ci.cancel();
    }

    @Inject(method = {"decode(Lnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$decodeCustomPayload(B buffer, CallbackInfoReturnable<abe> cir) {
        wg copiedBuffer = new wg(buffer.copy());
        ResourceLocation resourceLocationQ = copiedBuffer.q();
        LabyMod.references().clientNetworkPacketListener().onPayloadReceive(resourceLocationQ, copiedBuffer.copy());
        zm<wg, LabyModCustomPacketPayload> codec = CustomPayloadHolder.findOrRegisterCodec((ame) resourceLocationQ);
        if (codec != null) {
            cir.setReturnValue((abe) codec.decode(copiedBuffer));
            buffer.y();
        }
    }
}
