package animated_mojang.client.internal;

import animated_mojang.common.CameraProfiles;
import animated_mojang.common.DynamicBackgroundScreens;
import animated_mojang.common.OpeningTimeline;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.DirectJoinServerScreen;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.GenericWaitingScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.ManageServerScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;

public final class VersionedTitleBackgroundController {
	private static final float[] TITLE_CAMERA = CameraProfiles.TITLE.toArray();
	private static final float[] SINGLEPLAYER_CAMERA = CameraProfiles.SINGLEPLAYER.toArray();
	private static final float[] MULTIPLAYER_CAMERA = CameraProfiles.MULTIPLAYER.toArray();
	private static final float[] OPTIONS_CAMERA = CameraProfiles.OPTIONS.toArray();
	private static final float[] DIRECT_CONNECT_CAMERA = CameraProfiles.DIRECT_CONNECT.toArray();
	private static final float[] OPENER_START_CAMERA = CameraProfiles.OPENER_START.toArray();
	private static final float[] OPENER_TRANSFER_CAMERA = CameraProfiles.OPENER_TRANSFER.toArray();
	private static final float[] currentCamera = TITLE_CAMERA.clone();
	private static final float[] transitionStartCamera = TITLE_CAMERA.clone();
	private static final float[] transitionTargetCamera = TITLE_CAMERA.clone();
	private static final long SCREEN_TRANSITION_MS = 500L;

	private static long openingStartedAt = -1L;
	private static long screenTransitionStartedAt = -1L;
	private static long screenTransitionDuration = SCREEN_TRANSITION_MS;
	private static long forcedTransitionDuration;
	private static Class<?> lastScreenClass = TitleScreen.class;
	private static boolean connectionFlowActive;

	private VersionedTitleBackgroundController() {
	}

	public static void startOpening() {
		if (openingStartedAt == -1L) {
			openingStartedAt = Util.getMillis();
		}
	}

	public static float animationTime() {
		return openingStartedAt == -1L
				? 0.0F
				: Math.max(0L, Util.getMillis() - openingStartedAt) / 1000.0F;
	}

	public static void renderWorldBackground() {
		Minecraft minecraft = Minecraft.getInstance();
		Screen screen = minecraft.screen;
		if (minecraft.getOverlay() != null || screen == null || openingStartedAt == -1L
				|| !usesDynamicBackground(screen)) {
			return;
		}

		long now = Util.getMillis();
		long elapsed = Math.max(0L, now - openingStartedAt);
		float animationTime = elapsed / 1000.0F;
		if (elapsed < OpeningTimeline.DURATION_MILLIS && screen instanceof TitleScreen) {
			float[] camera = openingCamera(OpeningTimeline.progress(elapsed));
			renderAt(camera, animationTime);
			return;
		}

		updateScreenCamera(now, screen);
		renderAt(currentCamera, animationTime);
	}

	private static void renderAt(float[] camera, float animationTime) {
		VersionedSchematicScene.get().renderDirectAt(camera[0], camera[1], camera[2], camera[3], camera[4], camera[5],
				animationTime, animationTime);
	}

	public static boolean usesDynamicBackground(Screen screen) {
		if (Minecraft.getInstance().level != null && !isTransitionScreen(screen)) {
			connectionFlowActive = false;
			return false;
		}
		updateConnectionFlow(screen);
		if (connectionFlowActive) {
			return true;
		}
		return screen instanceof TitleScreen || screen instanceof SelectWorldScreen || screen instanceof CreateWorldScreen
				|| screen instanceof JoinMultiplayerScreen || screen instanceof ManageServerScreen
				|| screen instanceof DirectJoinServerScreen || screen instanceof ConnectScreen
				|| screen instanceof DisconnectedScreen || isTransitionScreen(screen) || isOptionsScreen(screen)
				|| DynamicBackgroundScreens.matches(screen);
	}

	private static boolean isTransitionScreen(Screen screen) {
		return screen instanceof GenericMessageScreen || screen instanceof GenericWaitingScreen
				|| screen instanceof LevelLoadingScreen || screen instanceof ProgressScreen;
	}

	private static void updateScreenCamera(long now, Screen screen) {
		if (screen.getClass() != lastScreenClass) {
			updateCameraInterpolation(now);
			System.arraycopy(currentCamera, 0, transitionStartCamera, 0, currentCamera.length);
			float[] target = cameraForScreen(screen);
			System.arraycopy(target, 0, transitionTargetCamera, 0, target.length);
			screenTransitionStartedAt = now;
			long targetDuration = isLongScreenTransition(screen) ? 3000L : SCREEN_TRANSITION_MS;
			screenTransitionDuration = Math.max(targetDuration, forcedTransitionDuration);
			if (forcedTransitionDuration > 0L) {
				forcedTransitionDuration = 0L;
			}
			if (targetDuration >= 1000L) {
				forcedTransitionDuration = targetDuration;
			}
			lastScreenClass = screen.getClass();
		}
		updateCameraInterpolation(now);
	}

	private static void updateCameraInterpolation(long now) {
		if (screenTransitionStartedAt == -1L) {
			System.arraycopy(TITLE_CAMERA, 0, currentCamera, 0, currentCamera.length);
			return;
		}
		float progress = OpeningTimeline.progress(Math.round(Mth.clamp(
				(now - screenTransitionStartedAt) / (float) screenTransitionDuration, 0.0F, 1.0F)
				* OpeningTimeline.DURATION_MILLIS));
		for (int i = 0; i < currentCamera.length; i++) {
			currentCamera[i] = Mth.lerp(progress, transitionStartCamera[i], transitionTargetCamera[i]);
		}
	}

	private static float[] cameraForScreen(Screen screen) {
		if (connectionFlowActive) return OPENER_START_CAMERA;
		if (isSingleplayerFlowScreen(screen)) return SINGLEPLAYER_CAMERA;
		if (screen instanceof JoinMultiplayerScreen) return MULTIPLAYER_CAMERA;
		if (screen instanceof ManageServerScreen || screen instanceof DirectJoinServerScreen) return DIRECT_CONNECT_CAMERA;
		if (screen instanceof ConnectScreen) return OPENER_START_CAMERA;
		if (screen instanceof DisconnectedScreen) return OPENER_START_CAMERA;
		if (isOptionsScreen(screen)) return OPTIONS_CAMERA;
		if (screen instanceof TitleScreen) return TITLE_CAMERA;
		return transitionTargetCamera;
	}

	private static boolean isOptionsScreen(Screen screen) {
		return screen instanceof OptionsScreen || screen instanceof OptionsSubScreen || screen instanceof PackSelectionScreen;
	}

	private static boolean isSingleplayerFlowScreen(Screen screen) {
		String name = screen.getClass().getSimpleName();
		return screen instanceof SelectWorldScreen || screen instanceof CreateWorldScreen
				|| name.contains("Create") || name.contains("GameRules")
				|| name.contains("Experiments") || name.contains("DataPack");
	}

	private static boolean isLongScreenTransition(Screen screen) {
		return connectionFlowActive || screen instanceof ConnectScreen || screen instanceof DisconnectedScreen;
	}

	private static void updateConnectionFlow(Screen screen) {
		if (screen instanceof ConnectScreen) {
			connectionFlowActive = true;
			return;
		}
		if (connectionFlowActive && isExplicitConnectionExit(screen)) {
			connectionFlowActive = false;
		}
	}

	private static boolean isExplicitConnectionExit(Screen screen) {
		return screen instanceof TitleScreen || screen instanceof JoinMultiplayerScreen
				|| screen instanceof DirectJoinServerScreen || screen instanceof ManageServerScreen
				|| screen instanceof SelectWorldScreen || screen instanceof CreateWorldScreen
				|| isOptionsScreen(screen);
	}

	private static float[] openingCamera(float progress) {
		float[] result = new float[OPENER_START_CAMERA.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = naturalSpline3(OPENER_START_CAMERA[i], OPENER_TRANSFER_CAMERA[i], TITLE_CAMERA[i], progress);
		}
		return result;
	}

	private static float naturalSpline3(float first, float middle, float last, float progress) {
		float gamma0 = 0.5F;
		float gamma1 = 1.0F / (4.0F - gamma0);
		float gamma2 = 1.0F / (2.0F - gamma1);
		float delta0 = 3.0F * (middle - first) * gamma0;
		float delta1 = (3.0F * (last - first) - delta0) * gamma1;
		float delta2 = (3.0F * (last - middle) - delta1) * gamma2;
		float d2 = delta2;
		float d1 = delta1 - gamma1 * d2;
		float d0 = delta0 - gamma0 * d1;
		float segmentProgress = progress * 2.0F;
		if (segmentProgress < 1.0F) {
			return evaluateCubic(first, d0, 3.0F * (middle - first) - 2.0F * d0 - d1,
					2.0F * (first - middle) + d0 + d1, segmentProgress);
		}
		float u = segmentProgress - 1.0F;
		return evaluateCubic(middle, d1, 3.0F * (last - middle) - 2.0F * d1 - d2,
				2.0F * (middle - last) + d1 + d2, u);
	}

	private static float evaluateCubic(float a, float b, float c, float d, float progress) {
		return ((d * progress + c) * progress + b) * progress + a;
	}
}
