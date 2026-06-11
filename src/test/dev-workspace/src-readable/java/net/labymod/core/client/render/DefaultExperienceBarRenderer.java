package net.labymod.core.client.render;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.Namespaces;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.render.ExperienceBarRenderer;
import net.labymod.api.client.render.RenderMode;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/DefaultExperienceBarRenderer.class */
@Singleton
@Implements(ExperienceBarRenderer.class)
public class DefaultExperienceBarRenderer implements ExperienceBarRenderer {
    private static final ResourceLocation EXPERIENCE_BAR_BACKGROUND_SPRITE = ResourceLocation.create(Namespaces.MINECRAFT, "hud/experience_bar_background");
    private static final ResourceLocation EXPERIENCE_BAR_PROGRESS_SPRITE = ResourceLocation.create(Namespaces.MINECRAFT, "hud/experience_bar_progress");
    private static final float EXPERIENCE_BAR_WIDTH = 182.0f;
    private static final float EXPERIENCE_BAR_HEIGHT = 5.0f;
    private final LabyAPI labyAPI;
    private float x;
    private float y;
    private RenderMode renderMode = RenderMode.REAL;
    private int experienceNeededForNextLevel = 155;
    private float progress = 0.55f;
    private int level = 13;

    @Inject
    public DefaultExperienceBarRenderer(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    @Override // net.labymod.api.client.render.ExperienceBarRenderer
    public ExperienceBarRenderer mode(RenderMode mode) {
        this.renderMode = mode;
        return this;
    }

    @Override // net.labymod.api.client.render.ExperienceBarRenderer
    public ExperienceBarRenderer pos(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override // net.labymod.api.client.render.ExperienceBarRenderer
    public ExperienceBarRenderer experienceNeededForNextLevel(int level) {
        this.experienceNeededForNextLevel = level;
        return this;
    }

    @Override // net.labymod.api.client.render.ExperienceBarRenderer
    public ExperienceBarRenderer experienceProgress(float progress) {
        this.progress = progress;
        return this;
    }

    @Override // net.labymod.api.client.render.ExperienceBarRenderer
    public ExperienceBarRenderer experienceLevel(int level) {
        this.level = level;
        return this;
    }

    @Override // net.labymod.api.client.render.ExperienceBarRenderer
    public void render(ScreenContext context) {
        Minecraft minecraft = this.labyAPI.minecraft();
        ClientPlayer clientPlayer = minecraft.getClientPlayer();
        float experienceProgress = this.progress;
        int experienceLevel = this.level;
        int experienceNeededForNextLevel = this.experienceNeededForNextLevel;
        if (clientPlayer != null && this.renderMode == RenderMode.REAL) {
            experienceLevel = clientPlayer.getExperienceLevel();
            experienceProgress = clientPlayer.getExperienceProgress();
            experienceNeededForNextLevel = clientPlayer.getExperienceNeededForNextLevel();
        }
        ScreenCanvas screenCanvas = context.canvas();
        if (experienceNeededForNextLevel > 0) {
            float progress = experienceProgress * 183.0f;
            ResourceLocation iconsTextureLocation = minecraft.textures().iconsTexture();
            TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(iconsTextureLocation);
            screenCanvas.submitGuiSprite(atlas, EXPERIENCE_BAR_BACKGROUND_SPRITE, (int) this.x, (int) this.y, EXPERIENCE_BAR_WIDTH, EXPERIENCE_BAR_HEIGHT, -1);
            if (progress > 0.0f) {
                screenCanvas.submitGuiSprite(atlas, EXPERIENCE_BAR_PROGRESS_SPRITE, (int) this.x, (int) this.y, (int) progress, 5, 0, 0, 182, 5, -1);
            }
        }
        if (experienceLevel > 0) {
            ScreenCanvas canvas = context.canvas();
            float textY = this.y - (canvas.getLineHeight() / 2.0f);
            String levelText = String.valueOf(experienceLevel);
            float width = 91.0f - (canvas.getTextWidth(levelText) / 2.0f);
            renderText(canvas, this.x + width + 1.0f, textY, levelText, -16777216);
            renderText(canvas, (this.x + width) - 1.0f, textY, levelText, -16777216);
            renderText(canvas, this.x + width, textY + 1.0f, levelText, -16777216);
            renderText(canvas, this.x + width, textY - 1.0f, levelText, -16777216);
            renderText(canvas, this.x + width, textY, levelText, -8323296);
        }
    }

    private void renderText(ScreenCanvas canvas, float x, float textY, String levelText, int color) {
        canvas.submitText(levelText, x, textY, color, 0);
    }

    @Override // net.labymod.api.client.render.ExperienceBarRenderer
    public float getWidth() {
        return EXPERIENCE_BAR_WIDTH;
    }

    @Override // net.labymod.api.client.render.ExperienceBarRenderer
    public float getHeight() {
        return EXPERIENCE_BAR_HEIGHT;
    }
}
