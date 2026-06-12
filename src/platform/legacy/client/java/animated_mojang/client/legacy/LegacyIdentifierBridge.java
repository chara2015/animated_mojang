package animated_mojang.client.legacy;

import net.minecraft.client.gui.GuiGraphics;

import java.lang.reflect.Method;

final class LegacyIdentifierBridge {
	private static final Method PRELOAD = find("preload");
	private static final Method PLAY_STARTUP_SOUND = find("playStartupSound");
	private static final Method USE_LIGHT_LOADING_BACKGROUND = find("useLightLoadingBackground");
	private static final Method START_WORLD_BACKGROUND = find("startWorldBackground");
	private static final Method RENDER_SCENE = find("renderScene", GuiGraphics.class, String.class, float.class);
	private static final Method RENDER_EFFECTS = find("renderEffects", GuiGraphics.class, float.class);
	private static final Method HAS_VERSIONED_SCREEN_EFFECTS = find("hasVersionedScreenEffects");
	private static final Method SUPPRESS_MENU_MUSIC = find("suppressMenuMusic");

	private LegacyIdentifierBridge() {
	}

	static void preload() {
		invoke(PRELOAD);
	}

	static boolean playStartupSound() {
		if (PLAY_STARTUP_SOUND == null) {
			return false;
		}
		try {
			return (boolean) PLAY_STARTUP_SOUND.invoke(null);
		} catch (ReflectiveOperationException ignored) {
			return false;
		}
	}

	static boolean useLightLoadingBackground() {
		if (USE_LIGHT_LOADING_BACKGROUND == null) {
			return false;
		}
		try {
			return (boolean) USE_LIGHT_LOADING_BACKGROUND.invoke(null);
		} catch (ReflectiveOperationException ignored) {
			return false;
		}
	}

	static void startWorldBackground() {
		invoke(START_WORLD_BACKGROUND);
	}

	static boolean renderScene(GuiGraphics graphics, String screenName, float progress) {
		if (RENDER_SCENE == null) {
			return false;
		}
		try {
			return (boolean) RENDER_SCENE.invoke(null, graphics, screenName, progress);
		} catch (ReflectiveOperationException ignored) {
			return false;
		}
	}

	static void renderEffects(GuiGraphics graphics, float progress) {
		if (RENDER_EFFECTS == null) {
			return;
		}
		try {
			RENDER_EFFECTS.invoke(null, graphics, progress);
		} catch (ReflectiveOperationException ignored) {
		}
	}

	static boolean hasVersionedScreenEffects() {
		if (HAS_VERSIONED_SCREEN_EFFECTS == null) {
			return false;
		}
		try {
			return (boolean) HAS_VERSIONED_SCREEN_EFFECTS.invoke(null);
		} catch (ReflectiveOperationException ignored) {
			return false;
		}
	}

	static void suppressMenuMusic() {
		invoke(SUPPRESS_MENU_MUSIC);
	}

	private static Method find(String name, Class<?>... parameters) {
		try {
			return Class.forName("animated_mojang.client.legacy.IdentifierAnimationBridge").getMethod(name, parameters);
		} catch (ReflectiveOperationException ignored) {
			return null;
		}
	}

	private static void invoke(Method method) {
		if (method == null) {
			return;
		}
		try {
			method.invoke(null);
		} catch (ReflectiveOperationException ignored) {
		}
	}
}
