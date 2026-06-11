package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.datafix.LegacyComponentDataFixUtils;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/OminousBannerRarityFix.class */
public class OminousBannerRarityFix extends DataFix {
    public OminousBannerRarityFix(Schema $$0) {
        super($$0, false);
    }

    public TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.BLOCK_ENTITY);
        Type<?> $$1 = getInputSchema().getType(References.ITEM_STACK);
        TaggedChoice.TaggedChoiceType<?> $$2 = getInputSchema().findChoiceType(References.BLOCK_ENTITY);
        OpticFinder<Pair<String, String>> $$3 = DSL.fieldFinder(Entity.TAG_ID, DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
        OpticFinder<?> $$4 = $$0.findField("components");
        OpticFinder<?> $$5 = $$1.findField("components");
        OpticFinder<?> $$6 = $$4.type().findField("minecraft:item_name");
        OpticFinder<Pair<String, String>> $$7 = DSL.typeFinder(getInputSchema().getType(References.TEXT_COMPONENT));
        return TypeRewriteRule.seq(fixTypeEverywhereTyped("Ominous Banner block entity common rarity to uncommon rarity fix", $$0, $$42 -> {
            Object $$52 = ((Pair) $$42.get($$2.finder())).getFirst();
            return $$52.equals("minecraft:banner") ? fix($$42, $$4, $$6, $$7) : $$42;
        }), fixTypeEverywhereTyped("Ominous Banner item stack common rarity to uncommon rarity fix", $$1, $$43 -> {
            String $$52 = (String) $$43.getOptional($$3).map((v0) -> {
                return v0.getSecond();
            }).orElse("");
            return $$52.equals("minecraft:white_banner") ? fix($$43, $$5, $$6, $$7) : $$43;
        }));
    }

    private Typed<?> fix(Typed<?> $$0, OpticFinder<?> $$1, OpticFinder<?> $$2, OpticFinder<Pair<String, String>> $$3) {
        return $$0.updateTyped($$1, $$22 -> {
            boolean $$32 = $$22.getOptionalTyped($$2).flatMap($$12 -> {
                return $$12.getOptional($$3);
            }).map((v0) -> {
                return v0.getSecond();
            }).flatMap(LegacyComponentDataFixUtils::extractTranslationString).filter($$02 -> {
                return $$02.equals("block.minecraft.ominous_banner");
            }).isPresent();
            if ($$32) {
                return $$22.updateTyped($$2, $$13 -> {
                    return $$13.set($$3, Pair.of(References.TEXT_COMPONENT.typeName(), LegacyComponentDataFixUtils.createTranslatableComponentJson("block.minecraft.ominous_banner")));
                }).update(DSL.remainderFinder(), $$03 -> {
                    return $$03.set("minecraft:rarity", $$03.createString("uncommon"));
                });
            }
            return $$22;
        });
    }
}
