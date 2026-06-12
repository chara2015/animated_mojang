package animated_mojang.client.mixin;

import animated_mojang.client.internal.TitleOpeningController;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ GenericMessageScreen.class, LevelLoadingScreen.class })
public class TransitionScreenMixin {
	@Inject(method = "extractBackground", at = @At("HEAD"), cancellable = true)
	private void animatedMojang$keepDynamicCaveDuringTransition(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		if (TitleOpeningController.shouldRenderDynamicScreenBackground()) {
			graphics.blurBeforeThisStratum();
			ci.cancel();
		}
	}
}
