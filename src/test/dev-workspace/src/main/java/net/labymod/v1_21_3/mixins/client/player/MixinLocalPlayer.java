package net.labymod.v1_21_3.mixins.client.player;

import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/player/MixinLocalPlayer.class */
@Mixin({gkh.class})
public abstract class MixinLocalPlayer extends MixinAbstractLocalPlayer implements ClientPlayer {
    private boolean labyMod$cancelHandSwingPacket = false;

    @Shadow
    public abstract void a(bti btiVar);

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public boolean isAbilitiesFlying() {
        return labyMod$getClientPlayer().gj().b;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public float getAbilitiesWalkingSpeed() {
        return labyMod$getClientPlayer().gj().b();
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public boolean isHandActive() {
        return labyMod$getClientPlayer().fz();
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public boolean isUsingBow() {
        return labyMod$getClientPlayer().fB().h() == cxt.oR;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public void swingArm(LivingEntity.Hand hand) {
        swingArm(hand, false);
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public void swingArm(LivingEntity.Hand hand, boolean cancelPacket) {
        bti btiVar;
        if (hand == LivingEntity.Hand.MAIN_HAND) {
            btiVar = bti.a;
        } else {
            btiVar = bti.b;
        }
        bti interactionHand = btiVar;
        this.labyMod$cancelHandSwingPacket = cancelPacket;
        a(interactionHand);
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public Inventory inventory() {
        return labyMod$getClientPlayer().gi();
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public void setDistanceWalked(float distance) {
        labyMod$getClientPlayer().g = distance;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public float getForwardMovingSpeed() {
        return labyMod$getClientPlayer().j.c;
    }

    @Insert(method = {"swing"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$cancelHandSwingPacket(bti hand, InsertInfo ci) {
        if (this.labyMod$cancelHandSwingPacket) {
            ci.cancel();
            this.labyMod$cancelHandSwingPacket = false;
        }
    }

    private gkh labyMod$getClientPlayer() {
        return (gkh) this;
    }
}
