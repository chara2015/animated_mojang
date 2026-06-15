package animated_mojang.client.mixin;

import animated_mojang.client.legacy.LegacyAnimations;
import animated_mojang.config.AnimatedMojangConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public final class LegacyTitleScreenMixin {
	@Inject(method = "added", at = @At("HEAD"))
	private void animatedMojang$startLegacyTitleAnimation(CallbackInfo ci) {
		if (AnimatedMojangConfig.isMinecraftTitleAnimationEnabled() && Minecraft.getInstance().getOverlay() == null
				&& !LegacyAnimations.hasLoadingAnimationStarted()) {
			LegacyAnimations.startTitle();
		}
	}

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void animatedMojang$hideLegacyTitleWidgets(GuiGraphics graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		if (AnimatedMojangConfig.isMinecraftTitleAnimationEnabled() && LegacyAnimations.shouldHideTitleWidgets()) {
			LegacyAnimations.renderTitleBackground(graphics);
			LegacyAnimations.renderScreenEffects(graphics);
			LegacyAnimations.renderTitle(graphics);
			ci.cancel();
		} else {
			LegacyAnimations.renderScreenEffects(graphics);
		}
	}

	@Redirect(method = "render", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/gui/screens/TitleScreen;renderPanorama(Lnet/minecraft/client/gui/GuiGraphics;F)V"),
			require = 0)
	private void animatedMojang$replaceVanillaPanorama(TitleScreen screen, GuiGraphics graphics, float delta) {
		LegacyAnimations.renderTitleBackground(graphics);
	}

	@Redirect(method = "render", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/gui/components/LogoRenderer;renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IF)V"),
			require = 0)
	private void animatedMojang$replaceVanillaTitleLogo(LogoRenderer renderer, GuiGraphics graphics,
			int screenWidth, float alpha) {
		if (AnimatedMojangConfig.isMinecraftTitleAnimationEnabled()) {
			LegacyAnimations.renderTitle(graphics, 1.0F);
		} else {
			renderer.renderLogo(graphics, screenWidth, alpha);
		}
	}
}
