package net.labymod.api.client.render.state.entity;

import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.laby3d.renderer.snapshot.LabySnapshot;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/state/entity/CustomAvatarDataSnapshot.class */
public interface CustomAvatarDataSnapshot extends LabySnapshot {
    @Nullable
    NetworkPlayerInfo playerInfo();
}
