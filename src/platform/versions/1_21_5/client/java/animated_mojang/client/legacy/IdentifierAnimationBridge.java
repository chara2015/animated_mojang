package animated_mojang.client.legacy;

import animated_mojang.ModMetadata;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public final class IdentifierAnimationBridge {
	private static final ResourceLocation STARTUP_ID = ResourceLocation.fromNamespaceAndPath(ModMetadata.MOD_ID, "startup");
	private static final SoundEvent STARTUP = Registry.register(BuiltInRegistries.SOUND_EVENT, STARTUP_ID,
			SoundEvent.createVariableRangeEvent(STARTUP_ID));

	private IdentifierAnimationBridge() {
	}

	public static void preload() {
	}

	public static void registerSounds() {
	}

	public static boolean playStartupSound() {
		Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(STARTUP, 1.0F));
		return true;
	}

	public static void suppressMenuMusic() {
		Minecraft.getInstance().getMusicManager().stopPlaying();
	}

	public static boolean useLightLoadingBackground() {
		return false;
	}

	public static void startWorldBackground() {
	}

	public static boolean renderScene(GuiGraphics graphics, String screenName, float progress) {
		return false;
	}

	public static void renderEffects(GuiGraphics graphics, float progress) {
	}

	public static boolean hasVersionedScreenEffects() {
		return false;
	}
}
