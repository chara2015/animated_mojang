package net.labymod.v1_8_9.mixins.client.player;

import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/player/MixinEntityPlayerSP.class */
@Mixin({bew.class})
public abstract class MixinEntityPlayerSP extends MixinAbstractLocalPlayer implements ClientPlayer {
    private boolean labyMod$cancelSwingPacket;

    @Shadow
    public abstract void bw();

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public boolean isAbilitiesFlying() {
        return labyMod$getClientPlayer().bA.b;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public float getAbilitiesWalkingSpeed() {
        return labyMod$getClientPlayer().bA.b();
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public boolean isHandActive() {
        return labyMod$getClientPlayer().bS();
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public boolean isUsingBow() {
        return labyMod$getClientPlayer().bQ().b() == zy.f;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public void swingArm(LivingEntity.Hand hand) {
        swingArm(hand, false);
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public void swingArm(LivingEntity.Hand hand, boolean cancelPacket) {
        this.labyMod$cancelSwingPacket = cancelPacket;
        bw();
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public void setDistanceWalked(float distance) {
        labyMod$getClientPlayer().M = distance;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public float getForwardMovingSpeed() {
        return labyMod$getClientPlayer().b.b;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public float getStrafeMovingSpeed() {
        return labyMod$getClientPlayer().b.a;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public Inventory inventory() {
        return ((bet) this).bi;
    }

    @Insert(method = {"swingItem()V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/NetHandlerPlayClient;addToSendQueue(Lnet/minecraft/network/Packet;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$cancelSwingPacket(InsertInfo ci) {
        if (this.labyMod$cancelSwingPacket) {
            ci.cancel();
            this.labyMod$cancelSwingPacket = false;
        }
    }

    private bew labyMod$getClientPlayer() {
        return ave.A().h;
    }
}
