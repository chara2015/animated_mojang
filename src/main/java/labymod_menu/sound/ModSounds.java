package labymod_menu.sound;

import labymod_menu.LabyModMenuMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public final class ModSounds {
	public static final SoundEvent STARTUP = register("startup");

	private ModSounds() {
	}

	private static SoundEvent register(String id) {
		Identifier location = Identifier.fromNamespaceAndPath(LabyModMenuMod.MOD_ID, id);
		SoundEvent sound = SoundEvent.createVariableRangeEvent(location);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, location, sound);
	}

	public static void initialize() {
	}
}
