package animated_mojang;

import net.fabricmc.api.ModInitializer;

public final class AnimatedMojangMod implements ModInitializer {
	public static final String MOD_ID = ModMetadata.MOD_ID;

	@Override
	public void onInitialize() {
		try {
			Class.forName("animated_mojang.client.legacy.IdentifierAnimationBridge")
					.getMethod("registerSounds")
					.invoke(null);
		} catch (ReflectiveOperationException exception) {
			throw new IllegalStateException("Unable to register Animated Mojang sounds", exception);
		}
	}
}
