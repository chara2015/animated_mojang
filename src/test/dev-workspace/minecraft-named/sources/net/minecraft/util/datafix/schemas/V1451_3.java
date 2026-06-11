package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.ContainerHelper;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V1451_3.class */
public class V1451_3 extends NamespacedSchema {
    public V1451_3(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
        $$0.registerSimple($$1, "minecraft:egg");
        $$0.registerSimple($$1, "minecraft:ender_pearl");
        $$0.registerSimple($$1, "minecraft:fireball");
        $$0.register($$1, "minecraft:potion", $$12 -> {
            return DSL.optionalFields("Potion", References.ITEM_STACK.in($$0));
        });
        $$0.registerSimple($$1, "minecraft:small_fireball");
        $$0.registerSimple($$1, "minecraft:snowball");
        $$0.registerSimple($$1, "minecraft:wither_skull");
        $$0.registerSimple($$1, "minecraft:xp_bottle");
        $$0.register($$1, "minecraft:arrow", () -> {
            return DSL.optionalFields("inBlockState", References.BLOCK_STATE.in($$0));
        });
        $$0.register($$1, "minecraft:enderman", () -> {
            return DSL.optionalFields("carriedBlockState", References.BLOCK_STATE.in($$0));
        });
        $$0.register($$1, "minecraft:falling_block", () -> {
            return DSL.optionalFields("BlockState", References.BLOCK_STATE.in($$0), "TileEntityData", References.BLOCK_ENTITY.in($$0));
        });
        $$0.register($$1, "minecraft:spectral_arrow", () -> {
            return DSL.optionalFields("inBlockState", References.BLOCK_STATE.in($$0));
        });
        $$0.register($$1, "minecraft:chest_minecart", () -> {
            return DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0), ContainerHelper.TAG_ITEMS, DSL.list(References.ITEM_STACK.in($$0)));
        });
        $$0.register($$1, "minecraft:commandblock_minecart", () -> {
            return DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0), "LastOutput", References.TEXT_COMPONENT.in($$0));
        });
        $$0.register($$1, "minecraft:furnace_minecart", () -> {
            return DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0));
        });
        $$0.register($$1, "minecraft:hopper_minecart", () -> {
            return DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0), ContainerHelper.TAG_ITEMS, DSL.list(References.ITEM_STACK.in($$0)));
        });
        $$0.register($$1, "minecraft:minecart", () -> {
            return DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0));
        });
        $$0.register($$1, "minecraft:spawner_minecart", () -> {
            return DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0), References.UNTAGGED_SPAWNER.in($$0));
        });
        $$0.register($$1, "minecraft:tnt_minecart", () -> {
            return DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0));
        });
        return $$1;
    }
}
