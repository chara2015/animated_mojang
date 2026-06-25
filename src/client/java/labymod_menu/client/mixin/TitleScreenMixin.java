package labymod_menu.client.mixin;

import labymod_menu.client.internal.TitleOpeningController;
import labymod_menu.config.LabyModMenuConfig;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
	@Inject(method = "extractBackground", at = @At("HEAD"), cancellable = true)
	private void labyModMenu$renderOpeningBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		TitleOpeningController.playOpening();
		if (TitleOpeningController.shouldRenderOpeningBackground()) {
			ci.cancel();
		}
	}

	@Inject(method = "extractRenderState", at = @At("HEAD"), cancellable = true)
	private void labyModMenu$hideTitleWidgetsDuringOpening(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		TitleOpeningController.playOpening();
		if (TitleOpeningController.shouldHideTitleWidgets()) {
			TitleOpeningController.renderBackground(graphics);
			TitleOpeningController.renderAnimatedMinecraftTitle(graphics, 1.0F);
			TitleOpeningController.renderFadeCover(graphics);
			ci.cancel();
		} else if (TitleOpeningController.shouldRenderOpeningBackground()) {
			TitleOpeningController.renderBackground(graphics);
		}
	}

	@ModifyArg(method = "extractRenderState", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/gui/screens/TitleScreen;fadeWidgets(F)V"), index = 0, require = 0)
	private float labyModMenu$useOpeningWidgetFade(float vanillaAlpha) {
		return TitleOpeningController.getTitleWidgetAlpha();
	}

	@Redirect(method = "extractRenderState", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/gui/components/LogoRenderer;extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IF)V"))
	private void labyModMenu$renderAnimatedMinecraftTitle(LogoRenderer logoRenderer, GuiGraphicsExtractor graphics,
			int screenWidth, float alpha) {
		if (TitleOpeningController.shouldRenderOpeningBackground()
				&& LabyModMenuConfig.isMinecraftTitleAnimationEnabled()) {
			TitleOpeningController.renderAnimatedMinecraftTitle(graphics, 1.0F);
			TitleOpeningController.renderMinecraftEditionWithMenu(graphics, alpha);
		} else {
			logoRenderer.extractRenderState(graphics, screenWidth, alpha);
		}
	}

	@Inject(method = "extractRenderState", at = @At("TAIL"))
	private void labyModMenu$renderOpeningCover(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		TitleOpeningController.renderFadeCover(graphics);
	}

	@Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
	private void labyModMenu$blockEarlyOpeningInput(MouseButtonEvent event, boolean doubleClick,
			CallbackInfoReturnable<Boolean> cir) {
		if (TitleOpeningController.shouldBlockEarlyInput()) {
			cir.setReturnValue(true);
		}
	}
}
