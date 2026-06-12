package animated_mojang.client.mixin;

import animated_mojang.client.legacy.LegacyAnimations;
import animated_mojang.config.AnimatedMojangConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.Overlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LoadingOverlay.class)
public final class LegacyLoadingCompletionMixin {
	@Redirect(method = "render", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/Minecraft;setOverlay(Lnet/minecraft/client/gui/screens/Overlay;)V"),
			require = 0)
	private void animatedMojang$finishAfterAnimation(Minecraft minecraft, Overlay overlay) {
		if (overlay == null && AnimatedMojangConfig.isMojangLogoAnimationEnabled()) {
			if (!LegacyAnimations.hasLoadingAnimationStarted()) {
				return;
			}
			if (!LegacyAnimations.isLoadingAnimationFinished()) {
				return;
			}
			LegacyAnimations.finishLoading();
		}
		minecraft.setOverlay(overlay);
	}
}
