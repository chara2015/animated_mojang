package net.labymod.v1_21.mixins.compatibility.optifine;

import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.v1_21.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/compatibility/optifine/MixinOptiFineLevelRendererSmoothFPS.class */
@Pseudo
@DynamicMixin(value = OptiFine.NAMESPACE, applier = OptiFineDynamicMixinApplier.class)
@Mixin({gex.class})
public class MixinOptiFineLevelRendererSmoothFPS {
    @Redirect(method = {"renderLevel"}, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glFinish()V", remap = false))
    @Dynamic
    private void labyMod$disableSmoothFPS() {
    }
}
