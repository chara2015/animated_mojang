package net.labymod.api.client.render;

import net.labymod.api.client.GameTickProvider;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.gfx.pipeline.renderer.text.TextRenderer;
import net.labymod.api.client.render.batch.RenderContexts;
import net.labymod.api.client.render.draw.CircleRenderer;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.model.ModelService;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.api.client.resources.pack.ResourcePackRepository;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/RenderPipeline.class */
@Referenceable
public interface RenderPipeline {
    void setModifiedAlpha(boolean z);

    void setAlpha(float f);

    void multiplyAlpha(float f);

    void multiplyAlpha(float f, Runnable runnable);

    void setAlpha(float f, Runnable runnable);

    float getAlpha();

    TextRenderer textRenderer();

    ComponentRenderer componentRenderer();

    CircleRenderer circleRenderer();

    RectangleRenderer rectangleRenderer();

    ResourceRenderer resourceRenderer();

    ModelService modelService();

    Resources resources();

    ResourcePackRepository resourcePackRepository();

    ResourcePack.Factory resourcePackFactory();

    RenderContexts renderContexts();

    RenderConstants renderConstants();

    GameTickProvider gameTickProvider();

    void renderSeeThrough(Entity entity, float f, Runnable runnable);

    void renderNoneStandardNameTag(Entity entity, Runnable runnable);

    default void renderSeeThrough(Entity entity, Runnable renderer) {
        renderSeeThrough(entity, 0.5f, renderer);
    }
}
