package net.labymod.v26_1.mixins.client.minecraft;

import net.labymod.api.Laby;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/minecraft/MixinClientPlayerInteraction.class */
@Mixin({Minecraft.class})
public class MixinClientPlayerInteraction {

    @Shadow
    public LocalPlayer player;

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

    @Inject(method = {"pickBlockOrEntity"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$handlePickBlock(CallbackInfo ci) {
        if (labyMod$fireClientPlayerInteractEvent(ClientPlayerInteractEvent.InteractionType.PICK_BLOCK)) {
            ci.cancel();
        }
    }

    private boolean labyMod$fireClientPlayerInteractEvent(ClientPlayerInteractEvent.InteractionType interactionType) {
        return ((ClientPlayerInteractEvent) Laby.fireEvent(new ClientPlayerInteractEvent(this.player, interactionType))).isCancelled();
    }
}
