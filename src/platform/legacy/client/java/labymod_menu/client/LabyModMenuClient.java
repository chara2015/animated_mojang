package labymod_menu.client;

import labymod_menu.ModMetadata;
import labymod_menu.config.LabyModMenuConfig;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LabyModMenuClient implements ClientModInitializer {
	public static final String MOD_ID = ModMetadata.MOD_ID;
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LabyModMenuConfig.load();
		LOGGER.info("labymod_menu legacy platform initialized");
	}
}
