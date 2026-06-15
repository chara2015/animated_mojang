package animated_mojang.client.mixin;

import animated_mojang.client.legacy.LegacyAnimations;
import animated_mojang.config.AnimatedMojangConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TitleScreen.class, priority = 1)
public final class LegacyTitleScreenCompatOverlayMixin {
	@Inject(method = "render", at = @At("TAIL"))
	private void animatedMojang$coverEarlyThirdPartyWidgets(GuiGraphics graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		if (AnimatedMojangConfig.isMinecraftTitleAnimationEnabled()
				&& LegacyAnimations.shouldHideThirdPartyTitleWidgets()) {
			LegacyAnimations.renderTitleBackground(graphics);
			LegacyAnimations.renderScreenEffects(graphics);
			LegacyAnimations.renderTitle(graphics);
		}
	}
}
