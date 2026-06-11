package net.labymod.core.client.render.state.entity.mutable;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.state.entity.AvatarSnapshot;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/state/entity/mutable/LiveAvatarSnapshot.class */
public class LiveAvatarSnapshot extends LiveLivingEntitySnapshot<Player> implements AvatarSnapshot {
    public LiveAvatarSnapshot(Player entity, float partialTicks) {
        super(entity, partialTicks);
    }
}
