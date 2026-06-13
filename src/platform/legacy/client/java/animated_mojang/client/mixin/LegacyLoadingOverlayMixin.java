package animated_mojang.client.mixin;

import animated_mojang.client.legacy.LegacyAnimations;
import animated_mojang.config.AnimatedMojangConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LoadingOverlay.class)
public final class LegacyLoadingOverlayMixin {
	@Unique
	private boolean animatedMojang$animationStarted;

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void animatedMojang$startAfterReload(GuiGraphics graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		LegacyLoadingOverlayAccessor overlay = (LegacyLoadingOverlayAccessor) (Object) this;
		if (!AnimatedMojangConfig.isMojangLogoAnimationEnabled() || overlay.animatedMojang$getFadeIn()) {
			return;
		}
		if (!animatedMojang$animationStarted
				&& overlay.animatedMojang$getReload().isDone()) {
			animatedMojang$animationStarted = true;
			LegacyAnimations.beginLoading();
			return;
		}
		if (animatedMojang$animationStarted) {
			if (LegacyAnimations.isLoadingAnimationFinished()) {
				Minecraft.getInstance().setOverlay(null);
				ci.cancel();
				return;
			}
			LegacyAnimations.renderLoading(graphics);
			ci.cancel();
		}
	}

	@Inject(method = "render", at = @At("TAIL"))
	private void animatedMojang$renderLegacyLoadingAnimation(GuiGraphics graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		if (!((LegacyLoadingOverlayAccessor) (Object) this).animatedMojang$getFadeIn()
				&& AnimatedMojangConfig.isMojangLogoAnimationEnabled() && LegacyAnimations.hasLoadingAnimationStarted()
				&& !animatedMojang$animationStarted) {
			LegacyAnimations.renderLoading(graphics);
		}
	}
}
