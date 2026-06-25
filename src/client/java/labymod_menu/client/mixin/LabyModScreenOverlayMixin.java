package labymod_menu.client.mixin;

import labymod_menu.client.internal.TitleOpeningController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.labymod.core.client.gui.screen.overlay.DefaultScreenOverlayHandler", remap = false)
public final class LabyModScreenOverlayMixin {
	@Inject(method = "onGameRender", at = @At("HEAD"), cancellable = true, require = 0)
	private void labyModMenu$hideEarlyTitleOverlays(CallbackInfo ci) {
		if (Minecraft.getInstance().screen instanceof TitleScreen
				&& TitleOpeningController.shouldHidePostTitleOverlays()) {
			ci.cancel();
		}
	}
}
