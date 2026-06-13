package animated_mojang.client.mixin;

import animated_mojang.client.internal.TitleOpeningController;
import animated_mojang.config.AnimatedMojangConfig;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
	@Inject(method = "extractBackground", at = @At("HEAD"), cancellable = true)
	private void animatedMojang$renderOpeningBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		if (TitleOpeningController.shouldRenderOpeningBackground()) {
			ci.cancel();
		}
	}

	@Inject(method = "extractRenderState", at = @At("HEAD"), cancellable = true)
	private void animatedMojang$hideTitleWidgetsDuringOpening(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		if (TitleOpeningController.shouldHideTitleWidgets()) {
			TitleOpeningController.renderBackground(graphics);
			TitleOpeningController.renderAnimatedMinecraftTitle(graphics, 0.0F);
			TitleOpeningController.renderFadeCover(graphics);
			ci.cancel();
		} else if (TitleOpeningController.shouldRenderOpeningBackground()) {
			TitleOpeningController.renderBackground(graphics);
		}
	}

	@Redirect(method = "extractRenderState", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/gui/components/LogoRenderer;extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IF)V"))
	private void animatedMojang$renderAnimatedMinecraftTitle(LogoRenderer logoRenderer, GuiGraphicsExtractor graphics,
			int screenWidth, float alpha) {
		if (TitleOpeningController.shouldRenderOpeningBackground()
				&& AnimatedMojangConfig.isMinecraftTitleAnimationEnabled()) {
			TitleOpeningController.renderAnimatedMinecraftTitle(graphics, alpha);
			TitleOpeningController.renderMinecraftEditionWithMenu(graphics, alpha);
		} else {
			logoRenderer.extractRenderState(graphics, screenWidth, alpha);
		}
	}

	@Inject(method = "extractRenderState", at = @At("TAIL"))
	private void animatedMojang$renderOpeningCover(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		TitleOpeningController.renderFadeCover(graphics);
	}

	@Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
	private void animatedMojang$blockEarlyOpeningInput(MouseButtonEvent event, boolean doubleClick,
			CallbackInfoReturnable<Boolean> cir) {
		if (TitleOpeningController.shouldBlockEarlyInput()) {
			cir.setReturnValue(true);
		}
	}
}
