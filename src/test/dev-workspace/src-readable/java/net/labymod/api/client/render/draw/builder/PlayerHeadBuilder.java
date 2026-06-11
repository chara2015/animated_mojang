package net.labymod.api.client.render.draw.builder;

import java.util.UUID;
import net.labymod.api.client.render.draw.builder.PlayerHeadBuilder;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.mojang.GameProfile;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/builder/PlayerHeadBuilder.class */
public interface PlayerHeadBuilder<T extends PlayerHeadBuilder<T>> extends RectangleBuilder<T> {
    T player(UUID uuid);

    T player(GameProfile gameProfile);

    T player(ResourceLocation resourceLocation);

    T wearingHat(boolean z);
}
