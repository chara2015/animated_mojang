package labymod_menu.client.mixin;

import labymod_menu.client.internal.TitleOpeningController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "ru.vidtu.ias.IASMinecraft", remap = false)
public final class InGameAccountSwitcherMixin {
	@Inject(method = "onDraw", at = @At("HEAD"), cancellable = true, require = 0)
	private static void labyModMenu$hideEarlyTitleText(CallbackInfo ci) {
		if (Minecraft.getInstance().screen instanceof TitleScreen
				&& TitleOpeningController.shouldHideThirdPartyTitleWidgets()) {
			ci.cancel();
		}
	}

	@ModifyArg(method = "onDraw", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;text(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"),
			index = 4, require = 0)
	private static int labyModMenu$fadeTitleText(int color) {
		if (!(Minecraft.getInstance().screen instanceof TitleScreen)) {
			return color;
		}
		int alpha = Math.round((color >>> 24) * TitleOpeningController.getTitleWidgetAlpha());
		return alpha << 24 | color & 0xFFFFFF;
	}
}
