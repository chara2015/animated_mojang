package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.Set;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.ExtraDataFixUtils;
import net.minecraft.util.datafix.LegacyComponentDataFixUtils;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/BlockEntityCustomNameToComponentFix.class */
public class BlockEntityCustomNameToComponentFix extends DataFix {
    private static final Set<String> NAMEABLE_BLOCK_ENTITIES = Set.of((Object[]) new String[]{"minecraft:beacon", "minecraft:banner", "minecraft:brewing_stand", "minecraft:chest", "minecraft:trapped_chest", "minecraft:dispenser", "minecraft:dropper", "minecraft:enchanting_table", "minecraft:furnace", "minecraft:hopper", "minecraft:shulker_box"});

    public BlockEntityCustomNameToComponentFix(Schema $$0) {
        super($$0, true);
    }

    public TypeRewriteRule makeRule() {
        OpticFinder<String> $$0 = DSL.fieldFinder(Entity.TAG_ID, NamespacedSchema.namespacedString());
        Type<?> $$1 = getInputSchema().getType(References.BLOCK_ENTITY);
        Type<?> $$2 = getOutputSchema().getType(References.BLOCK_ENTITY);
        Type<?> $$3 = ExtraDataFixUtils.patchSubType($$1, $$1, $$2);
        return fixTypeEverywhereTyped("BlockEntityCustomNameToComponentFix", $$1, $$2, $$32 -> {
            Optional<String> $$4 = $$32.getOptional($$0);
            if ($$4.isPresent() && !NAMEABLE_BLOCK_ENTITIES.contains($$4.get())) {
                return ExtraDataFixUtils.cast($$2, $$32);
            }
            return Util.writeAndReadTypedOrThrow(ExtraDataFixUtils.cast($$3, $$32), $$2, BlockEntityCustomNameToComponentFix::fixTagCustomName);
        });
    }

    public static <T> Dynamic<T> fixTagCustomName(Dynamic<T> $$0) {
        String $$1 = $$0.get(Entity.TAG_CUSTOM_NAME).asString("");
        if ($$1.isEmpty()) {
            return $$0.remove(Entity.TAG_CUSTOM_NAME);
        }
        return $$0.set(Entity.TAG_CUSTOM_NAME, LegacyComponentDataFixUtils.createPlainTextComponent($$0.getOps(), $$1));
    }
}
