package net.minecraft.world.level.levelgen.structure.templatesystem;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/templatesystem/StructureProcessor.class */
public abstract class StructureProcessor {
    protected abstract StructureProcessorType<?> getType();

    public StructureTemplate.StructureBlockInfo processBlock(LevelReader $$0, BlockPos $$1, BlockPos $$2, StructureTemplate.StructureBlockInfo $$3, StructureTemplate.StructureBlockInfo $$4, StructurePlaceSettings $$5) {
        return $$4;
    }

    public List<StructureTemplate.StructureBlockInfo> finalizeProcessing(ServerLevelAccessor $$0, BlockPos $$1, BlockPos $$2, List<StructureTemplate.StructureBlockInfo> $$3, List<StructureTemplate.StructureBlockInfo> $$4, StructurePlaceSettings $$5) {
        return $$4;
    }
}
