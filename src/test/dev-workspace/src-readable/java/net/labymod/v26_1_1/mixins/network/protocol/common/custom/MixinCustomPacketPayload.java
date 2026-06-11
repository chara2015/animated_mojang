package net.labymod.v26_1_1.mixins.network.protocol.common.custom;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.core.main.LabyMod;
import net.labymod.v26_1_1.client.multiplayer.server.LabyModCustomPacketPayload;
import net.labymod.v26_1_1.client.multiplayer.server.payload.CustomPayloadHolder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.DiscardedPayload;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/network/protocol/common/custom/MixinCustomPacketPayload.class */
@Mixin(targets = {"net/minecraft/network/protocol/common/custom/CustomPacketPayload$1"})
public abstract class MixinCustomPacketPayload<B extends FriendlyByteBuf> implements StreamCodec<B, CustomPacketPayload> {
    @Inject(method = {"writeCap(Lnet/minecraft/network/FriendlyByteBuf;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload$Type;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;)V"}, at = {@At("HEAD")}, cancellable = true)
    private <T extends CustomPacketPayload> void labyMod$encodeCustomPayload(B buffer, CustomPacketPayload.Type<T> type, T payload, CallbackInfo ci) {
        if (!(payload instanceof LabyModCustomPacketPayload)) {
            return;
        }
        LabyModCustomPacketPayload labyModPayload = (LabyModCustomPacketPayload) payload;
        Identifier channelId = type.id();
        StreamCodec<FriendlyByteBuf, LabyModCustomPacketPayload> codec = CustomPayloadHolder.findOrRegisterCodec(channelId);
        buffer.writeIdentifier(channelId);
        if (codec == null) {
            if (IdeUtil.RUNNING_IN_IDE) {
                IdeUtil.LOGGER.error("Custom payload (\"{}\") could not be sent because its channel was not registered", channelId);
            }
            StreamCodec<FriendlyByteBuf, DiscardedPayload> discardedCodec = DiscardedPayload.codec(channelId, 1048576);
            discardedCodec.encode(buffer, new DiscardedPayload(channelId));
        } else {
            codec.encode(buffer, labyModPayload);
        }
        ci.cancel();
    }

    @Inject(method = {"decode(Lnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$decodeCustomPayload(B buffer, CallbackInfoReturnable<CustomPacketPayload> cir) {
        FriendlyByteBuf copiedBuffer = new FriendlyByteBuf(buffer.copy());
        ResourceLocation identifier = copiedBuffer.readIdentifier();
        LabyMod.references().clientNetworkPacketListener().onPayloadReceive(identifier, copiedBuffer.copy());
        StreamCodec<FriendlyByteBuf, LabyModCustomPacketPayload> codec = CustomPayloadHolder.findOrRegisterCodec((Identifier) identifier);
        if (codec != null) {
            cir.setReturnValue((CustomPacketPayload) codec.decode(copiedBuffer));
            buffer.clear();
        }
    }
}
