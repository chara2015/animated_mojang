package net.minecraft.world.level.levelgen.structure.templatesystem;

import java.util.List;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/templatesystem/StructureProcessorList.class */
public class StructureProcessorList {
    private final List<StructureProcessor> list;

    public StructureProcessorList(List<StructureProcessor> $$0) {
        this.list = $$0;
    }

    public List<StructureProcessor> list() {
        return this.list;
    }

    public String toString() {
        return "ProcessorList[" + String.valueOf(this.list) + "]";
    }
}
