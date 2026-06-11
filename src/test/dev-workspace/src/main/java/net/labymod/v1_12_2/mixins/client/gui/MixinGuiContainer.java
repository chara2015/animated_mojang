package net.labymod.v1_12_2.mixins.client.gui;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/MixinGuiContainer.class */
@Mixin({bmg.class})
public abstract class MixinGuiContainer {
    @Shadow
    protected abstract boolean b(int i);

    @WrapOperation(method = {"drawScreen"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;colorMask(ZZZZ)V")})
    private void labyMod$fixSlotHighlighting(boolean lvt_0_1_, boolean lvt_1_1_, boolean lvt_2_1_, boolean lvt_3_1_, Operation<Void> original) {
        if (lvt_3_1_) {
            original.call(new Object[]{Boolean.valueOf(lvt_0_1_), Boolean.valueOf(lvt_1_1_), Boolean.valueOf(lvt_2_1_), Boolean.valueOf(lvt_3_1_)});
        }
    }

    @Inject(method = {"mouseClicked"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;mouseClicked(III)V", shift = At.Shift.AFTER)}, cancellable = true)
    public void labyMod$fixMouseButtonsInInventory(int lvt_1_1_, int lvt_2_1_, int lvt_3_1_, CallbackInfo ci) {
        if (b(lvt_3_1_ - 100)) {
            ci.cancel();
        }
    }
}
