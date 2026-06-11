package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ItemShulkerBoxColorFix.class */
public class ItemShulkerBoxColorFix extends DataFix {
    public static final String[] NAMES_BY_COLOR = {"minecraft:white_shulker_box", "minecraft:orange_shulker_box", "minecraft:magenta_shulker_box", "minecraft:light_blue_shulker_box", "minecraft:yellow_shulker_box", "minecraft:lime_shulker_box", "minecraft:pink_shulker_box", "minecraft:gray_shulker_box", "minecraft:silver_shulker_box", "minecraft:cyan_shulker_box", "minecraft:purple_shulker_box", "minecraft:blue_shulker_box", "minecraft:brown_shulker_box", "minecraft:green_shulker_box", "minecraft:red_shulker_box", "minecraft:black_shulker_box"};

    public ItemShulkerBoxColorFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    public TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.ITEM_STACK);
        OpticFinder<Pair<String, String>> $$1 = DSL.fieldFinder(Entity.TAG_ID, DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
        OpticFinder<?> $$2 = $$0.findField("tag");
        OpticFinder<?> $$3 = $$2.type().findField("BlockEntityTag");
        return fixTypeEverywhereTyped("ItemShulkerBoxColorFix", $$0, $$32 -> {
            Optional<Pair<String, String>> $$4 = $$32.getOptional($$1);
            if ($$4.isPresent() && Objects.equals($$4.get().getSecond(), "minecraft:shulker_box")) {
                Optional<? extends Typed<?>> $$5 = $$32.getOptionalTyped($$2);
                if ($$5.isPresent()) {
                    Typed<?> $$6 = $$5.get();
                    Optional<? extends Typed<?>> $$7 = $$6.getOptionalTyped($$3);
                    if ($$7.isPresent()) {
                        Typed<?> $$8 = $$7.get();
                        Dynamic<?> $$9 = (Dynamic) $$8.get(DSL.remainderFinder());
                        int $$10 = $$9.get("Color").asInt(0);
                        $$9.remove("Color");
                        return $$32.set($$2, $$6.set($$3, $$8.set(DSL.remainderFinder(), $$9))).set($$1, Pair.of(References.ITEM_NAME.typeName(), NAMES_BY_COLOR[$$10 % 16]));
                    }
                }
            }
            return $$32;
        });
    }
}
