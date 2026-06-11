package net.minecraft.world.level.levelgen.structure;

import java.util.Optional;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/SinglePieceStructure.class */
public abstract class SinglePieceStructure extends Structure {
    private final PieceConstructor constructor;
    private final int width;
    private final int depth;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/SinglePieceStructure$PieceConstructor.class */
    @FunctionalInterface
    protected interface PieceConstructor {
        StructurePiece construct(WorldgenRandom worldgenRandom, int i, int i2);
    }

    protected SinglePieceStructure(PieceConstructor $$0, int $$1, int $$2, Structure.StructureSettings $$3) {
        super($$3);
        this.constructor = $$0;
        this.width = $$1;
        this.depth = $$2;
    }

    @Override // net.minecraft.world.level.levelgen.structure.Structure
    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext $$0) {
        if (getLowestY($$0, this.width, this.depth) < $$0.chunkGenerator().getSeaLevel()) {
            return Optional.empty();
        }
        return onTopOfChunkCenter($$0, Heightmap.Types.WORLD_SURFACE_WG, $$1 -> {
            generatePieces($$1, $$0);
        });
    }

    private void generatePieces(StructurePiecesBuilder $$0, Structure.GenerationContext $$1) {
        ChunkPos $$2 = $$1.chunkPos();
        $$0.addPiece(this.constructor.construct($$1.random(), $$2.getMinBlockX(), $$2.getMinBlockZ()));
    }
}
