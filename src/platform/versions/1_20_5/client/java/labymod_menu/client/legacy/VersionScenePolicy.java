package labymod_menu.client.legacy;

/**
 * The 1.20.5 Stonecutter target is compiled against Minecraft 1.20.6.  Keep the
 * source with the target directory so the complete legacy scene implementation
 * (including its startup-audio bridge) is selected consistently.
 */
public final class VersionScenePolicy {
	private VersionScenePolicy() {
	}

	static boolean isInvisible(String state) {
		return state == null || state.contains(":air") || state.startsWith("minecraft:barrier");
	}
}
