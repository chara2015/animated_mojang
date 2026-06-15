package animated_mojang.client.mixin;

import animated_mojang.client.internal.TitleOpeningController;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TitleScreen.class, priority = 1)
public class TitleScreenCompatOverlayMixin {
	@Inject(method = "extractRenderState", at = @At("TAIL"))
	private void animatedMojang$coverEarlyThirdPartyWidgets(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		if (TitleOpeningController.shouldHideThirdPartyTitleWidgets()) {
			TitleOpeningController.renderHiddenTitleOverlay(graphics);
		}
	}
}
