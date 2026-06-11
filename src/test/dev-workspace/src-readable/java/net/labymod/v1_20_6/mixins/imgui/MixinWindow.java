package net.labymod.v1_20_6.mixins.imgui;

import net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.ImGuiPipeline;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/imgui/MixinWindow.class */
@Mixin({eze.class})
public class MixinWindow {

    @Shadow
    @Final
    private long e;

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$initializeImGui(ezf handler, ezc screenManager, eyr displayData, String videoMode, String title, CallbackInfo ci) {
        ImGuiPipeline.getInstance().initialize(this.e);
    }
}
