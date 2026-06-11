package net.labymod.v1_16_5.mixins.client.entity.snapshot;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.render.state.entity.mutable.AbstractLiveEntitySnapshot;
import net.labymod.core.client.render.state.entity.mutable.LiveAvatarSnapshot;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/entity/snapshot/MixinAbstractClientPlayer_SnapshotAPI.class */
@Mixin({dzj.class})
public class MixinAbstractClientPlayer_SnapshotAPI extends MixinLivingEntity_SnapshotAPI<Player> {
    @Override // net.labymod.v1_16_5.mixins.client.entity.snapshot.MixinLivingEntity_SnapshotAPI, net.labymod.v1_16_5.mixins.client.entity.snapshot.MixinEntity_SnapshotAPI, net.labymod.core.client.render.state.entity.EntitySnapshotCreator
    public AbstractLiveEntitySnapshot<Player> createSnapshot(float partialTicks) {
        return new LiveAvatarSnapshot((Player) CastUtil.cast(this), partialTicks);
    }
}
