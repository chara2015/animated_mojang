package net.labymod.core.client.render.state.entity;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.state.EntityExtraKeys;
import net.labymod.api.client.render.state.entity.CustomAvatarDataSnapshot;
import net.labymod.api.laby3d.renderer.snapshot.Extras;
import net.labymod.api.laby3d.renderer.snapshot.LabySnapshotFactory;
import net.labymod.api.service.annotation.AutoService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/state/entity/CustomAvatarDataSnapshotFactory.class */
@AutoService(LabySnapshotFactory.class)
public class CustomAvatarDataSnapshotFactory extends LabySnapshotFactory<Player, CustomAvatarDataSnapshot> {
    public CustomAvatarDataSnapshotFactory() {
        super(EntityExtraKeys.CUSTOM_AVATAR_DATA);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.labymod.api.laby3d.renderer.snapshot.LabySnapshotFactory
    public CustomAvatarDataSnapshot create(Player player, Extras extras) {
        NetworkPlayerInfo playerInfo = player.getNetworkPlayerInfo();
        return new DefaultCustomAvatarDataSnapshot(playerInfo == null ? null : playerInfo.toImmutable(), extras);
    }
}
