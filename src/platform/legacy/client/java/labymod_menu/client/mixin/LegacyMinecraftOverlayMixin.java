package labymod_menu.client.mixin;

import labymod_menu.client.legacy.LegacyAnimations;
import labymod_menu.config.LabyModMenuConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Overlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public final class LegacyMinecraftOverlayMixin {
	@Inject(method = "setOverlay", at = @At("HEAD"), cancellable = true)
	private void labyModMenu$holdLoadingOverlayUntilAnimationFinishes(Overlay overlay, CallbackInfo ci) {
		if (overlay != null || !LabyModMenuConfig.isMojangLogoAnimationEnabled()
				|| !LegacyAnimations.hasLoadingAnimationStarted()) {
			return;
		}
		if (!LegacyAnimations.isLoadingAnimationFinished()) {
			ci.cancel();
			return;
		}
		LegacyAnimations.finishLoading();
	}
}
