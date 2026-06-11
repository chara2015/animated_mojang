package net.labymod.core.client.render.state.entity.mutable;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.event.client.render.state.EntityRenderStateCreationEvent;
import net.labymod.api.laby3d.renderer.snapshot.Extras;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/state/entity/mutable/LiveEntitySnapshot.class */
public class LiveEntitySnapshot<E extends Entity> extends AbstractLiveEntitySnapshot<E> {
    protected final E entity;
    protected final float partialTicks;
    private final Extras extras;
    private final int lightCoords;

    public LiveEntitySnapshot(E entity, float partialTicks) {
        this.entity = entity;
        Extras.Builder builder = Extras.builder();
        Laby.fireEvent(new EntityRenderStateCreationEvent(entity, partialTicks, builder));
        this.extras = builder.build();
        this.partialTicks = partialTicks;
        this.lightCoords = Laby.labyAPI().minecraft().clientWorld().getPackedLight(x(), y() + ((double) this.entity.getEyeHeight()), z());
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public double x() {
        return this.entity.position().lerpX(this.entity.previousPosition(), this.partialTicks);
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public double y() {
        return this.entity.position().lerpY(this.entity.previousPosition(), this.partialTicks);
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public double z() {
        return this.entity.position().lerpZ(this.entity.previousPosition(), this.partialTicks);
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public float eyeHeight() {
        return this.entity.getEyeHeight();
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public boolean isInvisible() {
        return this.entity.isInvisible();
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public boolean isDiscrete() {
        return this.entity.isCrouching();
    }

    @Override // net.labymod.api.client.render.state.entity.EntitySnapshot
    public int lightCoords() {
        return this.lightCoords;
    }

    @Override // net.labymod.api.laby3d.renderer.snapshot.LabySnapshot
    public Extras extras() {
        return this.extras;
    }
}
