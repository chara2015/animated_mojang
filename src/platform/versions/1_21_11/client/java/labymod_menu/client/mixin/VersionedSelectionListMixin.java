package labymod_menu.client.mixin;

import labymod_menu.client.internal.VersionedTitleBackgroundController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSelectionList.class)
public abstract class VersionedSelectionListMixin {
	@Inject(method = "renderListBackground", at = @At("HEAD"), cancellable = true)
	private void labyModMenu$keepDynamicBackgroundBehindLists(GuiGraphics graphics, CallbackInfo ci) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level == null && minecraft.screen != null
				&& VersionedTitleBackgroundController.usesDynamicBackground(minecraft.screen)) {
			ci.cancel();
		}
	}
}
