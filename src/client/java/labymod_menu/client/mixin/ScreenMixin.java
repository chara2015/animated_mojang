package labymod_menu.client.mixin;

import labymod_menu.client.internal.TitleOpeningController;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Screen.class)
public class ScreenMixin {
	@Shadow
	private List<Renderable> renderables;

	@Inject(method = "addWidget", at = @At("RETURN"))
	private void labyModMenu$initializeTitleWidgetAlpha(GuiEventListener widget,
			CallbackInfoReturnable<GuiEventListener> cir) {
		if ((Object) this instanceof TitleScreen && widget instanceof AbstractWidget abstractWidget) {
			abstractWidget.setAlpha(TitleOpeningController.getTitleWidgetAlpha());
		}
	}

	@Inject(method = "fadeWidgets", at = @At("TAIL"))
	private void labyModMenu$fadeDirectTitleRenderables(float alpha, CallbackInfo ci) {
		if ((Object) this instanceof TitleScreen) {
			float openingAlpha = TitleOpeningController.getTitleWidgetAlpha();
			for (Renderable renderable : renderables) {
				if (renderable instanceof AbstractWidget widget) {
					widget.setAlpha(openingAlpha);
				}
			}
		}
	}

	@Inject(method = "extractPanorama", at = @At("HEAD"), cancellable = true)
	private void labyModMenu$skipTitlePanorama(GuiGraphicsExtractor graphics, float delta, CallbackInfo ci) {
		if (TitleOpeningController.shouldRenderDynamicScreenBackground()
				&& TitleOpeningController.usesDynamicBackground((Screen) (Object) this)) {
			ci.cancel();
		}
	}

	@Inject(method = "extractBackground", at = @At("HEAD"), cancellable = true)
	private void labyModMenu$keepDynamicCaveBehindMenuScreens(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		if (TitleOpeningController.shouldRenderDynamicScreenBackground()
				&& TitleOpeningController.usesDynamicBackground((Screen) (Object) this)) {
			if (!((Object) this instanceof net.minecraft.client.gui.screens.TitleScreen)) {
				if (!TitleOpeningController.isOptionsScreen((Screen) (Object) this)) {
					graphics.blurBeforeThisStratum();
				}
			}
			ci.cancel();
		}
	}

	@Inject(method = "extractRenderState", at = @At("TAIL"))
	private void labyModMenu$renderDynamicCaveEffects(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		if (!((Object) this instanceof TitleScreen)
				&& TitleOpeningController.shouldRenderDynamicScreenBackground()
				&& TitleOpeningController.usesDynamicBackground((Screen) (Object) this)) {
			TitleOpeningController.renderDynamicScreenEffects(graphics);
		}
	}
}
