package net.labymod.api.client.world;

import java.util.UUID;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/BossBar.class */
public interface BossBar {
    @NotNull
    UUID getIdentifier();

    Component displayName();

    BossBarColor bossBarColor();

    BossBarOverlay bossBarOverlay();

    BossBarProgressHandler progressHandler();
}
