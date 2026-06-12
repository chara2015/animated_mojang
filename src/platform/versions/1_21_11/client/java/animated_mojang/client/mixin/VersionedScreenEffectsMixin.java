package animated_mojang.client.mixin;

import animated_mojang.client.internal.VersionedTitleBackgroundController;
import animated_mojang.client.legacy.LegacyAnimations;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class VersionedScreenEffectsMixin {
	@Inject(method = "render", at = @At("TAIL"), require = 0)
	private void animatedMojang$renderDynamicEffects(GuiGraphics graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		renderEffects(graphics);
	}

	private void renderEffects(GuiGraphics graphics) {
		if (VersionedTitleBackgroundController.usesDynamicBackground((Screen) (Object) this)
				&& !getClass().getSimpleName().contains("Title")) {
			LegacyAnimations.renderScreenEffects(graphics);
		}
	}
}
