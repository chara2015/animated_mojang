package net.labymod.v1_20_4.mixins.client.entity.snapshot;

import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.render.state.entity.mutable.AbstractLiveEntitySnapshot;
import net.labymod.core.client.render.state.entity.mutable.LiveLivingEntitySnapshot;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/entity/snapshot/MixinLivingEntity_SnapshotAPI.class */
@Mixin({bml.class})
public class MixinLivingEntity_SnapshotAPI<E extends LivingEntity> extends MixinEntity_SnapshotAPI<E> {
    @Override // net.labymod.v1_20_4.mixins.client.entity.snapshot.MixinEntity_SnapshotAPI, net.labymod.core.client.render.state.entity.EntitySnapshotCreator
    public AbstractLiveEntitySnapshot<E> createSnapshot(float partialTicks) {
        return new LiveLivingEntitySnapshot((LivingEntity) CastUtil.cast(this), partialTicks);
    }
}
