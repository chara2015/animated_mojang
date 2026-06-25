package labymod_menu;

import net.fabricmc.api.ModInitializer;

public final class LabyModMenuMod implements ModInitializer {
	public static final String MOD_ID = ModMetadata.MOD_ID;

	@Override
	public void onInitialize() {
		try {
			Class.forName("labymod_menu.client.legacy.IdentifierAnimationBridge")
					.getMethod("registerSounds")
					.invoke(null);
		} catch (ReflectiveOperationException exception) {
			throw new IllegalStateException("Unable to register labymod_menu sounds", exception);
		}
	}
}
