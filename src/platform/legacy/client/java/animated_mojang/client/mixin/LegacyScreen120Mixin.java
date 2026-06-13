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
public abstract class LegacyScreen120Mixin {
	@Inject(method = "renderBackground(Lnet/minecraft/client/gui/GuiGraphics;)V",
			at = @At("HEAD"), cancellable = true, require = 0)
	private void animatedMojang$renderLegacy120DynamicBackground(GuiGraphics graphics, CallbackInfo ci) {
		String name = getClass().getSimpleName();
		if (Minecraft.getInstance().level == null && DynamicBackgroundScreens.matches(this)) {
			LegacyAnimations.renderScreenBackground(graphics, name);
			ci.cancel();
		}
	}
}
