package animated_mojang.client;

import animated_mojang.ModMetadata;
import animated_mojang.config.AnimatedMojangConfig;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AnimatedMojangClient implements ClientModInitializer {
	public static final String MOD_ID = ModMetadata.MOD_ID;
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		AnimatedMojangConfig.load();
		LOGGER.info("Animated Mojang legacy platform initialized");
	}
}
