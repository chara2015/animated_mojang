package animated_mojang.common;

public record CameraPose(float x, float y, float z, float yaw, float pitch, float roll) {
	public static CameraPose interpolate(CameraPose start, CameraPose end, float progress) {
		return new CameraPose(
				AnimationMath.lerp(progress, start.x, end.x),
				AnimationMath.lerp(progress, start.y, end.y),
				AnimationMath.lerp(progress, start.z, end.z),
				AnimationMath.lerp(progress, start.yaw, end.yaw),
				AnimationMath.lerp(progress, start.pitch, end.pitch),
				AnimationMath.lerp(progress, start.roll, end.roll));
	}

	public float[] toArray() {
		return new float[] { x, y, z, yaw, pitch, roll };
	}
}
