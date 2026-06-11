package net.labymod.v26_1.mixins.client.window;

import net.labymod.v26_1.client.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/window/MixinMinecraft_BorderlessWindow.class */
@Mixin({Minecraft.class})
public class MixinMinecraft_BorderlessWindow {
    @Inject(method = {"run"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;pollEvents()V", shift = At.Shift.AFTER)})
    private void labyMod$pollTasks(CallbackInfo ci) {
        MinecraftUtil.pollBorderlessWindowTasks();
    }
}
