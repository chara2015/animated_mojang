package net.labymod.core.client.gui.background.bootlogo;

import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/background/bootlogo/AbstractBootLogoRenderer.class */
public abstract class AbstractBootLogoRenderer {
    protected final LabyAPI labyAPI = Laby.labyAPI();
    protected final RectangleRenderer rectangleRenderer = this.labyAPI.renderPipeline().rectangleRenderer();
    protected float timePassed;
    protected boolean resourcesPreloaded;
    protected float progress;
    protected boolean progressVisible;

    public abstract void renderBackground(ScreenContext screenContext, float f, float f2, float f3, float f4);

    public abstract void renderForeground(ScreenContext screenContext, float f, float f2, float f3, float f4, boolean z);

    protected abstract void onPreloadResources();

    protected AbstractBootLogoRenderer() {
    }

    public void updateProgress(float progress, boolean visible) {
        this.progress = (float) MathHelper.clamp((((double) this.progress) * 0.95d) + (((double) progress) * 0.05d), 0.0d, 1.0d);
        this.progressVisible = visible;
    }

    public void initialize() {
        this.timePassed = 0.0f;
        this.progress = 0.0f;
    }

    public void render(ScreenContext context, float left, float top, float right, float bottom) {
        renderBackground(context, left, top, right, bottom);
        renderForeground(context, left, top, right, bottom);
    }

    public void renderForeground(ScreenContext context, float left, float top, float right, float bottom) {
        renderForeground(context, left, top, right, bottom, true);
    }

    public void preloadResources() {
        if (this.resourcesPreloaded) {
            return;
        }
        onPreloadResources();
        this.resourcesPreloaded = true;
    }

    protected void preloadTexture(ResourceLocation resourceLocation) {
        Laby.references().textureRepository().preloadTexture(resourceLocation);
    }

    public boolean isResourcesPreloaded() {
        return this.resourcesPreloaded;
    }
}
