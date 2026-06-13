package animated_mojang.client.mixin;

import animated_mojang.client.internal.VersionedTitleBackgroundController;
import animated_mojang.client.legacy.LegacyAnimations;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class VersionedScreenEffectsMixin {
	@Inject(method = "renderBackground(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
			at = @At("HEAD"), cancellable = true, require = 0)
	private void animatedMojang$replaceVersionedBackground(GuiGraphics graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		if (usesDynamicBackground()) {
			LegacyAnimations.renderScreenEffects(graphics);
			ci.cancel();
		}
	}

	@Inject(method = "renderTransparentBackground(Lnet/minecraft/client/gui/GuiGraphics;)V",
			at = @At("HEAD"), cancellable = true, require = 0)
	private void animatedMojang$replaceVersionedTransparentBackground(GuiGraphics graphics, CallbackInfo ci) {
		if (usesDynamicBackground()) {
			LegacyAnimations.renderScreenEffects(graphics);
			ci.cancel();
		}
	}

	@Inject(method = "renderMenuBackground(Lnet/minecraft/client/gui/GuiGraphics;)V",
			at = @At("HEAD"), cancellable = true, require = 0)
	private void animatedMojang$keepVersionedDynamicBackground(GuiGraphics graphics, CallbackInfo ci) {
		if (usesDynamicBackground()) {
			LegacyAnimations.renderScreenEffects(graphics);
			ci.cancel();
		}
	}

	private boolean usesDynamicBackground() {
		return VersionedTitleBackgroundController.usesDynamicBackground((Screen) (Object) this);
	}
}
