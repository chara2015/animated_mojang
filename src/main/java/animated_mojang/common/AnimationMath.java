package animated_mojang.common;

public final class AnimationMath {
	private AnimationMath() {
	}

	public static float clamp(float value, float minimum, float maximum) {
		return Math.max(minimum, Math.min(maximum, value));
	}

	public static int clamp(int value, int minimum, int maximum) {
		return Math.max(minimum, Math.min(maximum, value));
	}

	public static float lerp(float progress, float start, float end) {
		return start + progress * (end - start);
	}

	public static float cubicBezier(float progress, float x1, float y1, float x2, float y2) {
		float x = clamp(progress, 0.0F, 1.0F);
		float lower = 0.0F;
		float upper = 1.0F;
		float parameter = x;
		for (int index = 0; index < 14; index++) {
			parameter = (lower + upper) * 0.5F;
			if (bezier(parameter, x1, x2) < x) {
				lower = parameter;
			} else {
				upper = parameter;
			}
		}
		return bezier(parameter, y1, y2);
	}

	private static float bezier(float parameter, float first, float second) {
		float inverse = 1.0F - parameter;
		return 3.0F * inverse * inverse * parameter * first
				+ 3.0F * inverse * parameter * parameter * second
				+ parameter * parameter * parameter;
	}
}
