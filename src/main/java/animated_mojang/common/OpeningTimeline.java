package animated_mojang.common;

public final class OpeningTimeline {
	public static final long DURATION_MILLIS = 4000L;
	public static final long MENU_REVEAL_MILLIS = 2700L;
	public static final long MENU_FADE_MILLIS = 500L;
	private static final float CURVE_X1 = 0.2F;
	private static final float CURVE_Y1 = 0.32F;
	private static final float CURVE_X2 = 0.2F;
	private static final float CURVE_Y2 = 1.0F;

	private OpeningTimeline() {
	}

	public static float progress(long elapsedMillis) {
		float raw = AnimationMath.clamp(elapsedMillis / (float) DURATION_MILLIS, 0.0F, 1.0F);
		return AnimationMath.cubicBezier(raw, CURVE_X1, CURVE_Y1, CURVE_X2, CURVE_Y2);
	}

	public static boolean shouldRevealMenu(long elapsedMillis) {
		return elapsedMillis >= MENU_REVEAL_MILLIS;
	}

	public static float menuFade(long elapsedMillis) {
		return AnimationMath.clamp(
				(elapsedMillis - MENU_REVEAL_MILLIS) / (float) MENU_FADE_MILLIS, 0.0F, 1.0F);
	}
}
