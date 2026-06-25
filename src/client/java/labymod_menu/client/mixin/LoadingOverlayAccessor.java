package labymod_menu.client.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.server.packs.resources.ReloadInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(LoadingOverlay.class)
public interface LoadingOverlayAccessor {
	@Accessor("minecraft")
	Minecraft labyModMenu$getMinecraft();

	@Accessor("reload")
	ReloadInstance labyModMenu$getReload();

	@Accessor("onFinish")
	Consumer<Optional<Throwable>> labyModMenu$getOnFinish();

	@Accessor("fadeIn")
	boolean labyModMenu$getFadeIn();

	@Accessor("currentProgress")
	float labyModMenu$getCurrentProgress();

	@Accessor("currentProgress")
	void labyModMenu$setCurrentProgress(float value);

	@Accessor("fadeOutStart")
	long labyModMenu$getFadeOutStart();

	@Accessor("fadeOutStart")
	void labyModMenu$setFadeOutStart(long value);

	@Accessor("fadeInStart")
	long labyModMenu$getFadeInStart();

	@Accessor("fadeInStart")
	void labyModMenu$setFadeInStart(long value);
}
