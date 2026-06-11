package net.labymod.api.client.render.state;

import net.labymod.api.client.render.state.entity.CustomAvatarDataSnapshot;
import net.labymod.api.client.render.state.entity.GameUserSnapshot;
import net.labymod.api.laby3d.renderer.snapshot.ExtraKey;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/state/EntityExtraKeys.class */
public final class EntityExtraKeys {
    public static final ExtraKey<CustomAvatarDataSnapshot> CUSTOM_AVATAR_DATA = ExtraKey.of("custom_avatar_data", CustomAvatarDataSnapshot.class);
    public static final ExtraKey<GameUserSnapshot> GAME_USER = ExtraKey.of("game_user", GameUserSnapshot.class);
}
