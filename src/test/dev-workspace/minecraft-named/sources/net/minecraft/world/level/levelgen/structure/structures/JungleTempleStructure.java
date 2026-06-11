package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/structures/JungleTempleStructure.class */
public class JungleTempleStructure extends SinglePieceStructure {
    public static final MapCodec<JungleTempleStructure> CODEC = simpleCodec(JungleTempleStructure::new);

    public JungleTempleStructure(Structure.StructureSettings $$0) {
        super((v1, v2, v3) -> {
            return new JungleTemplePiece(v1, v2, v3);
        }, 12, 15, $$0);
    }

    @Override // net.minecraft.world.level.levelgen.structure.Structure
    public StructureType<?> type() {
        return StructureType.JUNGLE_TEMPLE;
    }
}
