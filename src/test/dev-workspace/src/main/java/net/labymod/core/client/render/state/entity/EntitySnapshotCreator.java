package net.labymod.core.client.render.state.entity;

import net.labymod.api.client.entity.Entity;
import net.labymod.core.client.render.state.entity.mutable.AbstractLiveEntitySnapshot;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/state/entity/EntitySnapshotCreator.class */
public interface EntitySnapshotCreator<E extends Entity> {
    AbstractLiveEntitySnapshot<E> createSnapshot(float f);
}
