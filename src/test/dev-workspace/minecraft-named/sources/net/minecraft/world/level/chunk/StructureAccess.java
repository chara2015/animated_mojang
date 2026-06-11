package net.minecraft.world.level.chunk;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Map;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/StructureAccess.class */
public interface StructureAccess {
    StructureStart getStartForStructure(Structure structure);

    void setStartForStructure(Structure structure, StructureStart structureStart);

    LongSet getReferencesForStructure(Structure structure);

    void addReferenceForStructure(Structure structure, long j);

    Map<Structure, LongSet> getAllReferences();

    void setAllReferences(Map<Structure, LongSet> map);
}
