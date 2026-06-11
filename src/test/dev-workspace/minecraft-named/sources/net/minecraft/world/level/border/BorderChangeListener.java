package net.minecraft.world.level.border;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/border/BorderChangeListener.class */
public interface BorderChangeListener {
    void onSetSize(WorldBorder worldBorder, double d);

    void onLerpSize(WorldBorder worldBorder, double d, double d2, long j, long j2);

    void onSetCenter(WorldBorder worldBorder, double d, double d2);

    void onSetWarningTime(WorldBorder worldBorder, int i);

    void onSetWarningBlocks(WorldBorder worldBorder, int i);

    void onSetDamagePerBlock(WorldBorder worldBorder, double d);

    void onSetSafeZone(WorldBorder worldBorder, double d);
}
