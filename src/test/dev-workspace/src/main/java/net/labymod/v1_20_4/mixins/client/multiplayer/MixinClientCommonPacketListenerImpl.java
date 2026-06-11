package net.labymod.v1_20_4.mixins.client.multiplayer;

import io.netty.buffer.Unpooled;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.multiplayer.ClientNetworkPacketListener;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/multiplayer/MixinClientCommonPacketListenerImpl.class */
@Mixin(value = {fnp.class}, priority = 999)
public class MixinClientCommonPacketListenerImpl {

    @Shadow
    @Final
    protected evi a;
    private ClientNetworkPacketListener labyMod$networkPacketListener;

    private ClientNetworkPacketListener labyMod$networkPacketListener() {
        if (this.labyMod$networkPacketListener == null) {
            this.labyMod$networkPacketListener = LabyMod.references().clientNetworkPacketListener();
        }
        return this.labyMod$networkPacketListener;
    }

    private ResourceLocation labyMod$convertResourceLocation(ahg location) {
        return (ResourceLocation) location;
    }

    @Insert(method = {"handleCustomPayload(Lnet/minecraft/network/protocol/common/ClientboundCustomPayloadPacket;)V"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$payloadReceive(xj packet, InsertInfo insertInfo) {
        ya payload = packet.a();
        if (!(payload instanceof yb)) {
            xh.a(packet, (xi) this, this.a);
            ui byteBuf = new ui(Unpooled.buffer());
            payload.a(byteBuf);
            labyMod$networkPacketListener().onPayloadReceive(labyMod$convertResourceLocation(payload.a()), byteBuf.copy());
            labyMod$networkPacketListener().onCustomPayloadReceive(labyMod$convertResourceLocation(payload.a()), byteBuf, insertInfo);
        }
    }
}
