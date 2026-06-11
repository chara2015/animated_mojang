package net.labymod.v1_20_6.mixins.network.protocol.common.custom;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.core.main.LabyMod;
import net.labymod.v1_20_6.client.multiplayer.server.LabyModCustomPacketPayload;
import net.labymod.v1_20_6.client.multiplayer.server.payload.CustomPayloadHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wm;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/network/protocol/common/custom/MixinCustomPacketPayload.class */
@Mixin(targets = {"net/minecraft/network/protocol/common/custom/CustomPacketPayload$1"})
public abstract class MixinCustomPacketPayload<B extends wm> implements zn<B, aax> {
    @Inject(method = {"writeCap(Lnet/minecraft/network/FriendlyByteBuf;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$Type;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;)V"}, at = {@At("HEAD")}, cancellable = true)
    private <T extends aax> void labyMod$encodeCustomPayload(B buffer, b<T> type, T payload, CallbackInfo ci) {
        if (!(payload instanceof LabyModCustomPacketPayload)) {
            return;
        }
        LabyModCustomPacketPayload labyModPayload = (LabyModCustomPacketPayload) payload;
        alf channelId = type.a();
        zn<wm, LabyModCustomPacketPayload> codec = CustomPayloadHolder.findOrRegisterCodec(channelId);
        buffer.a(channelId);
        if (codec == null) {
            if (IdeUtil.RUNNING_IN_IDE) {
                IdeUtil.LOGGER.error("Custom payload (\"{}\") could not be sent because its channel was not registered", channelId);
            }
            zn<wm, aay> discardedCodec = aay.a(channelId, 1048576);
            discardedCodec.encode(buffer, new aay(channelId));
        } else {
            codec.encode(buffer, labyModPayload);
        }
        ci.cancel();
    }

    @Inject(method = {"decode(Lnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$decodeCustomPayload(B buffer, CallbackInfoReturnable<aax> cir) {
        wm copiedBuffer = new wm(buffer.copy());
        ResourceLocation resourceLocationQ = copiedBuffer.q();
        LabyMod.references().clientNetworkPacketListener().onPayloadReceive(resourceLocationQ, copiedBuffer.copy());
        zn<wm, LabyModCustomPacketPayload> codec = CustomPayloadHolder.findOrRegisterCodec((alf) resourceLocationQ);
        if (codec != null) {
            cir.setReturnValue((aax) codec.decode(copiedBuffer));
            buffer.x();
        }
    }
}
