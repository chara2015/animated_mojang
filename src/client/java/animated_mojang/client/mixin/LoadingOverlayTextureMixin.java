package animated_mojang.client.mixin;

import animated_mojang.client.AnimatedMojangClient;
import animated_mojang.client.internal.ClasspathTexture;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LoadingOverlay.class)
public class LoadingOverlayTextureMixin {
	@Unique
	private static final Identifier ANIMATION_TEXTURE = Identifier.fromNamespaceAndPath(
			AnimatedMojangClient.MOD_ID, "textures/gui/mojang_animation.png");
	@Unique
	private static final Identifier STUDIOS_TEXTURE = Identifier.fromNamespaceAndPath(
			AnimatedMojangClient.MOD_ID, "textures/gui/studios.png");

	@Inject(method = "registerTextures", at = @At("TAIL"))
	private static void animatedMojang$registerTextures(TextureManager textureManager, CallbackInfo ci) {
		textureManager.registerAndLoad(ANIMATION_TEXTURE, new ClasspathTexture(ANIMATION_TEXTURE));
		textureManager.registerAndLoad(STUDIOS_TEXTURE, new ClasspathTexture(STUDIOS_TEXTURE));
	}
}
