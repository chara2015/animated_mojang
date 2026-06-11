package animated_mojang;

import animated_mojang.sound.ModSounds;
import net.fabricmc.api.ModInitializer;

public class AnimatedMojangMod implements ModInitializer {
	public static final String MOD_ID = "animated_mojang";

	@Override
	public void onInitialize() {
		ModSounds.initialize();
	}
}
