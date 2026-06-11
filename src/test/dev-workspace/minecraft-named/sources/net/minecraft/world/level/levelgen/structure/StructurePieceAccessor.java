package net.minecraft.world.level.levelgen.structure;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/StructurePieceAccessor.class */
public interface StructurePieceAccessor {
    void addPiece(StructurePiece structurePiece);

    StructurePiece findCollisionPiece(BoundingBox boundingBox);
}
