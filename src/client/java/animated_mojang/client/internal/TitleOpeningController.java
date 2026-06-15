package animated_mojang.client.internal;

import animated_mojang.common.CameraProfiles;
import animated_mojang.common.DynamicBackgroundScreens;
import animated_mojang.common.OpeningTimeline;
import animated_mojang.config.AnimatedMojangConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
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

public final class TitleOpeningController {
	private static final long OPENING_MS = OpeningTimeline.DURATION_MILLIS;
	private static final long MENU_REVEAL_MS = OpeningTimeline.MENU_REVEAL_MILLIS;
	private static final float CURVE_X1 = 0.2F;
	private static final float CURVE_Y1 = 0.32F;
	private static final float CURVE_X2 = 0.2F;
	private static final float CURVE_Y2 = 1.0F;
	private static final int VOID = 0xFF08070B;
	private static final int BACK_STONE = 0xFF141218;
	private static final int MID_STONE = 0xFF242027;
	private static final int FRONT_STONE = 0xFF3A3335;
	private static final int WOOD_DARK = 0xFF4B3521;
	private static final int WOOD_LIGHT = 0xFF725034;
	private static final int LAVA = 0xFFFF7A1A;
	private static final int MOJANG_RED = 0xFFEF323D;

	private static boolean openingPlayed;
	private static long openingStartedAt = -1L;
	private static final float[] TITLE_CAMERA = CameraProfiles.TITLE.toArray();
	private static final float[] SINGLEPLAYER_CAMERA = CameraProfiles.SINGLEPLAYER.toArray();
	private static final float[] MULTIPLAYER_CAMERA = CameraProfiles.MULTIPLAYER.toArray();
	private static final float[] OPTIONS_CAMERA = CameraProfiles.OPTIONS.toArray();
	private static final float[] ACCOUNT_CAMERA = CameraProfiles.ACCOUNT.toArray();
	private static final float[] DIRECT_CONNECT_CAMERA = CameraProfiles.DIRECT_CONNECT.toArray();
	private static final float[] OPENER_START_CAMERA = CameraProfiles.OPENER_START.toArray();
	private static final float[] OPENER_TRANSFER_CAMERA = CameraProfiles.OPENER_TRANSFER.toArray();
	private static final long SCREEN_TRANSITION_MS = 500L;
	private static final float[] currentCamera = TITLE_CAMERA.clone();
	private static final float[] transitionStartCamera = TITLE_CAMERA.clone();
	private static final float[] transitionTargetCamera = TITLE_CAMERA.clone();
	private static Class<?> lastScreenClass = TitleScreen.class;
	private static long screenTransitionStartedAt = -1L;
	private static long screenTransitionDuration = SCREEN_TRANSITION_MS;
	private static long forcedTransitionDuration;
	private static boolean connectionFlowActive;
	private static boolean wasInWorld;

	private TitleOpeningController() {
	}

	public static void playOpening() {
		if (openingPlayed) {
			return;
		}
		AnimatedMinecraftTitle.preload();
		openingPlayed = true;
		openingStartedAt = Util.getMillis();
	}

	public static boolean shouldBlockEarlyInput() {
		return shouldHideTitleWidgets();
	}

	public static boolean shouldHideTitleWidgets() {
		return AnimatedMojangConfig.isMinecraftTitleAnimationEnabled() && isOpeningPlaying()
				&& getOpeningElapsedMillis() < MENU_REVEAL_MS;
	}

	public static boolean shouldRenderOpeningBackground() {
		return openingPlayed;
	}

	public static boolean shouldSuppressMenuMusic() {
		return !openingPlayed;
	}

	public static boolean shouldRenderDynamicScreenBackground() {
		return openingPlayed && usesDynamicBackground(Minecraft.getInstance().screen);
	}

	public static boolean usesDynamicBackground(Screen screen) {
		if (Minecraft.getInstance().level != null && !isTransitionScreen(screen)) {
			connectionFlowActive = false;
			wasInWorld = true;
			return false;
		}
		if (wasInWorld) {
			forcedTransitionDuration = 3000L;
			wasInWorld = false;
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

	public static long getOpeningElapsedMillis() {
		return openingStartedAt == -1L ? OPENING_MS : Math.max(0L, Util.getMillis() - openingStartedAt);
	}

	public static void renderAnimatedMinecraftTitle(GuiGraphicsExtractor graphics, float alpha) {
		if (openingPlayed && AnimatedMojangConfig.isMinecraftTitleAnimationEnabled()) {
			AnimatedMinecraftTitle.render(graphics, alpha);
		}
	}

	public static void renderHiddenTitleOverlay(GuiGraphicsExtractor graphics) {
		renderBackground(graphics);
		renderAnimatedMinecraftTitle(graphics, 1.0F);
		renderFadeCover(graphics);
	}

	public static void renderMinecraftEditionWithMenu(GuiGraphicsExtractor graphics, float alpha) {
		if (openingPlayed && AnimatedMojangConfig.isMinecraftTitleAnimationEnabled() && !shouldHideTitleWidgets()) {
			AnimatedMinecraftTitle.renderEdition(graphics,
					alpha * OpeningTimeline.menuFade(getOpeningElapsedMillis()));
		}
	}

	public static void renderBackground(GuiGraphicsExtractor graphics) {
		int width = graphics.guiWidth();
		int height = graphics.guiHeight();
		long now = Util.getMillis();
		long elapsed = openingPlayed ? getOpeningElapsedMillis() : OPENING_MS;
		float rawProgress = Mth.clamp(elapsed / (float) OPENING_MS, 0.0F, 1.0F);
		float camera = cubicBezier(rawProgress, CURVE_X1, CURVE_Y1, CURVE_X2, CURVE_Y2);
		float time = elapsed / 1000.0F;

		renderCave(graphics, width, height, camera, time);
	}

	public static void renderDynamicScreenEffects(GuiGraphicsExtractor graphics) {
		if (!shouldRenderDynamicScreenBackground() || Minecraft.getInstance().screen instanceof TitleScreen) {
			return;
		}
		float time = getOpeningElapsedMillis() / 1000.0F;
		SchematicScene.get().renderTorchEffects(graphics, 1.0F, time, time);
	}

	public static void renderWorldBackground() {
		if (!openingPlayed) {
			return;
		}
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.screen == null || !shouldRenderDynamicScreenBackground()) {
			return;
		}

		long now = Util.getMillis();
		long elapsed = Math.max(0L, now - openingStartedAt);
		float animationTime = elapsed / 1000.0F;
		if (elapsed < OPENING_MS && minecraft.screen instanceof TitleScreen) {
			float rawProgress = Mth.clamp(elapsed / (float) OPENING_MS, 0.0F, 1.0F);
			float progress = cubicBezier(rawProgress, CURVE_X1, CURVE_Y1, CURVE_X2, CURVE_Y2);
			float[] camera = openingCamera(progress);
			SchematicScene.get().renderDirectAt(camera[0], camera[1], camera[2], camera[3], camera[4], camera[5],
					animationTime, animationTime);
			return;
		}

		updateScreenCamera(now, minecraft.screen);
		SchematicScene.get().renderDirectAt(currentCamera[0], currentCamera[1], currentCamera[2], currentCamera[3],
				currentCamera[4], currentCamera[5], animationTime, animationTime);
	}

	private static void updateScreenCamera(long now, Screen screen) {
		if (screen.getClass() != lastScreenClass) {
			updateCameraInterpolation(now, screen);
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
		updateCameraInterpolation(now, screen);
	}

	private static void updateCameraInterpolation(long now, Screen screen) {
		if (screenTransitionStartedAt == -1L) {
			System.arraycopy(TITLE_CAMERA, 0, currentCamera, 0, currentCamera.length);
			return;
		}
		float raw = Mth.clamp((now - screenTransitionStartedAt) / (float) screenTransitionDuration, 0.0F, 1.0F);
		float progress = cubicBezier(raw, CURVE_X1, CURVE_Y1, CURVE_X2, CURVE_Y2);
		for (int i = 0; i < currentCamera.length; i++) {
			currentCamera[i] = Mth.lerp(progress, transitionStartCamera[i], transitionTargetCamera[i]);
		}
	}

	private static float[] cameraForScreen(Screen screen) {
		if (connectionFlowActive) return OPENER_START_CAMERA;
		if (screen instanceof SelectWorldScreen || screen instanceof CreateWorldScreen) return SINGLEPLAYER_CAMERA;
		if (screen instanceof JoinMultiplayerScreen) return MULTIPLAYER_CAMERA;
		if (screen instanceof ManageServerScreen || screen instanceof DirectJoinServerScreen) return DIRECT_CONNECT_CAMERA;
		if (screen instanceof ConnectScreen) return OPENER_START_CAMERA;
		if (screen instanceof DisconnectedScreen) return OPENER_START_CAMERA;
		if (DynamicBackgroundScreens.isAccountScreen(screen)) return ACCOUNT_CAMERA;
		if (DynamicBackgroundScreens.isReplayScreen(screen)) return SINGLEPLAYER_CAMERA;
		if (isOptionsScreen(screen)) return OPTIONS_CAMERA;
		if (screen instanceof TitleScreen) return TITLE_CAMERA;
		return transitionTargetCamera;
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

	public static boolean isOptionsScreen(Screen screen) {
		return screen instanceof OptionsScreen || screen instanceof OptionsSubScreen || screen instanceof PackSelectionScreen;
	}

	private static boolean isTransitionScreen(Screen screen) {
		return screen instanceof GenericMessageScreen || screen instanceof GenericWaitingScreen
				|| screen instanceof LevelLoadingScreen || screen instanceof ProgressScreen;
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

	public static void renderFadeCover(GuiGraphicsExtractor graphics) {
		if (!openingPlayed) {
			return;
		}

		float alpha = Math.max(0.0F, 0.48F - getTransitionProgress() * 0.52F);
		if (alpha > 0.0F) {
			graphics.fill(0, 0, graphics.guiWidth(), graphics.guiHeight(), packAlpha(0x000000, alpha));
		}
	}

	private static void renderCave(GuiGraphicsExtractor graphics, int width, int height, float camera, float time) {
		float openingTime = openingPlayed ? Math.max(0L, Util.getMillis() - openingStartedAt) / 1000.0F : 4.0F;
		SchematicScene.get().renderTorchEffects(graphics, camera, time, openingTime);
		drawParticles(graphics, width, height, camera, time);
	}

	private static void drawHeroCave(GuiGraphicsExtractor graphics, int width, int height, float camera, float time) {
		float push = camera * 0.08F;
		drawBackChamber(graphics, width, height, camera, time);
		drawLargeRock(graphics, width, height, 0.07F - push, 0.08F, 0.20F, 0.86F, 0xFF1B2630, 0.96F);
		drawLargeRock(graphics, width, height, 0.77F + push, 0.04F, 0.22F, 0.92F, 0xFF231B22, 0.96F);
		drawLargeRock(graphics, width, height, 0.28F - push, 0.15F, 0.24F, 0.70F, 0xFF17151B, 0.86F);
		drawLargeRock(graphics, width, height, 0.52F, 0.12F, 0.22F, 0.58F, 0xFF201920, 0.82F);
		drawLargeRock(graphics, width, height, 0.63F + push, 0.18F, 0.18F, 0.66F, 0xFF281C20, 0.88F);
		drawMiningDetails(graphics, width, height, camera, time);
		drawLava(graphics, width, height, camera, time);
		drawForegroundPlatforms(graphics, width, height, camera);
	}

	private static void drawLargeRock(GuiGraphicsExtractor graphics, int width, int height, float rx, float ry,
			float rw, float rh, int color, float alpha) {
		int x = Math.round(width * rx);
		int y = Math.round(height * ry);
		int w = Math.round(width * rw);
		int h = Math.round(height * rh);
		graphics.fill(x, y, x + w, y + h, packAlpha(color & 0xFFFFFF, alpha));
		graphics.fill(x, y, x + Math.max(2, w / 9), y + h, packAlpha(0x07080D, 0.32F));
		graphics.fill(x, y + h - Math.max(2, h / 12), x + w, y + h, packAlpha(0x030306, 0.30F));
		graphics.fill(x + w / 6, y + h / 5, x + w / 2, y + h / 5 + Math.max(2, h / 18), packAlpha(0xB07A43, 0.08F));
	}

	private static void drawMiningDetails(GuiGraphicsExtractor graphics, int width, int height, float camera, float time) {
		drawTorch(graphics, Math.round(width * 0.10F), Math.round(height * 0.52F), Math.max(1.0F, height / 260.0F), time);
		drawTorch(graphics, Math.round(width * 0.50F), Math.round(height * 0.58F), Math.max(0.8F, height / 330.0F), time + 2.0F);
		drawTorch(graphics, Math.round(width * 0.76F), Math.round(height * 0.55F), Math.max(0.85F, height / 320.0F), time + 4.0F);
		drawMineshaft(graphics, width, height, camera, time);
		int oreSize = Math.max(2, width / 160);
		for (int i = 0; i < 10; i++) {
			int x = Math.round(width * (0.055F + (i % 3) * 0.025F));
			int y = Math.round(height * (0.42F + (i / 3) * 0.028F));
			graphics.fill(x, y, x + oreSize, y + oreSize, packAlpha(0xBFE8D7, 0.72F));
		}
	}

	private static void drawForegroundPlatforms(GuiGraphicsExtractor graphics, int width, int height, float camera) {
		int floorY = Math.round(height * 0.77F);
		graphics.fill(0, floorY, width, height, 0xFF111016);
		graphics.fill(Math.round(width * 0.55F), Math.round(height * 0.62F), width, Math.round(height * 0.68F), 0xFF49301E);
		graphics.fill(Math.round(width * 0.70F), Math.round(height * 0.68F), width, Math.round(height * 0.72F), 0xFF241913);
		graphics.fill(0, Math.round(height * 0.72F), Math.round(width * 0.28F), height, 0xFF161B22);
		graphics.fill(0, Math.round(height * 0.72F), Math.round(width * 0.28F), Math.round(height * 0.735F), 0xFF31343B);
		int chestX = Math.round(width * 0.92F);
		int chestY = Math.round(height * 0.67F);
		int chest = Math.max(8, width / 46);
		graphics.fill(chestX, chestY, chestX + chest, chestY + chest, 0xFFB96322);
		graphics.fill(chestX + chest / 2 - 1, chestY, chestX + chest / 2 + 1, chestY + chest, 0xFF3E2414);
	}

	private static void drawBackChamber(GuiGraphicsExtractor graphics, int width, int height, float camera, float time) {
		int centerX = Math.round(width * (0.50F - camera * 0.03F));
		int centerY = Math.round(height * 0.45F);
		int chamberWidth = Math.round(width * (0.56F + camera * 0.10F));
		int chamberHeight = Math.round(height * 0.54F);
		graphics.fill(centerX - chamberWidth / 2, centerY - chamberHeight / 2, centerX + chamberWidth / 2,
				centerY + chamberHeight / 2, 0xFF101018);
		graphics.fill(centerX - chamberWidth / 3, centerY - chamberHeight / 6, centerX + chamberWidth / 3,
				centerY + chamberHeight / 3, 0xFF1C151A);
	}

	private static void drawStoneLayer(GuiGraphicsExtractor graphics, int width, int height, float camera, float time,
			float depth, int baseColor, float alpha) {
		int tile = Math.max(18, Math.round(height * (0.045F + depth * 0.04F)));
		int columns = width / tile + 12;
		int rows = height / tile + 8;
		int offsetX = Math.round(camera * width * (0.28F + depth * 0.52F) + Mth.sin(time * (0.1F + depth)) * tile);
		int offsetY = Math.round(camera * height * (0.1F + depth * 0.24F));

		for (int row = -4; row < rows; row++) {
			for (int column = -6; column < columns; column++) {
				int hash = hash(column, row + Math.round(depth * 100.0F));
				int x = column * tile - Math.floorMod(offsetX, tile * 2);
				int y = row * tile - Math.floorMod(offsetY, tile * 2);
				boolean edge = y < height * 0.3F || y > height * 0.72F;
				if (!edge && (hash & 3) != 0) {
					continue;
				}

				int inset = Math.max(1, tile / 18);
				int color = shadeColor(baseColor & 0xFFFFFF, -18 + ((hash >>> 4) & 31));
				float blockAlpha = alpha * (edge ? 1.0F : 0.44F);
				graphics.fill(x, y, x + tile - inset, y + tile - inset, packAlpha(color, blockAlpha));
				graphics.fill(x, y + tile - inset * 3, x + tile - inset, y + tile - inset,
						packAlpha(0x060508, blockAlpha * 0.35F));
			}
		}
	}

	private static void drawMineshaft(GuiGraphicsExtractor graphics, int width, int height, float camera, float time) {
		int vanishingX = Math.round(width * (0.52F - camera * 0.08F));
		int top = Math.round(height * (0.32F + camera * 0.04F));
		int beam = Math.max(5, height / 80);
		int span = Math.round(width * (0.13F + camera * 0.08F));

		for (int i = 0; i < 6; i++) {
			float z = i / 5.0F;
			float scale = 0.45F + z * 1.25F + camera * 0.4F;
			int x = Math.round(vanishingX + (i - 2.2F) * width * 0.085F - camera * width * 0.23F);
			int yTop = Math.round(top + (1.0F - z) * height * 0.05F);
			int beamHeight = Math.round(height * 0.22F * scale);
			int postWidth = Math.max(beam, Math.round(beam * scale));
			int halfSpan = Math.round(span * scale);

			drawWood(graphics, x - halfSpan, yTop, x - halfSpan + postWidth, yTop + beamHeight, z);
			drawWood(graphics, x + halfSpan - postWidth, yTop, x + halfSpan, yTop + beamHeight, z);
			drawWood(graphics, x - halfSpan, yTop, x + halfSpan, yTop + postWidth, z);
			if ((i & 1) == 0) {
				drawTorch(graphics, x + halfSpan - postWidth * 3, yTop + beamHeight / 2, scale, time + i);
			}
		}
	}

	private static void drawLava(GuiGraphicsExtractor graphics, int width, int height, float camera, float time) {
		float flicker = 0.74F + Mth.sin(time * 5.1F) * 0.08F + Mth.sin(time * 9.4F) * 0.05F;
		int lavaY = Math.round(height * (0.74F + camera * 0.09F));
		int lavaX = Math.round(width * (0.42F - camera * 0.18F));
		int lavaWidth = Math.round(width * (0.28F + camera * 0.22F));
		int lavaHeight = Math.max(8, height / 32);
		graphics.fill(lavaX, lavaY, lavaX + lavaWidth, lavaY + lavaHeight, packAlpha(LAVA & 0xFFFFFF, flicker));
		graphics.fill(lavaX - lavaWidth / 5, lavaY - lavaHeight * 4, lavaX + lavaWidth + lavaWidth / 4,
				lavaY + lavaHeight * 6, packAlpha(0xE36824, 0.15F * flicker));
	}

	private static void drawParticles(GuiGraphicsExtractor graphics, int width, int height, float camera, float time) {
		for (int i = 0; i < 28; i++) {
			int hash = hash(i, 311);
			float speed = 0.04F + ((hash >>> 6) & 15) / 180.0F;
			float drift = Math.floorMod((int) ((time * speed + i * 0.137F) * 1000.0F), 1000) / 1000.0F;
			int x = Math.round(width * (((hash & 255) / 255.0F + camera * 0.18F) % 1.0F));
			int y = Math.round(height * (0.24F + drift * 0.55F));
			int size = 1 + ((hash >>> 12) & 1);
			graphics.fill(x, y, x + size, y + size, packAlpha(0xF7B96A, 0.16F + camera * 0.18F));
		}
	}

	private static void renderOpeningEcho(GuiGraphicsExtractor graphics, int width, int height, float rawProgress,
			float camera) {
		if (!openingPlayed || rawProgress >= 1.0F) {
			return;
		}

		float redAlpha = Mth.clamp(1.0F - rawProgress * 1.65F, 0.0F, 1.0F);
		if (redAlpha > 0.0F) {
			graphics.fill(0, 0, width, height, packAlpha(MOJANG_RED & 0xFFFFFF, redAlpha));
		}

		float wipe = Mth.clamp((rawProgress - 0.16F) / 0.56F, 0.0F, 1.0F);
		if (wipe > 0.0F && wipe < 1.0F) {
			int revealWidth = Math.round(width * (0.16F + wipe * 1.18F));
			int revealHeight = Math.round(height * (0.18F + wipe * 1.04F));
			int left = Math.round(-width * 0.08F + camera * width * 0.12F);
			int top = Math.round(height * (0.84F - wipe * 0.9F));
			graphics.fill(left, top, left + revealWidth, top + revealHeight, packAlpha(0x000000, 0.34F * (1.0F - wipe)));
		}
	}

	private static void drawTorch(GuiGraphicsExtractor graphics, int x, int y, float scale, float time) {
		int stick = Math.max(3, Math.round(4.0F * scale));
		int flame = Math.max(8, Math.round(11.0F * scale));
		float flicker = 0.8F + Mth.sin(time * 5.0F) * 0.14F;
		graphics.fill(x - stick / 2, y, x + stick / 2 + 1, y + flame * 2, 0xFF3D2516);
		graphics.fill(x - flame, y - flame, x + flame, y + flame, packAlpha(0xFF9824, 0.24F * flicker));
		graphics.fill(x - flame / 2, y - flame / 2, x + flame / 2, y + flame / 2, packAlpha(0xFFD15A, 0.86F * flicker));
	}

	private static void drawWood(GuiGraphicsExtractor graphics, int x1, int y1, int x2, int y2, float depth) {
		int color = depth > 0.55F ? WOOD_LIGHT : WOOD_DARK;
		graphics.fill(x1, y1, x2, y2, color);
		int stripe = Math.max(2, Math.min(Math.abs(x2 - x1), Math.abs(y2 - y1)) / 3);
		graphics.fill(x1, y1, x2, Math.min(y2, y1 + stripe), packAlpha(0xA77A4D, 0.22F));
		graphics.fill(x1, Math.max(y1, y2 - stripe), x2, y2, packAlpha(0x1D120B, 0.28F));
	}

	private static void drawVignette(GuiGraphicsExtractor graphics, int width, int height) {
		int bandX = Math.max(12, width / 16);
		int bandY = Math.max(10, height / 12);
		graphics.fill(0, 0, width, bandY, packAlpha(0x000000, 0.32F));
		graphics.fill(0, height - bandY, width, height, packAlpha(0x000000, 0.42F));
		graphics.fill(0, 0, bandX, height, packAlpha(0x000000, 0.34F));
		graphics.fill(width - bandX, 0, width, height, packAlpha(0x000000, 0.34F));
	}

	private static boolean isOpeningPlaying() {
		return openingPlayed && Util.getMillis() - openingStartedAt < OPENING_MS;
	}

	private static float getTransitionProgress() {
		if (!openingPlayed || openingStartedAt == -1L) {
			return 1.0F;
		}

		float timePassed = Math.max(0L, Util.getMillis() - openingStartedAt);
		float cameraProgress = cubicBezier(Mth.clamp(timePassed / (float) OPENING_MS, 0.0F, 1.0F),
				CURVE_X1, CURVE_Y1, CURVE_X2, CURVE_Y2);
		float fadeProgress = (float) ((Math.exp((timePassed / 1000.0F) - 4.0F) * 10.0D) - 0.2D);
		return Math.max(cameraProgress, Mth.clamp(fadeProgress, 0.0F, 1.0F));
	}

	private static float cubicBezier(float x, float x1, float y1, float x2, float y2) {
		float lower = 0.0F;
		float upper = 1.0F;
		float t = x;
		for (int i = 0; i < 12; i++) {
			t = (lower + upper) * 0.5F;
			float estimate = bezier(t, x1, x2);
			if (estimate < x) {
				lower = t;
			} else {
				upper = t;
			}
		}
		return bezier(t, y1, y2);
	}

	private static float bezier(float t, float a, float b) {
		float inverse = 1.0F - t;
		return (3.0F * inverse * inverse * t * a) + (3.0F * inverse * t * t * b) + (t * t * t);
	}

	private static int packAlpha(int color, float alpha) {
		return Math.round(Mth.clamp(alpha, 0.0F, 1.0F) * 255.0F) << 24 | color & 0xFFFFFF;
	}

	private static int shadeColor(int color, int amount) {
		int red = Mth.clamp(((color >> 16) & 255) + amount, 0, 255);
		int green = Mth.clamp(((color >> 8) & 255) + amount, 0, 255);
		int blue = Mth.clamp((color & 255) + amount, 0, 255);
		return red << 16 | green << 8 | blue;
	}

	private static int hash(int x, int y) {
		int value = x * 374761393 + y * 668265263;
		value = (value ^ (value >>> 13)) * 1274126177;
		return value ^ (value >>> 16);
	}
}
