package animated_mojang.config;

import animated_mojang.ModMetadata;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class AnimatedMojangConfig {
	private static final String MOJANG_LOGO_ANIMATION = "mojangLogoAnimation";
	private static final String MINECRAFT_TITLE_ANIMATION = "minecraftTitleAnimation";
	private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir()
			.resolve(ModMetadata.MOD_ID + ".properties");

	private static boolean loaded;
	private static boolean mojangLogoAnimation = true;
	private static boolean minecraftTitleAnimation = true;

	private AnimatedMojangConfig() {
	}

	public static synchronized void load() {
		if (loaded) {
			return;
		}
		loaded = true;
		Properties properties = new Properties();
		if (Files.isRegularFile(CONFIG_PATH)) {
			try (InputStream input = Files.newInputStream(CONFIG_PATH)) {
				properties.load(input);
			} catch (IOException ignored) {
			}
		}
		mojangLogoAnimation = booleanProperty(properties, MOJANG_LOGO_ANIMATION, true);
		minecraftTitleAnimation = booleanProperty(properties, MINECRAFT_TITLE_ANIMATION, true);
	}

	public static synchronized void save() {
		load();
		Properties properties = new Properties();
		properties.setProperty(MOJANG_LOGO_ANIMATION, Boolean.toString(mojangLogoAnimation));
		properties.setProperty(MINECRAFT_TITLE_ANIMATION, Boolean.toString(minecraftTitleAnimation));
		try {
			Files.createDirectories(CONFIG_PATH.getParent());
			try (OutputStream output = Files.newOutputStream(CONFIG_PATH)) {
				properties.store(output, ModMetadata.MOD_NAME + " settings");
			}
		} catch (IOException ignored) {
		}
	}

	public static boolean isMojangLogoAnimationEnabled() {
		load();
		return mojangLogoAnimation;
	}

	public static boolean isMinecraftTitleAnimationEnabled() {
		load();
		return minecraftTitleAnimation;
	}

	public static void setMojangLogoAnimationEnabled(boolean enabled) {
		load();
		mojangLogoAnimation = enabled;
	}

	public static void setMinecraftTitleAnimationEnabled(boolean enabled) {
		load();
		minecraftTitleAnimation = enabled;
	}

	private static boolean booleanProperty(Properties properties, String key, boolean fallback) {
		String value = properties.getProperty(key);
		return value == null ? fallback : Boolean.parseBoolean(value);
	}
}
