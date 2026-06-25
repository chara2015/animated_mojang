package labymod_menu.client.mixin;

import labymod_menu.client.legacy.LegacyAnimations;
import labymod_menu.config.LabyModMenuConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LoadingOverlay.class, priority = 1200)
public final class LegacyLoadingOverlayMixin {
	@Unique
	private boolean labyModMenu$animationStarted;

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void labyModMenu$startAfterReload(GuiGraphics graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		LegacyLoadingOverlayAccessor overlay = (LegacyLoadingOverlayAccessor) (Object) this;
		if (!LabyModMenuConfig.isMojangLogoAnimationEnabled()) {
			return;
		}
		if (!labyModMenu$animationStarted) {
			labyModMenu$animationStarted = true;
			LegacyAnimations.beginLoading();
		}
		LegacyAnimations.renderLoading(graphics);
		if (LegacyAnimations.isLoadingAnimationFinished() && overlay.labyModMenu$getReload().isDone()) {
			Minecraft.getInstance().setOverlay(null);
		}
		ci.cancel();
	}

	@Inject(method = "render", at = @At("TAIL"))
	private void labyModMenu$renderLegacyLoadingAnimation(GuiGraphics graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		if (LabyModMenuConfig.isMojangLogoAnimationEnabled() && LegacyAnimations.hasLoadingAnimationStarted()
				&& !labyModMenu$animationStarted) {
			LegacyAnimations.renderLoading(graphics);
		}
	}
}
