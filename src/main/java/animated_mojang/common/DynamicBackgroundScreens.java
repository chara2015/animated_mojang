package animated_mojang.common;

public final class DynamicBackgroundScreens {
	private static final String[] MATCHES = {
			"Title", "World", "Multiplayer", "Server", "Options", "Pack",
			"Connect", "Disconnected", "Loading", "Progress", "Waiting",
			"Message", "ReceivingLevel", "DownloadingTerrain", "Working",
			"Warning", "Confirm", "Notice", "Safety", "Realms", "Mod", "Config",
			"Create", "GameRules", "Experiments", "DataPack"
	};

	private DynamicBackgroundScreens() {
	}

	public static boolean matches(Object screen) {
		if (screen == null) {
			return false;
		}
		Class<?> screenClass = screen.getClass();
		String name = screenClass.getSimpleName();
		for (String match : MATCHES) {
			if (name.contains(match)) {
				return true;
			}
		}
		String fullName = screenClass.getName();
		return fullName.contains(".realms") || fullName.contains(".modmenu")
				|| fullName.contains("ModMenu") || fullName.contains("ConfigScreen");
	}
}
