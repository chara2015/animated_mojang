package animated_mojang.client.mixin;

import animated_mojang.client.legacy.LegacyAnimations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractWidget.class)
public abstract class LegacyTitleWidgetAlphaMixin {
	@Shadow
	protected float alpha;

	@Inject(method = "setAlpha", at = @At("HEAD"), cancellable = true)
	private void animatedMojang$keepOpeningAlphaContinuous(float alpha, CallbackInfo ci) {
		if (Minecraft.getInstance().screen instanceof TitleScreen
				&& LegacyAnimations.getTitleWidgetAlpha() < 1.0F) {
			this.alpha = LegacyAnimations.getTitleWidgetAlpha();
			ci.cancel();
		}
	}
}
