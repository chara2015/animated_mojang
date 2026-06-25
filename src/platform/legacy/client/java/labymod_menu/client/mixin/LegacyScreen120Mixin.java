package labymod_menu.client.mixin;

import labymod_menu.client.legacy.LegacyAnimations;
import labymod_menu.common.DynamicBackgroundScreens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class LegacyScreen120Mixin {
	@Inject(method = "renderBackground(Lnet/minecraft/client/gui/GuiGraphics;)V",
			at = @At("HEAD"), cancellable = true, require = 0)
	private void labyModMenu$renderLegacy120DynamicBackground(GuiGraphics graphics, CallbackInfo ci) {
		String name = getClass().getSimpleName();
		if (Minecraft.getInstance().level == null && DynamicBackgroundScreens.matches(this)) {
			LegacyAnimations.renderScreenBackground(graphics, name);
			ci.cancel();
		}
	}

	@Inject(method = "render", at = @At("TAIL"), require = 0)
	private void labyModMenu$renderLegacy120DynamicEffects(GuiGraphics graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		if (Minecraft.getInstance().level == null && DynamicBackgroundScreens.matches(this)
				&& !getClass().getSimpleName().contains("Title")
				&& !LegacyAnimations.hasVersionedScreenEffects()) {
			LegacyAnimations.renderScreenEffects(graphics);
		}
	}
}
