package net.labymod.v1_21_11.mixins.client.screen;

import java.util.function.IntSupplier;
import net.labymod.api.Laby;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LoadingOverlay;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/screen/MixinLoadingOverlayDarkBackground.class */
@Mixin({LoadingOverlay.class})
public class MixinLoadingOverlayDarkBackground {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    @Final
    private static int LOGO_BACKGROUND_COLOR_DARK;

    @Redirect(method = {"render"}, at = @At(value = "INVOKE", target = "Ljava/util/function/IntSupplier;getAsInt()I"), require = 0)
    private int labyMod$darkModeSetting(IntSupplier supplier) {
        return ((Boolean) Laby.labyAPI().config().appearance().darkLoadingScreen().get()).booleanValue() ? LOGO_BACKGROUND_COLOR_DARK : supplier.getAsInt();
    }
}

