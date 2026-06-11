package net.labymod.v1_21_11.mixins.compatibility.optifine;

import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/compatibility/optifine/MixinOptiFineVideoSettingsScreen.class */
@DynamicMixin("optifine")
@Mixin({VideoSettingsScreen.class})
public class MixinOptiFineVideoSettingsScreen {
    @Inject(method = {"updateGuiScale"}, at = {@At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwSetCursorPos(JDD)V", shift = At.Shift.BEFORE)}, cancellable = true)
    @Dynamic
    private void labyMod$fixMousePosition(CallbackInfo ci) {
        ci.cancel();
    }
}
