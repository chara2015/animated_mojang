package animated_mojang.client.legacy;

import animated_mojang.ModMetadata;
import animated_mojang.client.internal.VersionedSchematicScene;
import animated_mojang.client.internal.VersionedTitleBackgroundController;
import animated_mojang.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.resources.Identifier;

public final class IdentifierAnimationBridge {
	private static boolean preloaded;

	private IdentifierAnimationBridge() {
	}

	public static void preload() {
		if (preloaded) {
			return;
		}
		preloaded = true;
		Minecraft minecraft = Minecraft.getInstance();
		load(minecraft, "textures/gui/mojang_animation.png");
		load(minecraft, "textures/gui/studios.png");
		for (int index = 0; index < 9; index++) {
			load(minecraft, "textures/gui/title/minecraft_animation/minecraft_" + index + ".png");
		}
	}

	public static boolean playStartupSound() {
		SoundEngine.PlayResult result = Minecraft.getInstance().getSoundManager()
				.play(SimpleSoundInstance.forUI(ModSounds.STARTUP, 1.0F));
		return result != SoundEngine.PlayResult.NOT_STARTED;
	}

	public static void suppressMenuMusic() {
		Minecraft.getInstance().getMusicManager().stopPlaying();
	}

	public static boolean useLightLoadingBackground() {
		return true;
	}

	public static void startWorldBackground() {
		VersionedTitleBackgroundController.startOpening();
	}

	public static boolean renderScene(GuiGraphics graphics, String screenName, float progress) {
		return true;
	}

	public static void renderEffects(GuiGraphics graphics, float progress) {
		float time = VersionedTitleBackgroundController.animationTime();
		VersionedSchematicScene.get().renderTorchEffects(graphics, progress, time, time);
	}

	public static boolean hasVersionedScreenEffects() {
		return true;
	}

	private static void load(Minecraft minecraft, String path) {
		Identifier location = Identifier.fromNamespaceAndPath(ModMetadata.MOD_ID, path);
		minecraft.getTextureManager().registerAndLoad(location, new SimpleTexture(location));
	}
}
