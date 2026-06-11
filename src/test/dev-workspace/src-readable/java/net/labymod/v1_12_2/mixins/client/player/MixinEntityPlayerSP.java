package net.labymod.v1_12_2.mixins.client.player;

import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/player/MixinEntityPlayerSP.class */
@Mixin({bud.class})
@Implements({@Interface(iface = ClientPlayer.class, prefix = "player$")})
public abstract class MixinEntityPlayerSP extends MixinAbstractLocalPlayer implements ClientPlayer {

    @Shadow
    private boolean cr;
    private boolean labyMod$cancelSwingPacket;

    @Shadow
    public abstract void a(ub ubVar);

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public boolean isAbilitiesFlying() {
        return labyMod$getClientPlayer().bO.b;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public float getAbilitiesWalkingSpeed() {
        return labyMod$getClientPlayer().bO.b();
    }

    @Intrinsic
    public boolean player$isHandActive() {
        return labyMod$getClientPlayer().cG();
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public boolean isUsingBow() {
        return labyMod$getClientPlayer().cJ().c() == air.g;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public void swingArm(LivingEntity.Hand hand) {
        swingArm(hand, false);
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public void swingArm(LivingEntity.Hand hand, boolean cancelPacket) {
        this.labyMod$cancelSwingPacket = cancelPacket;
        a(hand == LivingEntity.Hand.MAIN_HAND ? ub.a : ub.b);
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public void setDistanceWalked(float distance) {
        labyMod$getClientPlayer().J = distance;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public float getForwardMovingSpeed() {
        return labyMod$getClientPlayer().e.b;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public float getStrafeMovingSpeed() {
        return labyMod$getClientPlayer().e.a;
    }

    @Override // net.labymod.api.client.entity.player.ClientPlayer
    public Inventory inventory() {
        return ((bua) this).bv;
    }

    @Insert(method = {"swingArm"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/NetHandlerPlayClient;sendPacket(Lnet/minecraft/network/Packet;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$cancelSwingPacket(ub hand, InsertInfo ci) {
        if (this.labyMod$cancelSwingPacket) {
            ci.cancel();
            this.labyMod$cancelSwingPacket = false;
        }
    }

    private bud labyMod$getClientPlayer() {
        return bib.z().h;
    }
}
