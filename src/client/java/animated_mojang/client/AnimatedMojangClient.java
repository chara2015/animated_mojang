package animated_mojang.client;

import animated_mojang.ModMetadata;
import animated_mojang.config.AnimatedMojangConfig;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnimatedMojangClient implements ClientModInitializer {
	public static final String MOD_ID = ModMetadata.MOD_ID;
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static boolean initialized;

	@Override
	public void onInitializeClient() {
		AnimatedMojangConfig.load();
		initialized = true;
		LOGGER.info("Animated Mojang initialized");
	}
}
