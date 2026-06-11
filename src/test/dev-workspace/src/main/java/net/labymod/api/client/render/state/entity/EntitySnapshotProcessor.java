package net.labymod.api.client.render.state.entity;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.laby3d.renderer.snapshot.ExtrasWriter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/state/entity/EntitySnapshotProcessor.class */
public abstract class EntitySnapshotProcessor<E extends Entity> {
    private final EntitySnapshotRegistry registry;

    public abstract boolean supports(Entity entity);

    public abstract void process(E e, float f, ExtrasWriter extrasWriter);

    protected EntitySnapshotProcessor(EntitySnapshotRegistry registry) {
        this.registry = registry;
    }

    protected EntitySnapshotRegistry registry() {
        return this.registry;
    }
}
