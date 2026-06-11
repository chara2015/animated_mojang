package net.labymod.v1_19_4.mixins.client.entity.snapshot;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.render.state.entity.EntitySnapshotCreator;
import net.labymod.core.client.render.state.entity.mutable.AbstractLiveEntitySnapshot;
import net.labymod.core.client.render.state.entity.mutable.LiveEntitySnapshot;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/entity/snapshot/MixinEntity_SnapshotAPI.class */
@Mixin({bfh.class})
public class MixinEntity_SnapshotAPI<E extends Entity> implements EntitySnapshotCreator<E> {
    @Override // net.labymod.core.client.render.state.entity.EntitySnapshotCreator
    public AbstractLiveEntitySnapshot<E> createSnapshot(float partialTicks) {
        return new LiveEntitySnapshot((Entity) CastUtil.cast(this), partialTicks);
    }
}
