package animated_mojang.common;

public final class CameraProfiles {
	public static final CameraPose TITLE = new CameraPose(31.0F, 13.0F, 6.0F, 21.0F, -13.0F, 0.0F);
	public static final CameraPose SINGLEPLAYER = new CameraPose(23.0F, 17.0F, 15.0F, -31.0F, 7.0F, 5.0F);
	public static final CameraPose MULTIPLAYER = new CameraPose(23.0F, 13.0F, 15.0F, -31.0F, -13.0F, 5.0F);
	public static final CameraPose OPTIONS = new CameraPose(23.0F, 13.0F, 15.0F, -15.0F, -13.0F, -5.0F);
	public static final CameraPose ACCOUNT = new CameraPose(18.0F, 13.0F, 18.0F, 10.0F, 0.0F, 1.0F);
	public static final CameraPose DIRECT_CONNECT = new CameraPose(23.0F, 13.0F, 18.0F, -21.0F, -13.0F, 8.0F);
	public static final CameraPose OPENER_START = new CameraPose(17.0F, 13.0F, 82.0F, 0.0F, 0.0F, -90.0F);
	public static final CameraPose OPENER_TRANSFER = new CameraPose(20.0F, 12.8F, 44.0F, 8.0F, -5.0F, -10.0F);

	private CameraProfiles() {
	}
}
