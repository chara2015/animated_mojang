package labymod_menu.client.internal;

final class VersionScenePolicy {
	private VersionScenePolicy() {}
	static boolean isInvisible(String state) {
		return state == null || state.contains(":air") || state.startsWith("minecraft:barrier");
	}
}
