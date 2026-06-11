package net.labymod.core.client.render.draw;

import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.render.draw.HeartRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.laby3d.render.queue.submissions.IconSubmission;
import net.labymod.api.models.Implements;
import net.labymod.api.util.HealthStatus;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/draw/DefaultHeartRenderer.class */
@Implements(HeartRenderer.class)
public class DefaultHeartRenderer implements HeartRenderer {
    private static final float HEART_SIZE = 9.0f;
    private static final float HEART_PIXEL_SIZE = 0.11111111f;
    private static final float HEART_PIXEL_UI_OFFSET = 0.8888889f;
    private static final float HEART_PIXEL_3D_OFFSET = 0.875f;
    private static final long FLASHING_DELAY = 100;
    private static final long FLASHING_TIME = 185;
    private long flashingStartTime;
    private int flashingTimes;

    @Inject
    public DefaultHeartRenderer() {
    }

    @Override // net.labymod.api.client.render.draw.HeartRenderer
    public void submitHealthBar(ScreenContext context, float x, float y, int size, HealthStatus status) {
        float health = status.getHealth();
        float maxHealth = status.getMaxHealth();
        float absorption = status.getAbsorptionHealth();
        HeartRenderer.HeartTexture backgroundTexture = getBackgroundTexture();
        submitHearts(backgroundTexture, context, x, y, size, ((int) maxHealth) / 2);
        float actualSize = getActualSize(size);
        for (int i = 1; i <= maxHealth + absorption; i++) {
            if (i % 2 == 0) {
                float iconX = x + ((actualSize / 2.0f) * (i - 2));
                if (i <= health) {
                    submitSingleHeart(HeartRenderer.HeartTexture.FULL_HEART, context, iconX, y, size);
                } else if (i > maxHealth) {
                    submitSingleHeart(backgroundTexture, context, iconX, y, size);
                    submitSingleHeart(HeartRenderer.HeartTexture.FULL_ABSORPTION_HEART, context, iconX, y, size);
                }
            } else {
                float iconX2 = x + ((actualSize / 2.0f) * (i - 1));
                if (i == health) {
                    submitSingleHeart(HeartRenderer.HeartTexture.HALF_HEART, context, iconX2, y, size);
                } else if (i > maxHealth && maxHealth + absorption == i) {
                    submitSingleHeart(backgroundTexture, context, iconX2, y, size);
                    submitSingleHeart(HeartRenderer.HeartTexture.HALF_ABSORPTION_HEART, context, iconX2, y, size);
                }
            }
        }
    }

    @Override // net.labymod.api.client.render.draw.HeartRenderer
    public void submitHealthBar(Stack stack, SubmissionCollector collector, IconSubmission.DisplayMode displayMode, float x, float y, int size, HealthStatus status) {
        float health = status.getHealth();
        float maxHealth = status.getMaxHealth();
        float absorption = status.getAbsorptionHealth();
        HeartRenderer.HeartTexture backgroundTexture = getBackgroundTexture();
        submitHearts(backgroundTexture, stack, collector, displayMode, x, y, size, ((int) maxHealth) / 2);
        float actualSize = getActualSize(size);
        for (int i = 1; i <= maxHealth + absorption; i++) {
            if (i % 2 == 0) {
                float iconX = x + ((actualSize / 2.0f) * (i - 2));
                if (i <= health) {
                    submitSingleHeart(HeartRenderer.HeartTexture.FULL_HEART, stack, collector, displayMode, iconX, y, size);
                } else if (i > maxHealth) {
                    submitSingleHeart(backgroundTexture, stack, collector, displayMode, iconX, y, size);
                    submitSingleHeart(HeartRenderer.HeartTexture.FULL_ABSORPTION_HEART, stack, collector, displayMode, iconX, y, size);
                }
            } else {
                float iconX2 = x + ((actualSize / 2.0f) * (i - 1));
                if (i == health) {
                    submitSingleHeart(HeartRenderer.HeartTexture.HALF_HEART, stack, collector, displayMode, iconX2, y, size);
                } else if (i > maxHealth && maxHealth + absorption == i) {
                    submitSingleHeart(backgroundTexture, stack, collector, displayMode, iconX2, y, size);
                    submitSingleHeart(HeartRenderer.HeartTexture.HALF_ABSORPTION_HEART, stack, collector, displayMode, iconX2, y, size);
                }
            }
        }
    }

    @Override // net.labymod.api.client.render.draw.HeartRenderer
    public void submitHearts(HeartRenderer.HeartTexture heartTexture, Stack stack, SubmissionCollector collector, IconSubmission.DisplayMode displayMode, float x, float y, int size, int heartAmount) {
        float actualSize = getActualSize(size);
        for (int index = 0; index < heartAmount; index++) {
            heartTexture.submit(stack, collector, displayMode, x + (index * actualSize), y, size);
        }
    }

    @Override // net.labymod.api.client.render.draw.HeartRenderer
    public void submitHearts(HeartRenderer.HeartTexture heartTexture, ScreenContext context, float x, float y, int size, int heartAmount) {
        float actualSize = getActualSize(size);
        ScreenCanvas canvas = context.canvas();
        for (int i = 0; i < heartAmount; i++) {
            heartTexture.submit(canvas, x + (i * actualSize), y, size);
        }
    }

    @Override // net.labymod.api.client.render.draw.HeartRenderer
    public void startFlashing(int times) {
        this.flashingTimes = times;
        this.flashingStartTime = TimeUtil.getMillis();
    }

    @Override // net.labymod.api.client.render.draw.HeartRenderer
    public void stopFlashing() {
        this.flashingTimes = 0;
    }

    @Override // net.labymod.api.client.render.draw.HeartRenderer
    public boolean isFlashing() {
        return this.flashingTimes != 0;
    }

    @Override // net.labymod.api.client.render.draw.HeartRenderer
    public boolean isCurrentlyFlashing() {
        long currentTime = TimeUtil.getMillis();
        if (isFlashing() && this.flashingStartTime <= currentTime && this.flashingStartTime < currentTime) {
            long flashingEndTime = this.flashingStartTime + FLASHING_TIME;
            if (flashingEndTime > currentTime) {
                return true;
            }
            this.flashingTimes--;
            this.flashingStartTime = currentTime + FLASHING_DELAY;
            return false;
        }
        return false;
    }

    @Override // net.labymod.api.client.render.draw.HeartRenderer
    public int getWidth(int hearts, int size) {
        return (int) ((hearts * getActualSize(size)) / 2.0f);
    }

    private float getActualSize(int size) {
        if (Laby.references().renderEnvironmentContext().isScreenContext()) {
            return size * HEART_PIXEL_UI_OFFSET;
        }
        return size * HEART_PIXEL_3D_OFFSET;
    }
}
