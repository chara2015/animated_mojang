package net.labymod.v1_21_10.mixins.client.multiplayer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/multiplayer/MixinClientPacketListener.class */
@Mixin({gzo.class})
public abstract class MixinClientPacketListener {

    @Shadow
    private gzn y;
    private ClientNetworkPacketListener labyMod$networkPacketListener;

    @Inject(method = {"handleSetPlayerTeamPacket"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/network/PacketProcessor;)V", shift = At.Shift.AFTER)}, cancellable = true)
    private void labyMod$preventNPEViaVersion(agp packet, CallbackInfo ci) {
        if (this.y == null) {
            ci.cancel();
        }
    }

    @Insert(method = {"handleLogin(Lnet/minecraft/network/protocol/game/ClientboundLoginPacket;)V"}, at = @At("TAIL"))
    private void labyMod$login(aen packet, InsertInfo ci) {
        labyMod$networkPacketListener().onLoginOrServerSwitch();
    }

    @WrapOperation(method = {"handleLogin"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/toasts/ToastManager;addToast(Lnet/minecraft/client/gui/components/toasts/Toast;)V")})
    private void labyMod$hideUnsecureServerToast(ghm toastManager, ghl toast, Operation<Void> original) {
        if (Laby.labyAPI().config().notifications().hideChatTrustToast().get().booleanValue()) {
            return;
        }
        original.call(new Object[]{toastManager, toast});
    }

    @Redirect(method = {"handleRotateMob"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;lerpHeadTo(FI)V"))
    private void labyMod$oldHeadRotation(cdv entity, float yaw, int pitch) {
        labyMod$networkPacketListener().onEntityRotate((Entity) entity, yaw, pitch);
    }

    private ClientNetworkPacketListener labyMod$networkPacketListener() {
        if (this.labyMod$networkPacketListener == null) {
            this.labyMod$networkPacketListener = LabyMod.references().clientNetworkPacketListener();
        }
        return this.labyMod$networkPacketListener;
    }
}
