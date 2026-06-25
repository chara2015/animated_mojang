package labymod_menu.client.internal;

import labymod_menu.client.LabyModMenuClient;
import labymod_menu.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public final class MojangAnimation {
	private static final int FRAME_COUNT = 79;
	private static final int FRAME_TIME_MS = 40;
	private static final int LOGO_ANIMATION_MS = FRAME_COUNT * FRAME_TIME_MS;
	private static final int SOUND_DELAY_MS = 350;
	private static final int SOUND_RETRY_MS = 200;
	private static final int SOUND_LENGTH_MS = 4066;
	private static final int AUDIO_TAIL_PADDING_MS = 450;
	private static final int SOUND_SYNC_FALLBACK_MS = 8000;
	private static final int MAX_VISUAL_STEP_MS = 50;
	private static final int TOTAL_ANIMATION_MS = SOUND_DELAY_MS + SOUND_LENGTH_MS + AUDIO_TAIL_PADDING_MS;
	private static final int FADE_IN_MS = 300;
	private static final int STUDIOS_START_MS = LOGO_ANIMATION_MS;
	private static final int STUDIOS_FADE_MS = 400;
	private static final float LOGO_ASPECT = 4.0F;
	private static final float STUDIOS_ASPECT = 560.0F / 90.0F;
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 129;
	private static final int LOGO_SOURCE_X = 31;
	private static final int LOGO_SOURCE_Y = 31;
	private static final int LOGO_SOURCE_WIDTH = 540;
	private static final int LOGO_SOURCE_HEIGHT = 83;
	private static final float LOGO_SOURCE_ASPECT = LOGO_SOURCE_WIDTH / (float) LOGO_SOURCE_HEIGHT;
	private static final int STUDIOS_SOURCE_X = 30;
	private static final int STUDIOS_SOURCE_Y = 18;
	private static final int STUDIOS_SOURCE_WIDTH = 506;
	private static final int STUDIOS_SOURCE_HEIGHT = 56;
	private static final float STUDIOS_SOURCE_ASPECT = STUDIOS_SOURCE_WIDTH / (float) STUDIOS_SOURCE_HEIGHT;
	private static final int ATLAS_COLUMNS = 10;
	private static final int ATLAS_WIDTH = FRAME_WIDTH * ATLAS_COLUMNS;
	private static final int ATLAS_HEIGHT = FRAME_HEIGHT * 8;
	private static final Identifier LOGO_TEXTURE = Identifier.fromNamespaceAndPath(
			LabyModMenuClient.MOD_ID, "textures/gui/mojang_animation.png");
	private static final Identifier STUDIOS_TEXTURE = Identifier.fromNamespaceAndPath(
			LabyModMenuClient.MOD_ID, "textures/gui/studios.png");

	private static boolean started;
	private static long startedAt = -1L;
	private static long visualElapsedMs;
	private static long lastRenderAt = -1L;
	private static boolean soundStarted;
	private static boolean soundAttempted;
	private static boolean soundReleased;
	private static long lastSoundAttemptAt = -1L;
	private static long soundStartedAt = -1L;

	private MojangAnimation() {
	}

	public static void start() {
		if (started) {
			return;
		}

		started = true;
		startedAt = System.currentTimeMillis();
		visualElapsedMs = 0L;
		lastRenderAt = -1L;
		soundStarted = false;
		soundAttempted = false;
		soundReleased = false;
		lastSoundAttemptAt = -1L;
		soundStartedAt = -1L;
	}

	public static boolean isFinished() {
		if (startedAt == -1L) {
			return false;
		}

		long elapsed = visualElapsedMs;
		long soundEnd = soundStarted ? SOUND_DELAY_MS + SOUND_LENGTH_MS + AUDIO_TAIL_PADDING_MS : TOTAL_ANIMATION_MS;
		long studiosEnd = STUDIOS_START_MS + STUDIOS_FADE_MS;
		return elapsed >= Math.max(Math.max(TOTAL_ANIMATION_MS, soundEnd), studiosEnd);
	}

	public static boolean hasStarted() {
		return started;
	}

	public static void render(GuiGraphicsExtractor graphics) {
		long now = System.currentTimeMillis();
		long elapsed = animationElapsed(now);
		elapsed = startSound(now, elapsed);
		int frame = (int) Math.min(elapsed / FRAME_TIME_MS, FRAME_COUNT - 1L);
		float logoAlpha = Mth.clamp(elapsed / (float) FADE_IN_MS, 0.0F, 1.0F);
		float studiosAlpha = Mth.clamp((elapsed - STUDIOS_START_MS) / (float) STUDIOS_FADE_MS, 0.0F, 1.0F);

		renderLogo(graphics, frame, logoAlpha);
		renderStudios(graphics, studiosAlpha);
	}

	public static void renderPreview(GuiGraphicsExtractor graphics) {
		renderLogo(graphics, 0, 1.0F);
	}

	private static long animationElapsed(long now) {
		if (lastRenderAt == -1L) {
			lastRenderAt = now;
			return visualElapsedMs;
		}
		long delta = Math.max(0L, now - lastRenderAt);
		lastRenderAt = now;
		visualElapsedMs += Math.min(delta, MAX_VISUAL_STEP_MS);
		return visualElapsedMs;
	}

	private static long startSound(long now, long elapsed) {
		if (soundReleased || startedAt == -1L || elapsed < SOUND_DELAY_MS) {
			return elapsed;
		}
		if (lastSoundAttemptAt != -1L && now - lastSoundAttemptAt < SOUND_RETRY_MS) {
			visualElapsedMs = SOUND_DELAY_MS;
			return SOUND_DELAY_MS;
		}

		lastSoundAttemptAt = now;
		SoundEngine.PlayResult result = Minecraft.getInstance().getSoundManager()
				.play(SimpleSoundInstance.forUI(ModSounds.STARTUP, 1.0F));
		soundAttempted = true;
		soundStarted = result != SoundEngine.PlayResult.NOT_STARTED;
		if (soundStarted) {
			soundReleased = true;
			soundStartedAt = now;
			visualElapsedMs = SOUND_DELAY_MS;
			return SOUND_DELAY_MS;
		}
		if (now - startedAt >= SOUND_SYNC_FALLBACK_MS && soundAttempted) {
			soundReleased = true;
			soundStartedAt = now;
			return elapsed;
		}
		visualElapsedMs = SOUND_DELAY_MS;
		return SOUND_DELAY_MS;
	}

	private static void renderLogo(GuiGraphicsExtractor graphics, int frame, float alpha) {
		int vanillaHeight = (int) (Math.min(graphics.guiWidth() * 0.75, graphics.guiHeight()) * 0.25);
		int width = (int) (vanillaHeight * LOGO_ASPECT);
		int height = (int) (width / LOGO_SOURCE_ASPECT);
		int x = (graphics.guiWidth() - width) / 2;
		int y = (graphics.guiHeight() - height) / 2 - Math.round(vanillaHeight * 0.16F);
		float u = (frame % ATLAS_COLUMNS) * FRAME_WIDTH + LOGO_SOURCE_X;
		float v = (frame / ATLAS_COLUMNS) * FRAME_HEIGHT + LOGO_SOURCE_Y;
		drawTexture(graphics, LOGO_TEXTURE, x, y, width, height, u, v, LOGO_SOURCE_WIDTH, LOGO_SOURCE_HEIGHT,
				ATLAS_WIDTH, ATLAS_HEIGHT, alpha);
	}

	private static void renderStudios(GuiGraphicsExtractor graphics, float alpha) {
		int vanillaHeight = (int) (Math.min(graphics.guiWidth() * 0.75, graphics.guiHeight()) * 0.25);
		int logoWidth = (int) (vanillaHeight * LOGO_ASPECT);
		int logoHeight = (int) (logoWidth / LOGO_SOURCE_ASPECT);
		int logoY = (graphics.guiHeight() - logoHeight) / 2 - Math.round(vanillaHeight * 0.16F);
		int height = (int) (vanillaHeight * 0.2F);
		int width = (int) (height * STUDIOS_SOURCE_ASPECT);
		int x = (graphics.guiWidth() - width) / 2;
		int y = logoY + logoHeight + Math.round(vanillaHeight * 0.12F);
		drawTexture(graphics, STUDIOS_TEXTURE, x, y, width, height, STUDIOS_SOURCE_X, STUDIOS_SOURCE_Y,
				STUDIOS_SOURCE_WIDTH, STUDIOS_SOURCE_HEIGHT, 560, 90, alpha);
	}

	private static void drawTexture(GuiGraphicsExtractor graphics, Identifier texture, int x, int y, int width, int height,
			float u, float v, int sourceWidth, int sourceHeight, int textureWidth, int textureHeight, float alpha) {
		graphics.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, u, v, width, height, sourceWidth, sourceHeight,
				textureWidth, textureHeight, Math.round(Mth.clamp(alpha, 0.0F, 1.0F) * 255.0F) << 24 | 0xFFFFFF);
	}

}
