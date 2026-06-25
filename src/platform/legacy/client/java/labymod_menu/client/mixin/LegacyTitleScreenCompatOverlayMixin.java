package labymod_menu.client.mixin;

import labymod_menu.client.legacy.LegacyAnimations;
import labymod_menu.config.LabyModMenuConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TitleScreen.class, priority = 1)
public final class LegacyTitleScreenCompatOverlayMixin {
	@Inject(method = "render", at = @At("TAIL"))
	private void labyModMenu$coverEarlyThirdPartyWidgets(GuiGraphics graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		if (LabyModMenuConfig.isMinecraftTitleAnimationEnabled()
				&& LegacyAnimations.shouldHideThirdPartyTitleWidgets()) {
			LegacyAnimations.renderTitleBackground(graphics);
			LegacyAnimations.renderScreenEffects(graphics);
			LegacyAnimations.renderTitle(graphics);
		}
	}
}
