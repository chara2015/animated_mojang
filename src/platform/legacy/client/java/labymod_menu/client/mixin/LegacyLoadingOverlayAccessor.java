package labymod_menu.client.mixin;

import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.server.packs.resources.ReloadInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LoadingOverlay.class)
public interface LegacyLoadingOverlayAccessor {
	@Accessor("reload")
	ReloadInstance labyModMenu$getReload();

	@Accessor("fadeIn")
	boolean labyModMenu$getFadeIn();
}
