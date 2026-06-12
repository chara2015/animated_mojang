package animated_mojang.client.internal;

final class VersionScenePolicy {
	private VersionScenePolicy() {
	}

	static boolean isInvisible(String state) {
		return state == null || state.startsWith("minecraft:air")
				|| state.startsWith("minecraft:cave_air")
				|| state.startsWith("minecraft:void_air")
				|| state.startsWith("minecraft:barrier");
	}
}
