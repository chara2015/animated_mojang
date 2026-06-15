package animated_mojang.client.legacy;

import animated_mojang.ModMetadata;
import animated_mojang.common.AnimationMath;
import animated_mojang.common.OpeningTimeline;
import animated_mojang.config.AnimatedMojangConfig;
import net.minecraft.client.gui.GuiGraphics;

public final class LegacyAnimations {
	private static final int MOJANG_FRAME_COUNT = 79;
	private static final int MOJANG_FRAME_TIME_MILLIS = 40;
	private static final int MOJANG_FRAME_WIDTH = 600;
	private static final int MOJANG_FRAME_HEIGHT = 129;
	private static final int MOJANG_ATLAS_COLUMNS = 10;
	private static final int MOJANG_ATLAS_WIDTH = MOJANG_FRAME_WIDTH * MOJANG_ATLAS_COLUMNS;
	private static final int MOJANG_ATLAS_HEIGHT = MOJANG_FRAME_HEIGHT * 8;
	private static final int MOJANG_SOURCE_X = 31;
	private static final int MOJANG_SOURCE_Y = 31;
	private static final int MOJANG_SOURCE_WIDTH = 540;
	private static final int MOJANG_SOURCE_HEIGHT = 83;
	private static final float MOJANG_SOURCE_ASPECT = MOJANG_SOURCE_WIDTH / (float) MOJANG_SOURCE_HEIGHT;
	private static final int MOJANG_SOUND_DELAY_MILLIS = 350;
	private static final int MOJANG_SOUND_RETRY_MILLIS = 200;
	private static final int MOJANG_SOUND_LENGTH_MILLIS = 4066;
	private static final int MOJANG_AUDIO_TAIL_MILLIS = 450;
	private static final int MOJANG_TOTAL_MILLIS =
			MOJANG_SOUND_DELAY_MILLIS + MOJANG_SOUND_LENGTH_MILLIS + MOJANG_AUDIO_TAIL_MILLIS;
	private static final int STUDIOS_FADE_MILLIS = 400;
	private static final int STUDIOS_SOURCE_X = 30;
	private static final int STUDIOS_SOURCE_Y = 18;
	private static final int STUDIOS_SOURCE_WIDTH = 506;
	private static final int STUDIOS_SOURCE_HEIGHT = 56;
	private static final float STUDIOS_SOURCE_ASPECT = STUDIOS_SOURCE_WIDTH / (float) STUDIOS_SOURCE_HEIGHT;
	private static final int TITLE_FRAME_COUNT = 69;
	private static final int TITLE_FRAMES_PER_TEXTURE = 8;
	private static final int TITLE_FRAME_TIME_MILLIS = 40;
	private static final int TITLE_DELAY_MILLIS = 1880;
	private static final int TITLE_SOURCE_WIDTH = 2403;
	private static final int TITLE_SOURCE_FRAME_HEIGHT = 749;
	private static final int TITLE_SOURCE_TEXTURE_HEIGHT = TITLE_SOURCE_FRAME_HEIGHT * TITLE_FRAMES_PER_TEXTURE;
	private static final int TITLE_CONTENT_X = 518;
	private static final int TITLE_CONTENT_Y = 502;
	private static final int TITLE_CONTENT_WIDTH = 1373;
	private static final int VANILLA_TITLE_WIDTH = 256;
	private static final int VANILLA_TITLE_HEIGHT = 44;
	private static final int VANILLA_EDITION_WIDTH = 128;
	private static final int VANILLA_EDITION_HEIGHT = 14;
	private static final int VANILLA_EDITION_OVERLAP = 7;
	private static final int VANILLA_TITLE_TOP = 30;
	private static final int LIGHT_LOADING_BACKGROUND = 0xFFEF323D;
	private static final Object MOJANG_TEXTURE = LegacyRenderBridge.location(
			ModMetadata.MOD_ID, "textures/gui/mojang_animation.png");
	private static final Object STUDIOS_TEXTURE = LegacyRenderBridge.location(
			ModMetadata.MOD_ID, "textures/gui/studios.png");
	private static final Object MINECRAFT_EDITION = LegacyRenderBridge.location(
			"minecraft", "textures/gui/title/edition.png");
	private static final Object CAVE_PARTICLES = LegacyRenderBridge.location(
			ModMetadata.MOD_ID, "textures/gui/title/cave_particles_clean.png");
	private static final int PARTICLE_ATLAS_SIZE = 128;
	private static final Object[] TITLE_TEXTURES = new Object[9];
	private static long titleStartedAt = -1L;
	private static long loadingStartedAt = -1L;
	private static boolean loadingSoundPlayed;
	private static long lastLoadingSoundAttemptAt = -1L;
	private static long loadingSoundStartedAt = -1L;
	private static boolean menuMusicSuppressed;

	static {
		for (int index = 0; index < TITLE_TEXTURES.length; index++) {
			TITLE_TEXTURES[index] = LegacyRenderBridge.location(
					ModMetadata.MOD_ID, "textures/gui/title/minecraft_animation/minecraft_" + index + ".png");
		}
	}

	private LegacyAnimations() {
	}

	public static void startTitle() {
		if (titleStartedAt == -1L) {
			titleStartedAt = System.currentTimeMillis();
			LegacyIdentifierBridge.startWorldBackground();
		}
	}

	public static void restartTitle() {
		titleStartedAt = System.currentTimeMillis();
	}

	public static void beginLoading() {
		LegacyIdentifierBridge.preload();
		loadingStartedAt = System.currentTimeMillis();
		loadingSoundPlayed = false;
		lastLoadingSoundAttemptAt = -1L;
		loadingSoundStartedAt = -1L;
		titleStartedAt = -1L;
		menuMusicSuppressed = false;
	}

	public static void finishLoading() {
		if (titleStartedAt == -1L) {
			restartTitle();
			LegacyIdentifierBridge.startWorldBackground();
		}
	}

	public static boolean hasLoadingAnimationStarted() {
		return loadingStartedAt != -1L;
	}

	public static boolean isLoadingAnimationFinished() {
		if (loadingStartedAt == -1L) {
			return false;
		}
		long elapsed = System.currentTimeMillis() - loadingStartedAt;
		long soundEnd = loadingSoundPlayed
				? loadingSoundStartedAt - loadingStartedAt + MOJANG_SOUND_LENGTH_MILLIS + MOJANG_AUDIO_TAIL_MILLIS
				: MOJANG_TOTAL_MILLIS;
		long studiosEnd = MOJANG_FRAME_COUNT * MOJANG_FRAME_TIME_MILLIS + STUDIOS_FADE_MILLIS;
		return elapsed >= Math.max(Math.max(MOJANG_TOTAL_MILLIS, soundEnd), studiosEnd);
	}

	public static boolean shouldHideTitleWidgets() {
		return titleStartedAt != -1L && !OpeningTimeline.shouldRevealMenu(titleElapsed());
	}

	public static boolean shouldHideThirdPartyTitleWidgets() {
		return shouldHideTitleWidgets();
	}

	public static boolean shouldHidePostTitleOverlays() {
		return titleStartedAt != -1L
				&& titleElapsed() < OpeningTimeline.MENU_REVEAL_MILLIS + OpeningTimeline.MENU_FADE_MILLIS;
	}

	public static float getTitleWidgetAlpha() {
		if (!AnimatedMojangConfig.isMinecraftTitleAnimationEnabled() || titleStartedAt == -1L) {
			return 1.0F;
		}
		return OpeningTimeline.menuFade(titleElapsed());
	}

	public static void suppressMenuMusicUntilWidgetsAppear() {
		if (!menuMusicSuppressed
				&& loadingStartedAt != -1L && !isLoadingAnimationFinished()) {
			LegacyIdentifierBridge.suppressMenuMusic();
			menuMusicSuppressed = true;
		}
	}

	public static boolean shouldSuppressMenuMusic() {
		return AnimatedMojangConfig.isMinecraftTitleAnimationEnabled() && titleStartedAt == -1L;
	}

	public static void renderLoading(GuiGraphics graphics) {
		if (loadingStartedAt == -1L) {
			beginLoading();
		}
		long elapsed = Math.max(0L, System.currentTimeMillis() - loadingStartedAt);
		suppressMenuMusicUntilWidgetsAppear();
		if (!loadingSoundPlayed && elapsed >= MOJANG_SOUND_DELAY_MILLIS
				&& elapsed - lastLoadingSoundAttemptAt >= MOJANG_SOUND_RETRY_MILLIS) {
			lastLoadingSoundAttemptAt = elapsed;
			loadingSoundPlayed = LegacyIdentifierBridge.playStartupSound();
			if (loadingSoundPlayed) {
				loadingSoundStartedAt = System.currentTimeMillis();
			}
		}
		int frame = Math.min((int) (elapsed / MOJANG_FRAME_TIME_MILLIS), MOJANG_FRAME_COUNT - 1);
		int vanillaHeight = Math.max(1, (int) (Math.min(graphics.guiWidth() * 0.75F, graphics.guiHeight()) * 0.25F));
		int width = Math.max(1, vanillaHeight * 4);
		int height = Math.max(1, Math.round(width / MOJANG_SOURCE_ASPECT));
		int x = (graphics.guiWidth() - width) / 2;
		int y = (graphics.guiHeight() - height) / 2 - Math.round(vanillaHeight * 0.16F);
		int color = AnimationMath.clamp(Math.round(elapsed / 300.0F * 255.0F), 0, 255) << 24 | 0xFFFFFF;
		float u = (frame % MOJANG_ATLAS_COLUMNS) * MOJANG_FRAME_WIDTH + MOJANG_SOURCE_X;
		float v = (frame / MOJANG_ATLAS_COLUMNS) * MOJANG_FRAME_HEIGHT + MOJANG_SOURCE_Y;

		int background = LegacyIdentifierBridge.useLightLoadingBackground()
				? LIGHT_LOADING_BACKGROUND : LegacyCompatibility.loadingBackgroundColor();
		graphics.fill(0, 0, graphics.guiWidth(), graphics.guiHeight(), background);
		LegacyRenderBridge.blit(graphics, MOJANG_TEXTURE, x, y, u, v, width, height,
				MOJANG_SOURCE_WIDTH, MOJANG_SOURCE_HEIGHT, MOJANG_ATLAS_WIDTH, MOJANG_ATLAS_HEIGHT, color);
		long studiosStart = MOJANG_FRAME_COUNT * MOJANG_FRAME_TIME_MILLIS;
		int studiosAlpha = AnimationMath.clamp(Math.round(
				(elapsed - studiosStart) / (float) STUDIOS_FADE_MILLIS * 255.0F), 0, 255);
		if (studiosAlpha > 0) {
			int studiosHeight = Math.max(1, Math.round(vanillaHeight * 0.2F));
			int studiosWidth = Math.max(1, Math.round(studiosHeight * STUDIOS_SOURCE_ASPECT));
			int studiosX = (graphics.guiWidth() - studiosWidth) / 2;
			int studiosY = y + height + Math.round(vanillaHeight * 0.12F);
			LegacyRenderBridge.blit(graphics, STUDIOS_TEXTURE, studiosX, studiosY,
					STUDIOS_SOURCE_X, STUDIOS_SOURCE_Y, studiosWidth, studiosHeight,
					STUDIOS_SOURCE_WIDTH, STUDIOS_SOURCE_HEIGHT, 560, 90,
					studiosAlpha << 24 | 0xFFFFFF);
		}
	}

	public static void renderTitleBackground(GuiGraphics graphics) {
		suppressMenuMusicUntilWidgetsAppear();
		renderScreenBackground(graphics, "TitleScreen");
	}

	public static void renderScreenBackground(GuiGraphics graphics, String screenName) {
		long elapsed = titleElapsed();
		float progress = OpeningTimeline.progress(elapsed);
		if (LegacyIdentifierBridge.renderScene(graphics, screenName, progress)) {
			return;
		}
		LegacySchematicScene.get().render(graphics, screenName, progress, elapsed);
	}

	public static void renderScreenEffects(GuiGraphics graphics) {
		LegacyIdentifierBridge.renderEffects(graphics, OpeningTimeline.progress(titleElapsed()));
	}

	public static boolean hasVersionedScreenEffects() {
		return LegacyIdentifierBridge.hasVersionedScreenEffects();
	}

	public static void renderTitle(GuiGraphics graphics) {
		renderTitle(graphics, 1.0F);
	}

	public static void renderTitle(GuiGraphics graphics, float alpha) {
		long elapsed = titleElapsed() - TITLE_DELAY_MILLIS;
		if (elapsed < 0L || alpha <= 0.0F) {
			return;
		}
		int frame = AnimationMath.clamp((int) (elapsed / TITLE_FRAME_TIME_MILLIS), 0, TITLE_FRAME_COUNT - 1);
		int textureIndex = frame / TITLE_FRAMES_PER_TEXTURE;
		int row = frame % TITLE_FRAMES_PER_TEXTURE;
		float logoScale = Math.min(1.0F, graphics.guiWidth() * 0.90F / VANILLA_TITLE_WIDTH);
		int width = Math.max(1, Math.round(VANILLA_TITLE_WIDTH * logoScale * TITLE_SOURCE_WIDTH / TITLE_CONTENT_WIDTH));
		int height = Math.max(1, Math.round(width * TITLE_SOURCE_FRAME_HEIGHT / (float) TITLE_SOURCE_WIDTH));
		float sourceScale = width / (float) TITLE_SOURCE_WIDTH;
		int x = Math.round((graphics.guiWidth() - VANILLA_TITLE_WIDTH * logoScale) / 2.0F
				- TITLE_CONTENT_X * sourceScale);
		int y = Math.round(VANILLA_TITLE_TOP - TITLE_CONTENT_Y * sourceScale);

		int color = AnimationMath.clamp(Math.round(alpha * 255.0F), 0, 255) << 24 | 0xFFFFFF;
		LegacyRenderBridge.blit(graphics, TITLE_TEXTURES[textureIndex], x, y, 0.0F,
				row * (float) TITLE_SOURCE_FRAME_HEIGHT, width, height, TITLE_SOURCE_WIDTH,
				TITLE_SOURCE_FRAME_HEIGHT, TITLE_SOURCE_WIDTH, TITLE_SOURCE_TEXTURE_HEIGHT, color);
		if (OpeningTimeline.shouldRevealMenu(titleElapsed())) {
			renderEdition(graphics, logoScale, OpeningTimeline.menuFade(titleElapsed()));
		}
	}

	private static void renderEdition(GuiGraphics graphics, float logoScale, float alpha) {
		int width = Math.max(1, Math.round(VANILLA_EDITION_WIDTH * logoScale));
		int height = Math.max(1, Math.round(VANILLA_EDITION_HEIGHT * logoScale));
		int x = (graphics.guiWidth() - width) / 2;
		int y = Math.round(VANILLA_TITLE_TOP + (VANILLA_TITLE_HEIGHT - VANILLA_EDITION_OVERLAP) * logoScale);
		LegacyRenderBridge.blit(graphics, MINECRAFT_EDITION, x, y, 0.0F, 0.0F, width, height,
				VANILLA_EDITION_WIDTH, VANILLA_EDITION_HEIGHT, VANILLA_EDITION_WIDTH, 16,
				AnimationMath.clamp(Math.round(alpha * 255.0F), 0, 255) << 24 | 0xFFFFFF);
	}

	private static long titleElapsed() {
		return titleStartedAt == -1L ? 0L : Math.max(0L, System.currentTimeMillis() - titleStartedAt);
	}

	private static int blend(int start, int end, float progress) {
		int red = Math.round(AnimationMath.lerp(progress, start >> 16 & 255, end >> 16 & 255));
		int green = Math.round(AnimationMath.lerp(progress, start >> 8 & 255, end >> 8 & 255));
		int blue = Math.round(AnimationMath.lerp(progress, start & 255, end & 255));
		return 0xFF000000 | red << 16 | green << 8 | blue;
	}

}
