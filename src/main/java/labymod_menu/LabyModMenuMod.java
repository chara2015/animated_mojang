package labymod_menu;

import net.fabricmc.api.ModInitializer;

public class LabyModMenuMod implements ModInitializer {
	public static final String MOD_ID = ModMetadata.MOD_ID;

	@Override
	public void onInitialize() {
		if (!invokeInitializer("labymod_menu.sound.ModSounds", "initialize")) {
			invokeInitializer("labymod_menu.client.legacy.IdentifierAnimationBridge", "registerSounds");
		}
	}

	private static boolean invokeInitializer(String className, String methodName) {
		try {
			Class.forName(className).getMethod(methodName).invoke(null);
			return true;
		} catch (ClassNotFoundException ignored) {
			return false;
		} catch (ReflectiveOperationException exception) {
			throw new IllegalStateException("Unable to initialize " + className, exception);
		}
	}
}
