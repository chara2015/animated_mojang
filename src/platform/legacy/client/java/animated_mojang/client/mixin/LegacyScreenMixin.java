package animated_mojang.client.mixin;

import animated_mojang.client.legacy.LegacyAnimations;
import animated_mojang.common.DynamicBackgroundScreens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class LegacyScreenMixin {
	@Inject(method = "renderBackground", at = @At("HEAD"), cancellable = true, require = 0)
	private void animatedMojang$renderLegacyDynamicBackground(GuiGraphics graphics, int mouseX, int mouseY,
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
	private void animatedMojang$renderLegacyDynamicEffects(GuiGraphics graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		if (Minecraft.getInstance().level == null && DynamicBackgroundScreens.matches(this)
				&& !getClass().getSimpleName().contains("Title")
				&& !LegacyAnimations.hasVersionedScreenEffects()) {
			LegacyAnimations.renderScreenEffects(graphics);
		}
	}
}
