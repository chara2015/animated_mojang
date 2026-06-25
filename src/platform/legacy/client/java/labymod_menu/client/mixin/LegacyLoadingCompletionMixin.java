package labymod_menu.client.mixin;

import labymod_menu.client.legacy.LegacyAnimations;
import labymod_menu.config.LabyModMenuConfig;
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
	private void labyModMenu$finishAfterAnimation(Minecraft minecraft, Overlay overlay) {
		if (((LegacyLoadingOverlayAccessor) (Object) this).labyModMenu$getFadeIn()) {
			minecraft.setOverlay(overlay);
			return;
		}
		if (overlay == null && LabyModMenuConfig.isMojangLogoAnimationEnabled()) {
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
