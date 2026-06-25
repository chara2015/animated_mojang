package labymod_menu.client.legacy;

import net.fabricmc.loader.api.FabricLoader;

import java.lang.reflect.Field;

final class LegacyCompatibility {
	private static final int DEFAULT_BACKGROUND = 0xFF050509;

	private LegacyCompatibility() {
	}

	static int loadingBackgroundColor() {
		if (!FabricLoader.getInstance().isModLoaded("dark-loading-screen")) {
			return DEFAULT_BACKGROUND;
		}
		try {
			Class<?> darkLoadingScreen = Class.forName("io.github.a5b84.darkloadingscreen.DarkLoadingScreen");
			Field configField = darkLoadingScreen.getField("config");
			Object config = configField.get(null);
			Field backgroundField = config.getClass().getField("backgroundColor");
			return 0xFF000000 | backgroundField.getInt(config) & 0xFFFFFF;
		} catch (ReflectiveOperationException ignored) {
			return DEFAULT_BACKGROUND;
		}
	}
}
