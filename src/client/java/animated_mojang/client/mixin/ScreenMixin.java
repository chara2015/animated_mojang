package animated_mojang.client.mixin;

import animated_mojang.client.internal.TitleOpeningController;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {
	@Inject(method = "extractPanorama", at = @At("HEAD"), cancellable = true)
	private void animatedMojang$skipTitlePanorama(GuiGraphicsExtractor graphics, float delta, CallbackInfo ci) {
		if (TitleOpeningController.shouldRenderDynamicScreenBackground()
				&& TitleOpeningController.usesDynamicBackground((Screen) (Object) this)) {
			ci.cancel();
		}
	}

	@Inject(method = "extractBackground", at = @At("HEAD"), cancellable = true)
	private void animatedMojang$keepDynamicCaveBehindMenuScreens(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		if (TitleOpeningController.shouldRenderDynamicScreenBackground()
				&& TitleOpeningController.usesDynamicBackground((Screen) (Object) this)) {
			if (!((Object) this instanceof net.minecraft.client.gui.screens.TitleScreen)) {
				graphics.blurBeforeThisStratum();
			}
			ci.cancel();
		}
	}

	@Inject(method = "extractRenderState", at = @At("TAIL"))
	private void animatedMojang$renderDynamicCaveEffects(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		TitleOpeningController.renderDynamicScreenEffects(graphics);
	}
}
