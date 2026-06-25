package labymod_menu.client.mixin;

import labymod_menu.client.legacy.LegacyAnimations;
import labymod_menu.common.DynamicBackgroundScreens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public abstract class LegacyScreenMixin {
	@Inject(method = "addWidget", at = @At("RETURN"))
	private void labyModMenu$initializeTitleWidgetAlpha(GuiEventListener widget,
			CallbackInfoReturnable<GuiEventListener> cir) {
		if ((Object) this instanceof TitleScreen && widget instanceof AbstractWidget abstractWidget) {
			abstractWidget.setAlpha(LegacyAnimations.getTitleWidgetAlpha());
		}
	}

	@Inject(method = "renderBackground(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
			at = @At("HEAD"), cancellable = true, require = 0)
	private void labyModMenu$renderLegacyDynamicBackground(GuiGraphics graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		String name = getClass().getSimpleName();
		if (Minecraft.getInstance().level == null && DynamicBackgroundScreens.matches(this)) {
			LegacyAnimations.renderScreenBackground(graphics, name);
			if (!name.contains("Title") && LegacyAnimations.hasVersionedScreenEffects()) {
				LegacyAnimations.renderScreenEffects(graphics);
			}
			ci.cancel();
		}
	}

	@Inject(method = "render", at = @At("TAIL"), require = 0)
	private void labyModMenu$renderLegacyDynamicEffects(GuiGraphics graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		if (Minecraft.getInstance().level == null && DynamicBackgroundScreens.matches(this)
				&& !getClass().getSimpleName().contains("Title")
				&& !LegacyAnimations.hasVersionedScreenEffects()) {
			LegacyAnimations.renderScreenEffects(graphics);
		}
	}
}
