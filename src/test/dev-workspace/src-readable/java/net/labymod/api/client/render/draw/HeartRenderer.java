package net.labymod.api.client.render.draw;

import java.util.EnumMap;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.client.gfx.pipeline.texture.atlas.AtlasRegistry;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.MinecraftTextures;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.laby3d.render.queue.submissions.IconSubmission;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.HealthStatus;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/HeartRenderer.class */
@Referenceable
public interface HeartRenderer {
    void submitHealthBar(ScreenContext screenContext, float f, float f2, int i, HealthStatus healthStatus);

    void submitHealthBar(Stack stack, SubmissionCollector submissionCollector, IconSubmission.DisplayMode displayMode, float f, float f2, int i, HealthStatus healthStatus);

    void submitHearts(HeartTexture heartTexture, Stack stack, SubmissionCollector submissionCollector, IconSubmission.DisplayMode displayMode, float f, float f2, int i, int i2);

    void submitHearts(HeartTexture heartTexture, ScreenContext screenContext, float f, float f2, int i, int i2);

    void startFlashing(int i);

    void stopFlashing();

    boolean isFlashing();

    boolean isCurrentlyFlashing();

    int getWidth(int i, int i2);

    default void submitSingleHeart(HeartTexture heartTexture, ScreenContext context, float x, float y, int size) {
        submitHearts(heartTexture, context, x, y, size, 1);
    }

    default void submitSingleHeart(HeartTexture heartTexture, Stack stack, SubmissionCollector collector, IconSubmission.DisplayMode displayMode, float x, float y, int size) {
        submitHearts(heartTexture, stack, collector, displayMode, x, y, size, 1);
    }

    default int getWidth(HealthStatus status, int size) {
        int maxHealth = Math.min(MathHelper.ceil(status.getMaxHealth()), 20);
        int absorption = MathHelper.ceil(status.getAbsorptionHealth());
        return getWidth(maxHealth + absorption, size);
    }

    default HeartTexture getBackgroundTexture() {
        return isCurrentlyFlashing() ? HeartTexture.FLASHING_HEART : HeartTexture.EMPTY_HEART;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/HeartRenderer$HeartTexture.class */
    public enum HeartTexture {
        EMPTY_HEART("hud/heart/container"),
        FLASHING_HEART("hud/heart/full_blinking"),
        FULL_HEART("hud/heart/full"),
        HALF_HEART("hud/heart/half"),
        FULL_ABSORPTION_HEART("hud/heart/absorbing_full"),
        HALF_ABSORPTION_HEART("hud/heart/absorbing_half");

        private static final Map<HeartTexture, Icon> ICONS = new EnumMap(HeartTexture.class);
        private final AtlasRegistry atlasRegistry = Laby.references().atlasRegistry();
        private final MinecraftTextures textures = Laby.labyAPI().minecraft().textures();
        private final ResourceLocation location;

        HeartTexture(String path) {
            this.location = ResourceLocation.create(Namespaces.MINECRAFT, path);
        }

        public Icon getIcon() {
            return ICONS.computeIfAbsent(this, texture -> {
                ResourceLocation iconsTexture = this.textures.iconsTexture();
                TextureAtlas atlas = this.atlasRegistry.getAtlas(iconsTexture);
                return Icon.sprite(atlas, atlas.findSprite(this.location));
            });
        }

        public void submit(ScreenCanvas canvas, float x, float y, float size) {
            TextureAtlas atlas = this.atlasRegistry.getAtlas(this.textures.iconsTexture());
            canvas.submitGuiSprite(atlas, this.location, x, y, size, size, -1);
        }

        public void submit(Stack stack, SubmissionCollector collector, IconSubmission.DisplayMode displayMode, float x, float y, float size) {
            collector.submitIcon(stack, getIcon(), displayMode, x, y, size, size, -1);
        }
    }
}
