package animated_mojang.client.internal;

import animated_mojang.client.AnimatedMojangClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public final class AnimatedMinecraftTitle {
	private static final int FRAME_COUNT = 69;
	private static final int FRAMES_PER_TEXTURE = 8;
	private static final int FRAME_TIME_MS = 40;
	private static final int ANIMATION_DELAY_MS = 1880;
	private static final int SOURCE_WIDTH = 2403;
	private static final int SOURCE_FRAME_HEIGHT = 749;
	private static final int SOURCE_TEXTURE_HEIGHT = SOURCE_FRAME_HEIGHT * FRAMES_PER_TEXTURE;
	private static final float SOURCE_RATIO = SOURCE_WIDTH / (float) SOURCE_FRAME_HEIGHT;
	private static final int FINAL_CONTENT_X = 518;
	private static final int FINAL_CONTENT_Y = 502;
	private static final int FINAL_CONTENT_WIDTH = 1373;
	private static final int VANILLA_LOGO_WIDTH = 256;
	private static final int VANILLA_LOGO_HEIGHT = 44;
	private static final int VANILLA_EDITION_WIDTH = 128;
	private static final int VANILLA_EDITION_HEIGHT = 14;
	private static final int VANILLA_EDITION_OVERLAP = 7;
	private static final int VANILLA_WORD_TOP = 30;
	private static final Identifier MINECRAFT_EDITION = Identifier.withDefaultNamespace(
			"textures/gui/title/edition.png");

	private static final Identifier[] TEXTURES = new Identifier[9];
	private static boolean preloaded;

	static {
		for (int index = 0; index < TEXTURES.length; index++) {
			TEXTURES[index] = Identifier.fromNamespaceAndPath(AnimatedMojangClient.MOD_ID,
					"textures/gui/title/minecraft_animation/minecraft_" + index + ".png");
		}
	}

	private AnimatedMinecraftTitle() {
	}

	public static void preload() {
		if (preloaded) {
			return;
		}
		preloaded = true;
		var textureManager = Minecraft.getInstance().getTextureManager();
		for (Identifier texture : TEXTURES) {
			textureManager.registerAndLoad(texture, new SimpleTexture(texture));
		}
	}

	public static void render(GuiGraphicsExtractor graphics, float alpha) {
		long elapsed = TitleOpeningController.getOpeningElapsedMillis() - ANIMATION_DELAY_MS;
		if (elapsed < 0L) {
			return;
		}
		int frame = Mth.clamp((int) (elapsed / FRAME_TIME_MS), 0, FRAME_COUNT - 1);
		int textureIndex = frame / FRAMES_PER_TEXTURE;
		int row = frame % FRAMES_PER_TEXTURE;

		float logoScale = logoScale(graphics);
		int canvasWidth = Math.max(1, Math.round(VANILLA_LOGO_WIDTH * logoScale * SOURCE_WIDTH / FINAL_CONTENT_WIDTH));
		int canvasHeight = Math.max(1, Math.round(canvasWidth / SOURCE_RATIO));
		float sourceScale = canvasWidth / (float) SOURCE_WIDTH;
		int x = Math.round((graphics.guiWidth() - VANILLA_LOGO_WIDTH * logoScale) / 2.0F
				- FINAL_CONTENT_X * sourceScale);
		int y = Math.round(VANILLA_WORD_TOP - FINAL_CONTENT_Y * sourceScale);
		int color = Mth.clamp(Math.round(alpha * 255.0F), 0, 255) << 24 | 0xFFFFFF;

		graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURES[textureIndex], x, y, 0.0F,
				row * (float) SOURCE_FRAME_HEIGHT, canvasWidth, canvasHeight, SOURCE_WIDTH,
				SOURCE_FRAME_HEIGHT, SOURCE_WIDTH, SOURCE_TEXTURE_HEIGHT, color);
	}

	public static void renderEdition(GuiGraphicsExtractor graphics, float alpha) {
		if (alpha <= 0.0F) {
			return;
		}
		float logoScale = logoScale(graphics);
		int width = Math.max(1, Math.round(VANILLA_EDITION_WIDTH * logoScale));
		int height = Math.max(1, Math.round(VANILLA_EDITION_HEIGHT * logoScale));
		int x = (graphics.guiWidth() - width) / 2;
		int y = Math.round(VANILLA_WORD_TOP + (VANILLA_LOGO_HEIGHT - VANILLA_EDITION_OVERLAP) * logoScale);
		int color = Mth.clamp(Math.round(alpha * 255.0F), 0, 255) << 24 | 0xFFFFFF;
		graphics.blit(RenderPipelines.GUI_TEXTURED, MINECRAFT_EDITION, x, y, 0.0F, 0.0F, width, height,
				128, 14, 128, 16, color);
	}

	private static float logoScale(GuiGraphicsExtractor graphics) {
		return Math.min(1.0F, graphics.guiWidth() * 0.90F / VANILLA_LOGO_WIDTH);
	}

}
