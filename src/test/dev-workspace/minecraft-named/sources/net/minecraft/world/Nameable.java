package net.minecraft.world;

import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/Nameable.class */
public interface Nameable {
    Component getName();

    default String getPlainTextName() {
        return getName().getString();
    }

    default boolean hasCustomName() {
        return getCustomName() != null;
    }

    default Component getDisplayName() {
        return getName();
    }

    default Component getCustomName() {
        return null;
    }
}
