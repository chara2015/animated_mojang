package net.labymod.api.client.world.block.properties;

import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/block/properties/BlockPropertyHolder.class */
public interface BlockPropertyHolder {
    boolean hasProperty(BlockProperty blockProperty);

    @Nullable
    Integer getInt(BlockProperty blockProperty);

    @Nullable
    Boolean getBoolean(BlockProperty blockProperty);

    <E extends Enum<E>> E getEnum(BlockProperty blockProperty);
}
