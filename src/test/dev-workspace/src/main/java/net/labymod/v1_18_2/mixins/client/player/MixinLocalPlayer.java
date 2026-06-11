package net.labymod.v1_18_2.mixins.client.player;

import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/player/MixinLocalPlayer.class */
@Mixin({epw.class})
public abstract class MixinLocalPlayer extends MixinAbstractLocalPlayer implements ClientPlayer {
    private boolean labyMod$cancelHandSwingPacket = false;

    @Shadow
    @Final
    public emt cx;

    @Shadow
    public abstract void a(awg awgVar);

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public boolean isAbilitiesFlying() {
        return labyMod$getClientPlayer().fs().b;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public float getAbilitiesWalkingSpeed() {
        return labyMod$getClientPlayer().fs().b();
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public boolean isHandActive() {
        return labyMod$getClientPlayer().eM();
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public boolean isUsingBow() {
        return labyMod$getClientPlayer().eO().c() == buy.mg;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public void swingArm(LivingEntity.Hand hand) {
        swingArm(hand, false);
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public void swingArm(LivingEntity.Hand hand, boolean cancelPacket) {
        awg awgVar;
        if (hand == LivingEntity.Hand.MAIN_HAND) {
            awgVar = awg.a;
        } else {
            awgVar = awg.b;
        }
        awg interactionHand = awgVar;
        this.labyMod$cancelHandSwingPacket = cancelPacket;
        a(interactionHand);
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public Inventory inventory() {
        return labyMod$getClientPlayer().fr();
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public void setDistanceWalked(float distance) {
        labyMod$getClientPlayer().I = distance;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public float getForwardMovingSpeed() {
        return labyMod$getClientPlayer().cy.b;
    }

    @Insert(method = {"swing"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$cancelHandSwingPacket(awg hand, InsertInfo ci) {
        if (this.labyMod$cancelHandSwingPacket) {
            ci.cancel();
            this.labyMod$cancelHandSwingPacket = false;
        }
    }

    private epw labyMod$getClientPlayer() {
        return (epw) this;
    }
}
