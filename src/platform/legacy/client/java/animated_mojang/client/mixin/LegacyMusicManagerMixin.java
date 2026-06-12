package animated_mojang.client.mixin;

import animated_mojang.client.legacy.LegacyAnimations;
import net.minecraft.client.sounds.MusicManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicManager.class)
public final class LegacyMusicManagerMixin {
	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void animatedMojang$delayMenuMusicUntilOpeningFinishes(CallbackInfo ci) {
		if (LegacyAnimations.shouldSuppressMenuMusic()) {
			ci.cancel();
		}
	}
}
