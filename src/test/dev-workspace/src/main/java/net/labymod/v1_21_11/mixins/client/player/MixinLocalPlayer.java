package net.labymod.v1_21_11.mixins.client.player;

import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/player/MixinLocalPlayer.class */
@Mixin({hnh.class})
public abstract class MixinLocalPlayer extends MixinAbstractLocalPlayer implements ClientPlayer {
    private boolean labyMod$cancelHandSwingPacket = false;

    @Shadow
    public abstract void a(cdb cdbVar);

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public boolean isAbilitiesFlying() {
        return labyMod$getClientPlayer().gL().b;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public float getAbilitiesWalkingSpeed() {
        return labyMod$getClientPlayer().gL().b();
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public boolean isHandActive() {
        return labyMod$getClientPlayer().fZ();
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public boolean isUsingBow() {
        return labyMod$getClientPlayer().gb().h() == dlx.pW;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public void swingArm(LivingEntity.Hand hand) {
        swingArm(hand, false);
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public void swingArm(LivingEntity.Hand hand, boolean cancelPacket) {
        cdb cdbVar;
        if (hand == LivingEntity.Hand.MAIN_HAND) {
            cdbVar = cdb.a;
        } else {
            cdbVar = cdb.b;
        }
        cdb interactionHand = cdbVar;
        this.labyMod$cancelHandSwingPacket = cancelPacket;
        a(interactionHand);
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public Inventory inventory() {
        return labyMod$getClientPlayer().gK();
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public void setDistanceWalked(float distance) {
        ClientAvatarStateAccessor.cast(labyMod$getClientPlayer().b()).labyMod$setWalkDist(distance);
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public float getForwardMovingSpeed() {
        return labyMod$getClientPlayer().c.b().k;
    }

    @Insert(method = {"swing"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$cancelHandSwingPacket(cdb hand, InsertInfo ci) {
        if (this.labyMod$cancelHandSwingPacket) {
            ci.cancel();
            this.labyMod$cancelHandSwingPacket = false;
        }
    }

    private hnh labyMod$getClientPlayer() {
        return (hnh) this;
    }
}
