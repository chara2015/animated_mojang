package net.labymod.v1_21_11.mixins.client.minecraft;

import net.labymod.api.Laby;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/minecraft/MixinClientPlayerInteraction.class */
@Mixin({gfj.class})
public class MixinClientPlayerInteraction {

    @Shadow
    public hnh s;

    @Inject(method = {"startAttack()Z"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$handleAttack(CallbackInfoReturnable<Boolean> cir) {
        if (labyMod$fireClientPlayerInteractEvent(ClientPlayerInteractEvent.InteractionType.ATTACK)) {
            cir.setReturnValue(false);
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
}
