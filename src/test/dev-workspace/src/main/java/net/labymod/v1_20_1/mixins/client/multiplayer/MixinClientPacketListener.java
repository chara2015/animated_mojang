package net.labymod.v1_20_1.mixins.client.multiplayer;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.multiplayer.ClientNetworkPacketListener;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/multiplayer/MixinClientPacketListener.class */
@Mixin({fex.class})
public abstract class MixinClientPacketListener {

    @Shadow
    private few n;
    private ClientNetworkPacketListener labyMod$networkPacketListener;

    @Inject(method = {"handleSetPlayerTeamPacket"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getScoreboard()Lnet/minecraft/world/scores/Scoreboard;", shift = At.Shift.BEFORE)}, cancellable = true)
    private void labyMod$preventNPEViaVersion(ye packet, CallbackInfo ci) {
        if (this.n == null) {
            ci.cancel();
        }
    }

    @Insert(method = {"handleLogin(Lnet/minecraft/network/protocol/game/ClientboundLoginPacket;)V"}, at = @At("TAIL"))
    private void login(wi packet, InsertInfo ci) {
        labyMod$networkPacketListener().onLoginOrServerSwitch();
    }

    @Redirect(method = {"handleServerData"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/toasts/ToastComponent;addToast(Lnet/minecraft/client/gui/components/toasts/Toast;)V"))
    public void labyMod$hideUnsecureServerToast(erh toastComponent, erg toast) {
        if (Laby.labyAPI().config().notifications().hideChatTrustToast().get().booleanValue()) {
            return;
        }
        toastComponent.a(toast);
    }

    @Insert(method = {"handleCustomPayload(Lnet/minecraft/network/protocol/game/ClientboundCustomPayloadPacket;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/util/thread/BlockableEventLoop;)V", shift = At.Shift.AFTER))
    private void payloadReceive(vp packet, InsertInfo ci) {
        labyMod$networkPacketListener().onPayloadReceive(labyMod$convertResourceLocation(packet.a()), packet.c().copy());
    }

    private ResourceLocation labyMod$convertResourceLocation(acq location) {
        return (ResourceLocation) location;
    }

    @Insert(method = {"handleCustomPayload(Lnet/minecraft/network/protocol/game/ClientboundCustomPayloadPacket;)V"}, at = @At(remap = false, value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void handleCustomPayloadChannel(vp packet, InsertInfo insertInfo) {
        labyMod$networkPacketListener().onCustomPayloadReceive(labyMod$convertResourceLocation(packet.a()), packet.c(), insertInfo);
    }

    @Redirect(method = {"handleRotateMob"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;lerpHeadTo(FI)V"))
    private void labyMod$oldHeadRotation(bfj entity, float yaw, int pitch) {
        labyMod$networkPacketListener().onEntityRotate((Entity) entity, yaw, pitch);
    }

    private ClientNetworkPacketListener labyMod$networkPacketListener() {
        if (this.labyMod$networkPacketListener == null) {
            this.labyMod$networkPacketListener = LabyMod.references().clientNetworkPacketListener();
        }
        return this.labyMod$networkPacketListener;
    }
}
