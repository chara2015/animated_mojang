package net.labymod.core.client.render;

import net.labymod.api.Laby;
import net.labymod.api.client.GameTickProvider;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.gfx.pipeline.renderer.text.TextRenderer;
import net.labymod.api.client.render.RenderConstants;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.batch.RenderContexts;
import net.labymod.api.client.render.draw.CircleRenderer;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.font.text.TextRendererProvider;
import net.labymod.api.client.render.model.ModelService;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.api.client.resources.pack.ResourcePackRepository;
import net.labymod.api.event.EventBus;
import net.labymod.api.generated.ReferenceStorage;
import net.labymod.api.laby3d.Laby3D;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/DefaultAbstractRenderPipeline.class */
public abstract class DefaultAbstractRenderPipeline implements RenderPipeline {
    private final CircleRenderer circleRenderer;
    private final ComponentRenderer componentRenderer;
    private final RectangleRenderer rectangleRenderer;
    private final ResourceRenderer resourceRenderer;
    private final ResourcePackRepository resourcePackRepository;
    private final ResourcePack.Factory resourcePackFactory;
    private final ModelService modelService;
    private final RenderContexts renderContexts;
    private final RenderConstants renderConstants;
    private final Resources resources;
    private final GameTickProvider gameTickProvider;
    private boolean modifiedAlpha = true;
    private float alpha = 1.0f;
    private final TextRendererProvider textRendererProvider;

    public DefaultAbstractRenderPipeline() {
        ReferenceStorage references = Laby.references();
        this.textRendererProvider = references.textRendererProvider();
        this.renderConstants = references.renderConstants();
        this.renderContexts = references.renderContexts();
        this.resources = references.resources();
        this.circleRenderer = references.circleRenderer();
        this.componentRenderer = references.componentRenderer();
        this.rectangleRenderer = references.rectangleRenderer();
        this.resourceRenderer = references.resourceRenderer();
        this.resourcePackRepository = references.resourcePackRepository();
        this.resourcePackFactory = references.resourcePackFactory();
        this.modelService = references.modelService();
        this.gameTickProvider = references.gameTickProvider();
        EventBus eventBus = references.eventBus();
        eventBus.registerListener(this.gameTickProvider);
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public void setModifiedAlpha(boolean value) {
        this.modifiedAlpha = value;
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public float getAlpha() {
        if (this.modifiedAlpha) {
            return this.alpha;
        }
        return 1.0f;
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public void multiplyAlpha(float alpha) {
        setAlpha(this.alpha * alpha);
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public void multiplyAlpha(float alpha, Runnable context) {
        float prev = getAlpha();
        multiplyAlpha(alpha);
        context.run();
        setAlpha(prev);
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public void setAlpha(float alpha, Runnable context) {
        float prev = getAlpha();
        setAlpha(alpha);
        context.run();
        setAlpha(prev);
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public TextRenderer textRenderer() {
        return this.textRendererProvider.getRenderer();
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public ComponentRenderer componentRenderer() {
        return this.componentRenderer;
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public CircleRenderer circleRenderer() {
        return this.circleRenderer;
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public RectangleRenderer rectangleRenderer() {
        return this.rectangleRenderer;
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public ResourceRenderer resourceRenderer() {
        return this.resourceRenderer;
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public ResourcePackRepository resourcePackRepository() {
        return this.resourcePackRepository;
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public ResourcePack.Factory resourcePackFactory() {
        return this.resourcePackFactory;
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public ModelService modelService() {
        return this.modelService;
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public RenderContexts renderContexts() {
        return this.renderContexts;
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public RenderConstants renderConstants() {
        return this.renderConstants;
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public Resources resources() {
        return this.resources;
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public GameTickProvider gameTickProvider() {
        return this.gameTickProvider;
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public void renderSeeThrough(Entity entity, float seeThroughOpacity, Runnable renderer) {
        Laby3D laby3D = Laby.references().laby3D();
        laby3D.storeStates();
        boolean crouching = entity.isCrouching();
        if (!crouching) {
            renderer.run();
        }
        if (crouching) {
            multiplyAlpha(seeThroughOpacity, renderer);
        } else {
            multiplyAlpha(seeThroughOpacity, renderer);
        }
        laby3D.restoreStates();
    }

    @Override // net.labymod.api.client.render.RenderPipeline
    public void renderNoneStandardNameTag(Entity entity, Runnable renderer) {
        if (entity.isCrouching()) {
            return;
        }
        renderer.run();
    }
}
