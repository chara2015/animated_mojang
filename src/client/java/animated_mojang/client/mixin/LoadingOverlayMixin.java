package animated_mojang.client.mixin;

import animated_mojang.client.internal.MojangAnimation;
import animated_mojang.client.internal.TitleOpeningController;
import animated_mojang.config.AnimatedMojangConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(LoadingOverlay.class)
public class LoadingOverlayMixin {
	@Unique
	private static final int LIGHT_BACKGROUND = 0xFFEF323D;
	@Unique
	private static final int DARK_BACKGROUND = 0xFF000000;
	@Unique
	private static final int TEXT_COLOR = 0xFFFFFFFF;
	@Unique
	private static final Identifier VANILLA_LOGO = Identifier.withDefaultNamespace("textures/gui/title/mojangstudios.png");
	@Unique
	private boolean animatedMojang$reloadFinished;

	@Inject(method = "extractRenderState", at = @At("HEAD"), cancellable = true)
	private void animatedMojang$render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		if (!AnimatedMojangConfig.isMojangLogoAnimationEnabled()) {
			return;
		}
		ci.cancel();
		LoadingOverlayAccessor overlay = (LoadingOverlayAccessor) this;
		long now = Util.getMillis();

		tickFadeIn(overlay, now);
		smoothProgress(overlay);

		float fadeOut = fadeOutProgress(overlay, now);
		float fadeIn = fadeInProgress(overlay, now);
		graphics.fill(0, 0, graphics.guiWidth(), graphics.guiHeight(), backgroundColor(overlay));

		if (shouldStartAnimation(overlay, fadeOut)) {
			MojangAnimation.start();
		}

		if (MojangAnimation.hasStarted()) {
			MojangAnimation.render(graphics);
		} else {
			renderVanillaLogo(graphics);
		}

		renderProgressBar(graphics, overlay, fadeOut, backgroundColor(overlay));

		if (canFinishReload(overlay, fadeIn)) {
			finishReload(overlay, graphics);
		}

		if (shouldFadeOut(overlay)) {
			overlay.animatedMojang$setFadeOutStart(now);
		}

		if (fadeOut >= 1.0F && MojangAnimation.isFinished()) {
			overlay.animatedMojang$getMinecraft().setOverlay(null);
			TitleOpeningController.playOpening();
		}
	}

	@Inject(method = "extractRenderState", at = @At("TAIL"))
	private void animatedMojang$startTitleAfterVanillaLoading(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
			float delta, CallbackInfo ci) {
		if (!AnimatedMojangConfig.isMojangLogoAnimationEnabled()
				&& ((LoadingOverlayAccessor) this).animatedMojang$getMinecraft().getOverlay() == null) {
			TitleOpeningController.playOpening();
		}
	}

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void animatedMojang$tick(CallbackInfo ci) {
		if (!AnimatedMojangConfig.isMojangLogoAnimationEnabled()) {
			return;
		}
		ci.cancel();
	}

	@Unique
	private static int backgroundColor(LoadingOverlayAccessor overlay) {
		return overlay.animatedMojang$getMinecraft().options.darkMojangStudiosBackground().get()
				? DARK_BACKGROUND : LIGHT_BACKGROUND;
	}

	@Unique
	private static void tickFadeIn(LoadingOverlayAccessor overlay, long now) {
		if (overlay.animatedMojang$getFadeIn() && overlay.animatedMojang$getFadeInStart() == -1L) {
			overlay.animatedMojang$setFadeInStart(now);
		}
	}

	@Unique
	private static float fadeOutProgress(LoadingOverlayAccessor overlay, long now) {
		return overlay.animatedMojang$getFadeOutStart() > -1L
				? (now - overlay.animatedMojang$getFadeOutStart()) / 1000.0F : -1.0F;
	}

	@Unique
	private static float fadeInProgress(LoadingOverlayAccessor overlay, long now) {
		return overlay.animatedMojang$getFadeInStart() > -1L
				? (now - overlay.animatedMojang$getFadeInStart()) / 500.0F : -1.0F;
	}

	@Unique
	private static void smoothProgress(LoadingOverlayAccessor overlay) {
		float rawProgress = overlay.animatedMojang$getReload().getActualProgress();
		float target = overlay.animatedMojang$getReload().isDone() ? 1.0F : rawProgress;
		float smoothing = MojangAnimation.isFinished() ? 0.12F : 0.050000012F;
		float progress = overlay.animatedMojang$getCurrentProgress() * (1.0F - smoothing) + target * smoothing;
		overlay.animatedMojang$setCurrentProgress(Mth.clamp(progress, 0.0F, 1.0F));
	}

	@Unique
	private static boolean shouldStartAnimation(LoadingOverlayAccessor overlay, float fadeOut) {
		return fadeOut >= 1.0F
				&& !MojangAnimation.hasStarted()
				&& overlay.animatedMojang$getReload().isDone();
	}

	@Unique
	private static void renderProgressBar(GuiGraphicsExtractor graphics, LoadingOverlayAccessor overlay, float fadeOut,
			int backgroundColor) {
		int centerX = graphics.guiWidth() / 2;
		int halfWidth = (int) (Math.min(graphics.guiWidth() * 0.75, graphics.guiHeight()) * 0.5);
		int y = (int) (graphics.guiHeight() * 0.8325);
		float alpha = MojangAnimation.isFinished() ? 1.0F - Mth.clamp(fadeOut - 1.0F, 0.0F, 1.0F) : 1.0F;
		drawProgressBar(graphics, centerX - halfWidth, y - 5, centerX + halfWidth, y + 5, alpha,
				overlay.animatedMojang$getCurrentProgress(), backgroundColor);
	}

	@Unique
	private static void renderVanillaLogo(GuiGraphicsExtractor graphics) {
		int centerX = graphics.guiWidth() / 2;
		int centerY = graphics.guiHeight() / 2;
		double logoHeight = Math.min(graphics.guiWidth() * 0.75, graphics.guiHeight()) * 0.25;
		int halfHeight = (int) (logoHeight * 0.5);
		double logoWidth = logoHeight * 4.0;
		int halfWidth = (int) (logoWidth * 0.5);

		graphics.blit(RenderPipelines.MOJANG_LOGO, VANILLA_LOGO, centerX - halfWidth, centerY - halfHeight,
				-0.0625F, 0.0F, halfWidth, (int) logoHeight, 120, 60, 120, 120, TEXT_COLOR);
		graphics.blit(RenderPipelines.MOJANG_LOGO, VANILLA_LOGO, centerX, centerY - halfHeight,
				0.0625F, 60.0F, halfWidth, (int) logoHeight, 120, 60, 120, 120, TEXT_COLOR);
	}

	@Unique
	private static void drawProgressBar(GuiGraphicsExtractor graphics, int x1, int y1, int x2, int y2, float alpha,
			float progress, int backgroundColor) {
		int width = x2 - x1;
		int filled = Math.min(Mth.ceil((width - 2) * progress), width - 4);
		int border = applyAlpha(TEXT_COLOR, alpha);
		int background = applyAlpha(backgroundColor, alpha);

		graphics.fill(x1 + 1, y1, x2 - 1, y1 + 1, border);
		graphics.fill(x1 + 1, y2 - 1, x2 - 1, y2, border);
		graphics.fill(x1, y1, x1 + 1, y2, border);
		graphics.fill(x2 - 1, y1, x2, y2, border);
		graphics.fill(x1 + 1, y1 + 1, x2 - 1, y2 - 1, background);
		graphics.fill(x1 + 2, y1 + 2, x1 + 2 + filled, y2 - 2, border);
	}

	@Unique
	private static int applyAlpha(int color, float alpha) {
		return Math.round(alpha * 255.0F) << 24 | color & 0xFFFFFF;
	}

	@Unique
	private boolean canFinishReload(LoadingOverlayAccessor overlay, float fadeIn) {
		return !animatedMojang$reloadFinished
				&& overlay.animatedMojang$getReload().isDone()
				&& (!overlay.animatedMojang$getFadeIn() || fadeIn >= 2.0F);
	}

	@Unique
	private void finishReload(LoadingOverlayAccessor overlay, GuiGraphicsExtractor graphics) {
		try {
			overlay.animatedMojang$getReload().checkExceptions();
			overlay.animatedMojang$getOnFinish().accept(Optional.empty());
		} catch (Throwable throwable) {
			overlay.animatedMojang$getOnFinish().accept(Optional.of(throwable));
		}

		animatedMojang$reloadFinished = true;
		if (overlay.animatedMojang$getMinecraft().screen != null) {
			overlay.animatedMojang$getMinecraft().screen.init(graphics.guiWidth(), graphics.guiHeight());
		}
	}

	@Unique
	private boolean shouldFadeOut(LoadingOverlayAccessor overlay) {
		return animatedMojang$reloadFinished
				&& overlay.animatedMojang$getFadeOutStart() == -1L;
	}
}
