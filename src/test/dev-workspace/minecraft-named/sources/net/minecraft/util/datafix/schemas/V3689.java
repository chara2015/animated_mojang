package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.SpawnData;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V3689.class */
public class V3689 extends NamespacedSchema {
    public V3689(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
        $$0.registerSimple($$1, "minecraft:breeze");
        $$0.registerSimple($$1, "minecraft:wind_charge");
        $$0.registerSimple($$1, "minecraft:breeze_wind_charge");
        return $$1;
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema $$0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerBlockEntities($$0);
        $$0.register($$1, "minecraft:trial_spawner", () -> {
            return DSL.optionalFields("spawn_potentials", DSL.list(DSL.fields("data", DSL.fields(SpawnData.ENTITY_TAG, References.ENTITY_TREE.in($$0)))), "spawn_data", DSL.fields(SpawnData.ENTITY_TAG, References.ENTITY_TREE.in($$0)));
        });
        return $$1;
    }
}
