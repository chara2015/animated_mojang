package net.labymod.api.user;

import java.util.UUID;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.user.group.Group;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/user/GameUserProfile.class */
public interface GameUserProfile {
    @NotNull
    UUID getUuid();

    @NotNull
    WhitelistState whitelistState();

    @NotNull
    Group visibleGroup();

    @NotNull
    TextColor displayColor();
}
