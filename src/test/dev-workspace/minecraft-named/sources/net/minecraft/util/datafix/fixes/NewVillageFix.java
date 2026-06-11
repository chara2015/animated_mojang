package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.CompoundList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/NewVillageFix.class */
public class NewVillageFix extends DataFix {
    public NewVillageFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    protected TypeRewriteRule makeRule() {
        CompoundList.CompoundListType<String, ?> $$0 = DSL.compoundList(DSL.string(), getInputSchema().getType(References.STRUCTURE_FEATURE));
        $$0.finder();
        return cap($$0);
    }

    private <SF> TypeRewriteRule cap(CompoundList.CompoundListType<String, SF> $$0) {
        Type<?> $$1 = getInputSchema().getType(References.CHUNK);
        Type<?> $$2 = getInputSchema().getType(References.STRUCTURE_FEATURE);
        OpticFinder<?> $$3 = $$1.findField("Level");
        OpticFinder<?> $$4 = $$3.type().findField("Structures");
        OpticFinder<?> $$5 = $$4.type().findField("Starts");
        OpticFinder<List<Pair<String, SF>>> $$6 = $$0.finder();
        return TypeRewriteRule.seq(fixTypeEverywhereTyped("NewVillageFix", $$1, $$42 -> {
            return $$42.updateTyped($$3, $$32 -> {
                return $$32.updateTyped($$4, $$22 -> {
                    return $$22.updateTyped($$5, $$12 -> {
                        return $$12.update($$6, $$02 -> {
                            return (List) $$02.stream().filter($$02 -> {
                                return !Objects.equals($$02.getFirst(), "Village");
                            }).map($$03 -> {
                                return $$03.mapFirst($$03 -> {
                                    return $$03.equals("New_Village") ? "Village" : $$03;
                                });
                            }).collect(Collectors.toList());
                        });
                    }).update(DSL.remainderFinder(), $$02 -> {
                        return $$02.update("References", $$02 -> {
                            Optional<? extends Dynamic<?>> $$13 = $$02.get("New_Village").result();
                            return ((Dynamic) DataFixUtils.orElse($$13.map($$14 -> {
                                return $$02.remove("New_Village").set("Village", $$14);
                            }), $$02)).remove("Village");
                        });
                    });
                });
            });
        }), fixTypeEverywhereTyped("NewVillageStartFix", $$2, $$02 -> {
            return $$02.update(DSL.remainderFinder(), $$02 -> {
                return $$02.update(Entity.TAG_ID, $$02 -> {
                    return Objects.equals(NamespacedSchema.ensureNamespaced($$02.asString("")), "minecraft:new_village") ? $$02.createString("minecraft:village") : $$02;
                });
            });
        }));
    }
}
