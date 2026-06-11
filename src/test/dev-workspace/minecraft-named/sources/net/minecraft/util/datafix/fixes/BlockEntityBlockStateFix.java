package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/BlockEntityBlockStateFix.class */
public class BlockEntityBlockStateFix extends NamedEntityFix {
    public BlockEntityBlockStateFix(Schema $$0, boolean $$1) {
        super($$0, $$1, "BlockEntityBlockStateFix", References.BLOCK_ENTITY, "minecraft:piston");
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityFix
    protected Typed<?> fix(Typed<?> $$0) {
        Type<?> $$1 = getOutputSchema().getChoiceType(References.BLOCK_ENTITY, "minecraft:piston");
        Type<?> $$2 = $$1.findFieldType("blockState");
        OpticFinder<?> $$3 = DSL.fieldFinder("blockState", $$2);
        Dynamic<?> $$4 = (Dynamic) $$0.get(DSL.remainderFinder());
        int $$5 = $$4.get("blockId").asInt(0);
        Dynamic<?> $$42 = $$4.remove("blockId");
        int $$6 = $$42.get("blockData").asInt(0) & 15;
        Dynamic<?> $$43 = $$42.remove("blockData");
        Dynamic<?> $$7 = BlockStateData.getTag(($$5 << 4) | $$6);
        Typed<?> $$8 = (Typed) $$1.pointTyped($$0.getOps()).orElseThrow(() -> {
            return new IllegalStateException("Could not create new piston block entity.");
        });
        return $$8.set(DSL.remainderFinder(), $$43).set($$3, (Typed) ((Pair) $$2.readTyped($$7).result().orElseThrow(() -> {
            return new IllegalStateException("Could not parse newly created block state tag.");
        })).getFirst());
    }
}
