package net.minecraft.server.level;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/level/FullChunkStatus.class */
public enum FullChunkStatus {
    INACCESSIBLE,
    FULL,
    BLOCK_TICKING,
    ENTITY_TICKING;

    public boolean isOrAfter(FullChunkStatus $$0) {
        return ordinal() >= $$0.ordinal();
    }
}
