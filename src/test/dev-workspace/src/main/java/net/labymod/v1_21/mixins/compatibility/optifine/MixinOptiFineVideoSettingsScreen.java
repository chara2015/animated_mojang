package net.labymod.v1_21.mixins.compatibility.optifine;

import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/compatibility/optifine/MixinOptiFineVideoSettingsScreen.class */
@DynamicMixin(OptiFine.NAMESPACE)
@Mixin({frl.class})
public class MixinOptiFineVideoSettingsScreen {
    @Inject(method = {"updateGuiScale"}, at = {@At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwSetCursorPos(JDD)V", shift = At.Shift.BEFORE)}, cancellable = true)
    @Dynamic
    private void labyMod$fixMousePosition(CallbackInfo ci) {
        ci.cancel();
    }
}
