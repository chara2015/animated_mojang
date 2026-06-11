package net.labymod.api.client.entity.player;

import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/OptiFinePlayer.class */
public interface OptiFinePlayer {
    @Nullable
    ResourceLocation getOptiFineCapeLocation();

    void bridge$optifine$setReloadCapeTime(long j);
}
