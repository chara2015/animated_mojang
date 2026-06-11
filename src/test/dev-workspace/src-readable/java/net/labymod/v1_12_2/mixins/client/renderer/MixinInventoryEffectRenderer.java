package net.labymod.v1_12_2.mixins.client.renderer;

import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/MixinInventoryEffectRenderer.class */
@Mixin({bmr.class})
public class MixinInventoryEffectRenderer {

    @Shadow
    private boolean v;

    @Inject(method = {"updateActivePotionEffects"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/renderer/InventoryEffectRenderer;guiLeft:I", ordinal = 0, shift = At.Shift.BEFORE)}, cancellable = true)
    private void labyMod$fixInventoryPosition(CallbackInfo ci) {
        if (Laby.labyAPI().config().multiplayer().classicPvP().potionFix().get().booleanValue()) {
            ci.cancel();
            this.v = true;
        }
    }
}
