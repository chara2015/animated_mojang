package labymod_menu.client.mixin;

import labymod_menu.config.LabyModMenuConfig;
import net.minecraft.client.gui.screens.LoadingOverlay;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.IntSupplier;

@Mixin(LoadingOverlay.class)
public abstract class LegacyLoadingOverlayBackgroundMixin {
	private static final int DARK_LOADING_BACKGROUND = 0xFF14181C;

	@Mutable
	@Shadow
	@Final
	private static IntSupplier BRAND_BACKGROUND;

	@Inject(method = "<clinit>", at = @At("RETURN"))
	private static void labyModMenu$replaceLoadingBackground(CallbackInfo ci) {
		IntSupplier vanillaBackground = BRAND_BACKGROUND;
		BRAND_BACKGROUND = () -> LabyModMenuConfig.isDarkLoadingScreenEnabled()
				? DARK_LOADING_BACKGROUND : vanillaBackground.getAsInt();
	}
}
