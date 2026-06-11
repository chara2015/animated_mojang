package animated_mojang.client.mixin;

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
	Minecraft animatedMojang$getMinecraft();

	@Accessor("reload")
	ReloadInstance animatedMojang$getReload();

	@Accessor("onFinish")
	Consumer<Optional<Throwable>> animatedMojang$getOnFinish();

	@Accessor("fadeIn")
	boolean animatedMojang$getFadeIn();

	@Accessor("currentProgress")
	float animatedMojang$getCurrentProgress();

	@Accessor("currentProgress")
	void animatedMojang$setCurrentProgress(float value);

	@Accessor("fadeOutStart")
	long animatedMojang$getFadeOutStart();

	@Accessor("fadeOutStart")
	void animatedMojang$setFadeOutStart(long value);

	@Accessor("fadeInStart")
	long animatedMojang$getFadeInStart();

	@Accessor("fadeInStart")
	void animatedMojang$setFadeInStart(long value);
}
