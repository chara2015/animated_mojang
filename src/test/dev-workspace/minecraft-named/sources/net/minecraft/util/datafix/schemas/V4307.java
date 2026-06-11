package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.SequencedMap;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V4307.class */
public class V4307 extends NamespacedSchema {
    public V4307(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public static SequencedMap<String, Supplier<TypeTemplate>> components(Schema $$0) {
        SequencedMap<String, Supplier<TypeTemplate>> $$1 = V4059.components($$0);
        $$1.put("minecraft:can_place_on", () -> {
            return adventureModePredicate($$0);
        });
        $$1.put("minecraft:can_break", () -> {
            return adventureModePredicate($$0);
        });
        return $$1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TypeTemplate adventureModePredicate(Schema $$0) {
        TypeTemplate $$1 = DSL.optionalFields(StructureTemplate.BLOCKS_TAG, DSL.or(References.BLOCK_NAME.in($$0), DSL.list(References.BLOCK_NAME.in($$0))));
        return DSL.or($$1, DSL.list($$1));
    }

    public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
        super.registerTypes($$0, $$1, $$2);
        $$0.registerType(true, References.DATA_COMPONENTS, () -> {
            return DSL.optionalFieldsLazy(components($$0));
        });
    }
}
