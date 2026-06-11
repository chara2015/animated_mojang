package animated_mojang.client;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnimatedMojangClient implements ClientModInitializer {
	public static final String MOD_ID = "animated_mojang";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static boolean initialized;

	@Override
	public void onInitializeClient() {
		initialized = true;
		LOGGER.info("Animated Mojang initialized");
	}
}
