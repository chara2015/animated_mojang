package net.labymod.v1_17_1.mixins.client.minecraft;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.event.client.entity.player.ClientPlayerUseItemOnBlockEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/minecraft/MixinClientPlayerInteraction.class */
@Mixin({dvp.class})
public class MixinClientPlayerInteraction {

    @Shadow
    public emm s;

    @Insert(method = {"startAttack()V"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$handleAttack(InsertInfo callbackInfo) {
        if (labyMod$fireClientPlayerInteractEvent(ClientPlayerInteractEvent.InteractionType.ATTACK)) {
            callbackInfo.cancel();
        }
    }

    @Insert(method = {"startUseItem()V"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$handleUseItem(InsertInfo callbackInfo) {
        if (labyMod$fireClientPlayerInteractEvent(ClientPlayerInteractEvent.InteractionType.INTERACT)) {
            callbackInfo.cancel();
        }
    }

    @Insert(method = {"continueAttack(Z)V"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$handleContinueAttack(boolean leftClick, InsertInfo callbackInfo) {
        if (leftClick && labyMod$fireClientPlayerInteractEvent(ClientPlayerInteractEvent.InteractionType.CONTINUE_ATTACK)) {
            callbackInfo.cancel();
        }
    }

    @Insert(method = {"pickBlock()V"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$handlePickBlock(InsertInfo callbackInfo) {
        if (labyMod$fireClientPlayerInteractEvent(ClientPlayerInteractEvent.InteractionType.PICK_BLOCK)) {
            callbackInfo.cancel();
        }
    }

    private boolean labyMod$fireClientPlayerInteractEvent(ClientPlayerInteractEvent.InteractionType interactionType) {
        return ((ClientPlayerInteractEvent) Laby.fireEvent(new ClientPlayerInteractEvent(this.s, interactionType))).isCancelled();
    }

    @WrapOperation(method = {"startUseItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;useItemOn(Lnet/minecraft/client/player/LocalPlayer;Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;")})
    private asb labyMod$onUseItemOn(ejl gamemode, emm player, eji clientLevel, asa hand, dmw blockHit, Operation<asb> op) {
        LivingEntity.Hand hand2;
        if (hand == asa.a) {
            hand2 = LivingEntity.Hand.MAIN_HAND;
        } else {
            hand2 = LivingEntity.Hand.OFF_HAND;
        }
        LivingEntity.Hand lmHand = hand2;
        ClientPlayerUseItemOnBlockEvent event = (ClientPlayerUseItemOnBlockEvent) Laby.fireEvent(new ClientPlayerUseItemOnBlockEvent((ClientPlayer) player, lmHand));
        if (event.isCancelled()) {
            return asb.d;
        }
        return (asb) op.call(new Object[]{gamemode, player, clientLevel, hand, blockHit});
    }
}
