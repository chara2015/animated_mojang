package net.labymod.api.client.entity;

import net.labymod.api.client.gui.screen.ScreenContext;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/EntityRenderBuilder.class */
public class EntityRenderBuilder {
    private final EntityVisualizer visualizer;
    private final Entity entity;
    private float x;
    private float y;
    private float rotationX;
    private float rotationY;
    private float rotationZ;
    private boolean renderName;
    private float scale = 1.0f;
    private float headRotationStrength = -1.0f;
    private boolean clipToBounds = true;

    public EntityRenderBuilder(EntityVisualizer visualizer, Entity entity) {
        this.visualizer = visualizer;
        this.entity = entity;
    }

    public EntityRenderBuilder position(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public EntityRenderBuilder rotation(float rotationX, float rotationY, float rotationZ) {
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.rotationZ = rotationZ;
        return this;
    }

    public EntityRenderBuilder scale(float scale) {
        this.scale = scale;
        return this;
    }

    public EntityRenderBuilder headRotationStrength(float strength) {
        this.headRotationStrength = strength;
        return this;
    }

    public EntityRenderBuilder withName() {
        this.renderName = true;
        return this;
    }

    public EntityRenderBuilder noClip() {
        this.clipToBounds = false;
        return this;
    }

    public void submit(ScreenContext context) {
        this.visualizer.submitEntity(context, this.entity, this.x, this.y, this.rotationX, this.rotationY, this.rotationZ, this.scale, this.headRotationStrength, this.renderName, this.clipToBounds);
    }
}
