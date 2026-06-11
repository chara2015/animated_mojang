package net.minecraft.world.level.levelgen.structure.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TripWireBlock;
import net.minecraft.world.level.block.TripWireHookBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/structures/JungleTemplePiece.class */
public class JungleTemplePiece extends ScatteredFeaturePiece {
    public static final int WIDTH = 12;
    public static final int DEPTH = 15;
    private boolean placedMainChest;
    private boolean placedHiddenChest;
    private boolean placedTrap1;
    private boolean placedTrap2;
    private static final MossStoneSelector STONE_SELECTOR = new MossStoneSelector();

    public JungleTemplePiece(RandomSource $$0, int $$1, int $$2) {
        super(StructurePieceType.JUNGLE_PYRAMID_PIECE, $$1, 64, $$2, 12, 10, 15, getRandomHorizontalDirection($$0));
    }

    public JungleTemplePiece(CompoundTag $$0) {
        super(StructurePieceType.JUNGLE_PYRAMID_PIECE, $$0);
        this.placedMainChest = $$0.getBooleanOr("placedMainChest", false);
        this.placedHiddenChest = $$0.getBooleanOr("placedHiddenChest", false);
        this.placedTrap1 = $$0.getBooleanOr("placedTrap1", false);
        this.placedTrap2 = $$0.getBooleanOr("placedTrap2", false);
    }

    @Override // net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece, net.minecraft.world.level.levelgen.structure.StructurePiece
    protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
        super.addAdditionalSaveData($$0, $$1);
        $$1.putBoolean("placedMainChest", this.placedMainChest);
        $$1.putBoolean("placedHiddenChest", this.placedHiddenChest);
        $$1.putBoolean("placedTrap1", this.placedTrap1);
        $$1.putBoolean("placedTrap2", this.placedTrap2);
    }

    @Override // net.minecraft.world.level.levelgen.structure.StructurePiece
    public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
        if (!updateAverageGroundHeight($$0, $$4, 0)) {
            return;
        }
        generateBox($$0, $$4, 0, -4, 0, this.width - 1, 0, this.depth - 1, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 2, 1, 2, 9, 2, 2, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 2, 1, 12, 9, 2, 12, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 2, 1, 3, 2, 2, 11, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 9, 1, 3, 9, 2, 11, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 1, 3, 1, 10, 6, 1, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 1, 3, 13, 10, 6, 13, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 1, 3, 2, 1, 6, 12, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 10, 3, 2, 10, 6, 12, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 2, 3, 2, 9, 3, 12, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 2, 6, 2, 9, 6, 12, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 3, 7, 3, 8, 7, 11, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 4, 8, 4, 7, 8, 10, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateAirBox($$0, $$4, 3, 1, 3, 8, 2, 11);
        generateAirBox($$0, $$4, 4, 3, 6, 7, 3, 9);
        generateAirBox($$0, $$4, 2, 4, 2, 9, 5, 12);
        generateAirBox($$0, $$4, 4, 6, 5, 7, 6, 9);
        generateAirBox($$0, $$4, 5, 7, 6, 6, 7, 8);
        generateAirBox($$0, $$4, 5, 1, 2, 6, 2, 2);
        generateAirBox($$0, $$4, 5, 2, 12, 6, 2, 12);
        generateAirBox($$0, $$4, 5, 5, 1, 6, 5, 1);
        generateAirBox($$0, $$4, 5, 5, 13, 6, 5, 13);
        placeBlock($$0, Blocks.AIR.defaultBlockState(), 1, 5, 5, $$4);
        placeBlock($$0, Blocks.AIR.defaultBlockState(), 10, 5, 5, $$4);
        placeBlock($$0, Blocks.AIR.defaultBlockState(), 1, 5, 9, $$4);
        placeBlock($$0, Blocks.AIR.defaultBlockState(), 10, 5, 9, $$4);
        for (int $$7 = 0; $$7 <= 14; $$7 += 14) {
            generateBox($$0, $$4, 2, 4, $$7, 2, 5, $$7, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
            generateBox($$0, $$4, 4, 4, $$7, 4, 5, $$7, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
            generateBox($$0, $$4, 7, 4, $$7, 7, 5, $$7, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
            generateBox($$0, $$4, 9, 4, $$7, 9, 5, $$7, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        }
        generateBox($$0, $$4, 5, 6, 0, 6, 6, 0, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        for (int $$8 = 0; $$8 <= 11; $$8 += 11) {
            for (int $$9 = 2; $$9 <= 12; $$9 += 2) {
                generateBox($$0, $$4, $$8, 4, $$9, $$8, 5, $$9, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
            }
            generateBox($$0, $$4, $$8, 6, 5, $$8, 6, 5, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
            generateBox($$0, $$4, $$8, 6, 9, $$8, 6, 9, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        }
        generateBox($$0, $$4, 2, 7, 2, 2, 9, 2, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 9, 7, 2, 9, 9, 2, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 2, 7, 12, 2, 9, 12, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 9, 7, 12, 9, 9, 12, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 4, 9, 4, 4, 9, 4, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 7, 9, 4, 7, 9, 4, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 4, 9, 10, 4, 9, 10, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 7, 9, 10, 7, 9, 10, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 5, 9, 7, 6, 9, 7, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        BlockState $$10 = (BlockState) Blocks.COBBLESTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.EAST);
        BlockState $$11 = (BlockState) Blocks.COBBLESTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.WEST);
        BlockState $$12 = (BlockState) Blocks.COBBLESTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
        BlockState $$13 = (BlockState) Blocks.COBBLESTONE_STAIRS.defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);
        placeBlock($$0, $$13, 5, 9, 6, $$4);
        placeBlock($$0, $$13, 6, 9, 6, $$4);
        placeBlock($$0, $$12, 5, 9, 8, $$4);
        placeBlock($$0, $$12, 6, 9, 8, $$4);
        placeBlock($$0, $$13, 4, 0, 0, $$4);
        placeBlock($$0, $$13, 5, 0, 0, $$4);
        placeBlock($$0, $$13, 6, 0, 0, $$4);
        placeBlock($$0, $$13, 7, 0, 0, $$4);
        placeBlock($$0, $$13, 4, 1, 8, $$4);
        placeBlock($$0, $$13, 4, 2, 9, $$4);
        placeBlock($$0, $$13, 4, 3, 10, $$4);
        placeBlock($$0, $$13, 7, 1, 8, $$4);
        placeBlock($$0, $$13, 7, 2, 9, $$4);
        placeBlock($$0, $$13, 7, 3, 10, $$4);
        generateBox($$0, $$4, 4, 1, 9, 4, 1, 9, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 7, 1, 9, 7, 1, 9, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 4, 1, 10, 7, 2, 10, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 5, 4, 5, 6, 4, 5, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        placeBlock($$0, $$10, 4, 4, 5, $$4);
        placeBlock($$0, $$11, 7, 4, 5, $$4);
        for (int $$14 = 0; $$14 < 4; $$14++) {
            placeBlock($$0, $$12, 5, 0 - $$14, 6 + $$14, $$4);
            placeBlock($$0, $$12, 6, 0 - $$14, 6 + $$14, $$4);
            generateAirBox($$0, $$4, 5, 0 - $$14, 7 + $$14, 6, 0 - $$14, 9 + $$14);
        }
        generateAirBox($$0, $$4, 1, -3, 12, 10, -1, 13);
        generateAirBox($$0, $$4, 1, -3, 1, 3, -1, 13);
        generateAirBox($$0, $$4, 1, -3, 1, 9, -1, 5);
        for (int $$15 = 1; $$15 <= 13; $$15 += 2) {
            generateBox($$0, $$4, 1, -3, $$15, 1, -2, $$15, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        }
        for (int $$16 = 2; $$16 <= 12; $$16 += 2) {
            generateBox($$0, $$4, 1, -1, $$16, 3, -1, $$16, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        }
        generateBox($$0, $$4, 2, -2, 1, 5, -2, 1, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 7, -2, 1, 9, -2, 1, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 6, -3, 1, 6, -3, 1, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 6, -1, 1, 6, -1, 1, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        placeBlock($$0, (BlockState) ((BlockState) Blocks.TRIPWIRE_HOOK.defaultBlockState().setValue(TripWireHookBlock.FACING, Direction.EAST)).setValue(TripWireHookBlock.ATTACHED, true), 1, -3, 8, $$4);
        placeBlock($$0, (BlockState) ((BlockState) Blocks.TRIPWIRE_HOOK.defaultBlockState().setValue(TripWireHookBlock.FACING, Direction.WEST)).setValue(TripWireHookBlock.ATTACHED, true), 4, -3, 8, $$4);
        placeBlock($$0, (BlockState) ((BlockState) ((BlockState) Blocks.TRIPWIRE.defaultBlockState().setValue(TripWireBlock.EAST, true)).setValue(TripWireBlock.WEST, true)).setValue(TripWireBlock.ATTACHED, true), 2, -3, 8, $$4);
        placeBlock($$0, (BlockState) ((BlockState) ((BlockState) Blocks.TRIPWIRE.defaultBlockState().setValue(TripWireBlock.EAST, true)).setValue(TripWireBlock.WEST, true)).setValue(TripWireBlock.ATTACHED, true), 3, -3, 8, $$4);
        BlockState $$17 = (BlockState) ((BlockState) Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedStoneWireBlock.NORTH, RedstoneSide.SIDE)).setValue(RedStoneWireBlock.SOUTH, RedstoneSide.SIDE);
        placeBlock($$0, $$17, 5, -3, 7, $$4);
        placeBlock($$0, $$17, 5, -3, 6, $$4);
        placeBlock($$0, $$17, 5, -3, 5, $$4);
        placeBlock($$0, $$17, 5, -3, 4, $$4);
        placeBlock($$0, $$17, 5, -3, 3, $$4);
        placeBlock($$0, $$17, 5, -3, 2, $$4);
        placeBlock($$0, (BlockState) ((BlockState) Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedStoneWireBlock.NORTH, RedstoneSide.SIDE)).setValue(RedStoneWireBlock.WEST, RedstoneSide.SIDE), 5, -3, 1, $$4);
        placeBlock($$0, (BlockState) ((BlockState) Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedStoneWireBlock.EAST, RedstoneSide.SIDE)).setValue(RedStoneWireBlock.WEST, RedstoneSide.SIDE), 4, -3, 1, $$4);
        placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 3, -3, 1, $$4);
        if (!this.placedTrap1) {
            this.placedTrap1 = createDispenser($$0, $$4, $$3, 3, -2, 1, Direction.NORTH, BuiltInLootTables.JUNGLE_TEMPLE_DISPENSER);
        }
        placeBlock($$0, (BlockState) Blocks.VINE.defaultBlockState().setValue(VineBlock.SOUTH, true), 3, -2, 2, $$4);
        placeBlock($$0, (BlockState) ((BlockState) Blocks.TRIPWIRE_HOOK.defaultBlockState().setValue(TripWireHookBlock.FACING, Direction.NORTH)).setValue(TripWireHookBlock.ATTACHED, true), 7, -3, 1, $$4);
        placeBlock($$0, (BlockState) ((BlockState) Blocks.TRIPWIRE_HOOK.defaultBlockState().setValue(TripWireHookBlock.FACING, Direction.SOUTH)).setValue(TripWireHookBlock.ATTACHED, true), 7, -3, 5, $$4);
        placeBlock($$0, (BlockState) ((BlockState) ((BlockState) Blocks.TRIPWIRE.defaultBlockState().setValue(TripWireBlock.NORTH, true)).setValue(TripWireBlock.SOUTH, true)).setValue(TripWireBlock.ATTACHED, true), 7, -3, 2, $$4);
        placeBlock($$0, (BlockState) ((BlockState) ((BlockState) Blocks.TRIPWIRE.defaultBlockState().setValue(TripWireBlock.NORTH, true)).setValue(TripWireBlock.SOUTH, true)).setValue(TripWireBlock.ATTACHED, true), 7, -3, 3, $$4);
        placeBlock($$0, (BlockState) ((BlockState) ((BlockState) Blocks.TRIPWIRE.defaultBlockState().setValue(TripWireBlock.NORTH, true)).setValue(TripWireBlock.SOUTH, true)).setValue(TripWireBlock.ATTACHED, true), 7, -3, 4, $$4);
        placeBlock($$0, (BlockState) ((BlockState) Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedStoneWireBlock.EAST, RedstoneSide.SIDE)).setValue(RedStoneWireBlock.WEST, RedstoneSide.SIDE), 8, -3, 6, $$4);
        placeBlock($$0, (BlockState) ((BlockState) Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedStoneWireBlock.WEST, RedstoneSide.SIDE)).setValue(RedStoneWireBlock.SOUTH, RedstoneSide.SIDE), 9, -3, 6, $$4);
        placeBlock($$0, (BlockState) ((BlockState) Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedStoneWireBlock.NORTH, RedstoneSide.SIDE)).setValue(RedStoneWireBlock.SOUTH, RedstoneSide.UP), 9, -3, 5, $$4);
        placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 9, -3, 4, $$4);
        placeBlock($$0, $$17, 9, -2, 4, $$4);
        if (!this.placedTrap2) {
            this.placedTrap2 = createDispenser($$0, $$4, $$3, 9, -2, 3, Direction.WEST, BuiltInLootTables.JUNGLE_TEMPLE_DISPENSER);
        }
        placeBlock($$0, (BlockState) Blocks.VINE.defaultBlockState().setValue(VineBlock.EAST, true), 8, -1, 3, $$4);
        placeBlock($$0, (BlockState) Blocks.VINE.defaultBlockState().setValue(VineBlock.EAST, true), 8, -2, 3, $$4);
        if (!this.placedMainChest) {
            this.placedMainChest = createChest($$0, $$4, $$3, 8, -3, 3, BuiltInLootTables.JUNGLE_TEMPLE);
        }
        placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 9, -3, 2, $$4);
        placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 8, -3, 1, $$4);
        placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 4, -3, 5, $$4);
        placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 5, -2, 5, $$4);
        placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 5, -1, 5, $$4);
        placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 6, -3, 5, $$4);
        placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 7, -2, 5, $$4);
        placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 7, -1, 5, $$4);
        placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 8, -3, 5, $$4);
        generateBox($$0, $$4, 9, -1, 1, 9, -1, 5, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateAirBox($$0, $$4, 8, -3, 8, 10, -1, 10);
        placeBlock($$0, Blocks.CHISELED_STONE_BRICKS.defaultBlockState(), 8, -2, 11, $$4);
        placeBlock($$0, Blocks.CHISELED_STONE_BRICKS.defaultBlockState(), 9, -2, 11, $$4);
        placeBlock($$0, Blocks.CHISELED_STONE_BRICKS.defaultBlockState(), 10, -2, 11, $$4);
        BlockState $$18 = (BlockState) ((BlockState) Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACING, Direction.NORTH)).setValue(LeverBlock.FACE, AttachFace.WALL);
        placeBlock($$0, $$18, 8, -2, 12, $$4);
        placeBlock($$0, $$18, 9, -2, 12, $$4);
        placeBlock($$0, $$18, 10, -2, 12, $$4);
        generateBox($$0, $$4, 8, -3, 8, 8, -3, 10, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        generateBox($$0, $$4, 10, -3, 8, 10, -3, 10, false, $$3, (StructurePiece.BlockSelector) STONE_SELECTOR);
        placeBlock($$0, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 10, -2, 9, $$4);
        placeBlock($$0, $$17, 8, -2, 9, $$4);
        placeBlock($$0, $$17, 8, -2, 10, $$4);
        placeBlock($$0, (BlockState) ((BlockState) ((BlockState) ((BlockState) Blocks.REDSTONE_WIRE.defaultBlockState().setValue(RedStoneWireBlock.NORTH, RedstoneSide.SIDE)).setValue(RedStoneWireBlock.SOUTH, RedstoneSide.SIDE)).setValue(RedStoneWireBlock.EAST, RedstoneSide.SIDE)).setValue(RedStoneWireBlock.WEST, RedstoneSide.SIDE), 10, -1, 9, $$4);
        placeBlock($$0, (BlockState) Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.UP), 9, -2, 8, $$4);
        placeBlock($$0, (BlockState) Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.WEST), 10, -2, 8, $$4);
        placeBlock($$0, (BlockState) Blocks.STICKY_PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.WEST), 10, -1, 8, $$4);
        placeBlock($$0, (BlockState) Blocks.REPEATER.defaultBlockState().setValue(RepeaterBlock.FACING, Direction.NORTH), 10, -2, 10, $$4);
        if (!this.placedHiddenChest) {
            this.placedHiddenChest = createChest($$0, $$4, $$3, 9, -3, 10, BuiltInLootTables.JUNGLE_TEMPLE);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/structures/JungleTemplePiece$MossStoneSelector.class */
    static class MossStoneSelector extends StructurePiece.BlockSelector {
        MossStoneSelector() {
        }

        @Override // net.minecraft.world.level.levelgen.structure.StructurePiece.BlockSelector
        public void next(RandomSource $$0, int $$1, int $$2, int $$3, boolean $$4) {
            if ($$0.nextFloat() < 0.4f) {
                this.next = Blocks.COBBLESTONE.defaultBlockState();
            } else {
                this.next = Blocks.MOSSY_COBBLESTONE.defaultBlockState();
            }
        }
    }
}
