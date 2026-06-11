package net.labymod.v1_8_9.mixins.client.gui;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.window.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/gui/MixinGuiMultiplayer.class */
@Mixin({azh.class})
public class MixinGuiMultiplayer {

    @Shadow
    private azl h;

    @Inject(method = {"initGui()V"}, at = {@At("RETURN")})
    protected void init(CallbackInfo ci) {
        Window window = Laby.labyAPI().minecraft().minecraftWindow();
        this.h.a(window.getScaledWidth(), window.getScaledHeight(), 32, window.getScaledHeight() - 64);
    }

    @Redirect(method = {"drawScreen(IIF)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMultiplayer;drawCenteredString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V"))
    public void clearTitle(azh guiMultiplayer, avn p_drawCenteredString_1_, String p_drawCenteredString_2_, int p_drawCenteredString_3_, int p_drawCenteredString_4_, int p_drawCenteredString_5_) {
    }
}
