package labymod_menu.client.mixin;

import labymod_menu.client.internal.VersionedTitleBackgroundController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.server.level.progress.ChunkLoadStatusView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelLoadingScreen.class)
public abstract class VersionedLevelLoadingScreenMixin {
	@Redirect(method = "render", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/gui/screens/LevelLoadingScreen;renderChunks(Lnet/minecraft/client/gui/GuiGraphics;IIIILnet/minecraft/server/level/progress/ChunkLoadStatusView;)V"))
	private void labyModMenu$hideVanillaChunkPreview(GuiGraphics graphics, int centerX, int centerY,
			int pixelSize, int margin, ChunkLoadStatusView statusView) {
		if (!VersionedTitleBackgroundController.usesDynamicBackground(Minecraft.getInstance().screen)) {
			LevelLoadingScreen.renderChunks(graphics, centerX, centerY, pixelSize, margin, statusView);
		}
	}
}
