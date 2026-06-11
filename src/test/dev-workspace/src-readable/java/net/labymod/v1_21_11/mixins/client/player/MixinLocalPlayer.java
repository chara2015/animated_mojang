package net.labymod.v1_21_11.mixins.client.player;

import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/player/MixinLocalPlayer.class */
@Mixin({LocalPlayer.class})
public abstract class MixinLocalPlayer extends MixinAbstractLocalPlayer implements ClientPlayer {
    private boolean labyMod$cancelHandSwingPacket = false;

    @Shadow
    public abstract void swing(InteractionHand interactionHand);

    public boolean isAbilitiesFlying() {
        return labyMod$getClientPlayer().getAbilities().flying;
    }

    public float getAbilitiesWalkingSpeed() {
        return labyMod$getClientPlayer().getAbilities().getWalkingSpeed();
    }

    public boolean isHandActive() {
        return labyMod$getClientPlayer().isUsingItem();
    }

    public boolean isUsingBow() {
        return labyMod$getClientPlayer().getUseItem().getItem() == Items.BOW;
    }

    public void swingArm(LivingEntity.Hand hand) {
        swingArm(hand, false);
    }

    public void swingArm(LivingEntity.Hand hand, boolean cancelPacket) {
        InteractionHand interactionHand;
        if (hand == LivingEntity.Hand.MAIN_HAND) {
            interactionHand = InteractionHand.MAIN_HAND;
        } else {
            interactionHand = InteractionHand.OFF_HAND;
        }
        InteractionHand interactionHand2 = interactionHand;
        this.labyMod$cancelHandSwingPacket = cancelPacket;
        swing(interactionHand2);
    }

    public Inventory inventory() {
        return labyMod$getClientPlayer().getInventory();
    }

    public void setDistanceWalked(float distance) {
        ClientAvatarStateAccessor.cast(labyMod$getClientPlayer().avatarState()).labyMod$setWalkDist(distance);
    }

    public float getForwardMovingSpeed() {
        return labyMod$getClientPlayer().input.getMoveVector().y;
    }

    @Insert(method = {"swing"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$cancelHandSwingPacket(InteractionHand hand, InsertInfo ci) {
        if (this.labyMod$cancelHandSwingPacket) {
            ci.cancel();
            this.labyMod$cancelHandSwingPacket = false;
        }
    }

    private LocalPlayer labyMod$getClientPlayer() {
        return (LocalPlayer) this;
    }
}

