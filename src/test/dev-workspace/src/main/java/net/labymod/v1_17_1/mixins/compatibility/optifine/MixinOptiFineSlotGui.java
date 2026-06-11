package net.labymod.v1_17_1.mixins.compatibility.optifine;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/compatibility/optifine/MixinOptiFineSlotGui.class */
@Pseudo
@DynamicMixin(OptiFine.NAMESPACE)
@Mixin(targets = {"net.optifine.gui.SlotGui"}, remap = false)
public class MixinOptiFineSlotGui {
    @Inject(method = {"renderList"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableTexture()V", shift = At.Shift.BEFORE)})
    private void labyMod$fixShaderLighting(dql poseStack, int x, int y, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
