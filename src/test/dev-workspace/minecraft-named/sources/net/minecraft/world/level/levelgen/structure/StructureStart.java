package net.minecraft.world.level.levelgen.structure;

import com.mojang.logging.LogUtils;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentStructure;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/StructureStart.class */
public final class StructureStart {
    public static final String INVALID_START_ID = "INVALID";
    public static final StructureStart INVALID_START = new StructureStart(null, new ChunkPos(0, 0), 0, new PiecesContainer(List.of()));
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Structure structure;
    private final PiecesContainer pieceContainer;
    private final ChunkPos chunkPos;
    private int references;
    private volatile BoundingBox cachedBoundingBox;

    public StructureStart(Structure $$0, ChunkPos $$1, int $$2, PiecesContainer $$3) {
        this.structure = $$0;
        this.chunkPos = $$1;
        this.references = $$2;
        this.pieceContainer = $$3;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static StructureStart loadStaticStart(StructurePieceSerializationContext $$0, CompoundTag $$1, long $$2) throws MatchException {
        String $$3 = $$1.getStringOr(Entity.TAG_ID, "");
        if (INVALID_START_ID.equals($$3)) {
            return INVALID_START;
        }
        Registry<Structure> $$4 = $$0.registryAccess().lookupOrThrow((ResourceKey) Registries.STRUCTURE);
        Structure $$5 = $$4.getValue(Identifier.parse($$3));
        if ($$5 == null) {
            LOGGER.error("Unknown stucture id: {}", $$3);
            return null;
        }
        ChunkPos $$6 = new ChunkPos($$1.getIntOr("ChunkX", 0), $$1.getIntOr("ChunkZ", 0));
        int $$7 = $$1.getIntOr("references", 0);
        ListTag $$8 = $$1.getListOrEmpty("Children");
        try {
            PiecesContainer $$9 = PiecesContainer.load($$8, $$0);
            if ($$5 instanceof OceanMonumentStructure) {
                $$9 = OceanMonumentStructure.regeneratePiecesAfterLoad($$6, $$2, $$9);
            }
            return new StructureStart($$5, $$6, $$7, $$9);
        } catch (Exception $$10) {
            LOGGER.error("Failed Start with id {}", $$3, $$10);
            return null;
        }
    }

    public BoundingBox getBoundingBox() {
        BoundingBox $$0 = this.cachedBoundingBox;
        if ($$0 == null) {
            $$0 = this.structure.adjustBoundingBox(this.pieceContainer.calculateBoundingBox());
            this.cachedBoundingBox = $$0;
        }
        return $$0;
    }

    public void placeInChunk(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5) {
        List<StructurePiece> $$6 = this.pieceContainer.pieces();
        if ($$6.isEmpty()) {
            return;
        }
        BoundingBox $$7 = $$6.get(0).boundingBox;
        BlockPos $$8 = $$7.getCenter();
        BlockPos $$9 = new BlockPos($$8.getX(), $$7.minY(), $$8.getZ());
        for (StructurePiece $$10 : $$6) {
            if ($$10.getBoundingBox().intersects($$4)) {
                $$10.postProcess($$0, $$1, $$2, $$3, $$4, $$5, $$9);
            }
        }
        this.structure.afterPlace($$0, $$1, $$2, $$3, $$4, $$5, this.pieceContainer);
    }

    public CompoundTag createTag(StructurePieceSerializationContext $$0, ChunkPos $$1) {
        CompoundTag $$2 = new CompoundTag();
        if (isValid()) {
            $$2.putString(Entity.TAG_ID, $$0.registryAccess().lookupOrThrow((ResourceKey) Registries.STRUCTURE).getKey(this.structure).toString());
            $$2.putInt("ChunkX", $$1.x);
            $$2.putInt("ChunkZ", $$1.z);
            $$2.putInt("references", this.references);
            $$2.put("Children", this.pieceContainer.save($$0));
            return $$2;
        }
        $$2.putString(Entity.TAG_ID, INVALID_START_ID);
        return $$2;
    }

    public boolean isValid() {
        return !this.pieceContainer.isEmpty();
    }

    public ChunkPos getChunkPos() {
        return this.chunkPos;
    }

    public boolean canBeReferenced() {
        return this.references < getMaxReferences();
    }

    public void addReference() {
        this.references++;
    }

    public int getReferences() {
        return this.references;
    }

    protected int getMaxReferences() {
        return 1;
    }

    public Structure getStructure() {
        return this.structure;
    }

    public List<StructurePiece> getPieces() {
        return this.pieceContainer.pieces();
    }
}
