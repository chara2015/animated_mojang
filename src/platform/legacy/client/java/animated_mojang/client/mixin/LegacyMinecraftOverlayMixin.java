package animated_mojang.client.mixin;

import animated_mojang.client.legacy.LegacyAnimations;
import animated_mojang.config.AnimatedMojangConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Overlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public final class LegacyMinecraftOverlayMixin {
	@Inject(method = "setOverlay", at = @At("HEAD"), cancellable = true)
	private void animatedMojang$holdLoadingOverlayUntilAnimationFinishes(Overlay overlay, CallbackInfo ci) {
		if (overlay != null || !AnimatedMojangConfig.isMojangLogoAnimationEnabled()
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
