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
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Optional;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.LegacyComponentDataFixUtils;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/BannerEntityCustomNameToOverrideComponentFix.class */
public class BannerEntityCustomNameToOverrideComponentFix extends DataFix {
    public BannerEntityCustomNameToOverrideComponentFix(Schema $$0) {
        super($$0, false);
    }

    public TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.BLOCK_ENTITY);
        TaggedChoice.TaggedChoiceType<?> $$1 = getInputSchema().findChoiceType(References.BLOCK_ENTITY);
        OpticFinder<?> $$2 = $$0.findField(Entity.TAG_CUSTOM_NAME);
        OpticFinder<Pair<String, String>> $$3 = DSL.typeFinder(getInputSchema().getType(References.TEXT_COMPONENT));
        return fixTypeEverywhereTyped("Banner entity custom_name to item_name component fix", $$0, $$32 -> {
            Object $$4 = ((Pair) $$32.get($$1.finder())).getFirst();
            return $$4.equals("minecraft:banner") ? fix($$32, $$3, $$2) : $$32;
        });
    }

    private Typed<?> fix(Typed<?> $$0, OpticFinder<Pair<String, String>> $$1, OpticFinder<?> $$2) {
        Optional<String> $$3 = $$0.getOptionalTyped($$2).flatMap($$12 -> {
            return $$12.getOptional($$1).map((v0) -> {
                return v0.getSecond();
            });
        });
        boolean $$4 = $$3.flatMap(LegacyComponentDataFixUtils::extractTranslationString).filter($$02 -> {
            return $$02.equals("block.minecraft.ominous_banner");
        }).isPresent();
        if ($$4) {
            return Util.writeAndReadTypedOrThrow($$0, $$0.getType(), $$13 -> {
                Dynamic<?> $$22 = $$13.createMap(Map.of($$13.createString("minecraft:item_name"), $$13.createString((String) $$3.get()), $$13.createString("minecraft:hide_additional_tooltip"), $$13.emptyMap()));
                return $$13.set("components", $$22).remove(Entity.TAG_CUSTOM_NAME);
            });
        }
        return $$0;
    }
}
