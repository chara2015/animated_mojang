package net.labymod.v1_21_10.mixins.client.player;

import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.v1_21_10.client.player.ClientAvatarStateAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/player/MixinLocalPlayer.class */
@Mixin({hep.class})
public abstract class MixinLocalPlayer extends MixinAbstractLocalPlayer implements ClientPlayer {
    private boolean labyMod$cancelHandSwingPacket = false;

    @Shadow
    public abstract void a(cbp cbpVar);

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public boolean isAbilitiesFlying() {
        return labyMod$getClientPlayer().gC().b;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public float getAbilitiesWalkingSpeed() {
        return labyMod$getClientPlayer().gC().b();
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public boolean isHandActive() {
        return labyMod$getClientPlayer().fO();
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public boolean isUsingBow() {
        return labyMod$getClientPlayer().fQ().h() == dht.pW;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public void swingArm(LivingEntity.Hand hand) {
        swingArm(hand, false);
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public void swingArm(LivingEntity.Hand hand, boolean cancelPacket) {
        cbp cbpVar;
        if (hand == LivingEntity.Hand.MAIN_HAND) {
            cbpVar = cbp.a;
        } else {
            cbpVar = cbp.b;
        }
        cbp interactionHand = cbpVar;
        this.labyMod$cancelHandSwingPacket = cancelPacket;
        a(interactionHand);
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public Inventory inventory() {
        return labyMod$getClientPlayer().gB();
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public void setDistanceWalked(float distance) {
        ClientAvatarStateAccessor.cast(labyMod$getClientPlayer().b()).labyMod$setWalkDist(distance);
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public float getForwardMovingSpeed() {
        return labyMod$getClientPlayer().d.b().k;
    }

    @Insert(method = {"swing"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$cancelHandSwingPacket(cbp hand, InsertInfo ci) {
        if (this.labyMod$cancelHandSwingPacket) {
            ci.cancel();
            this.labyMod$cancelHandSwingPacket = false;
        }
    }

    private hep labyMod$getClientPlayer() {
        return (hep) this;
    }
}
