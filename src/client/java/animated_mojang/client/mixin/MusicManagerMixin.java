package animated_mojang.client.mixin;

import animated_mojang.client.internal.TitleOpeningController;
import net.minecraft.client.sounds.MusicManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicManager.class)
public abstract class MusicManagerMixin {
	@Shadow
	public abstract void stopPlaying();

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void animatedMojang$delayMenuMusicUntilTitleAnimation(CallbackInfo ci) {
		if (TitleOpeningController.shouldSuppressMenuMusic()) {
			stopPlaying();
			ci.cancel();
		}
	}
}
