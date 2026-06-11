package net.minecraft.util.datafix.schemas;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.Hook;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.resources.Identifier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V1451_6.class */
public class V1451_6 extends NamespacedSchema {
    public static final String SPECIAL_OBJECTIVE_MARKER = "_special";
    protected static final Hook.HookFunction UNPACK_OBJECTIVE_ID = new Hook.HookFunction() { // from class: net.minecraft.util.datafix.schemas.V1451_6.1
        public <T> T apply(DynamicOps<T> dynamicOps, T t) {
            Dynamic dynamic = new Dynamic(dynamicOps, t);
            return (T) ((Dynamic) DataFixUtils.orElse(dynamic.get("CriteriaName").asString().result().map($$0 -> {
                int $$1 = $$0.indexOf(58);
                if ($$1 < 0) {
                    return Pair.of(V1451_6.SPECIAL_OBJECTIVE_MARKER, $$0);
                }
                try {
                    Identifier $$2 = Identifier.bySeparator($$0.substring(0, $$1), '.');
                    Identifier $$3 = Identifier.bySeparator($$0.substring($$1 + 1), '.');
                    return Pair.of($$2.toString(), $$3.toString());
                } catch (Exception e) {
                    return Pair.of(V1451_6.SPECIAL_OBJECTIVE_MARKER, $$0);
                }
            }).map($$1 -> {
                return dynamic.set("CriteriaType", dynamic.createMap(ImmutableMap.of(dynamic.createString(ChunkRegionIoEvent.Fields.TYPE), dynamic.createString((String) $$1.getFirst()), dynamic.createString(Entity.TAG_ID), dynamic.createString((String) $$1.getSecond()))));
            }), dynamic)).getValue();
        }
    };
    protected static final Hook.HookFunction REPACK_OBJECTIVE_ID = new Hook.HookFunction() { // from class: net.minecraft.util.datafix.schemas.V1451_6.2
        public <T> T apply(DynamicOps<T> dynamicOps, T t) {
            Dynamic dynamic = new Dynamic(dynamicOps, t);
            return (T) ((Dynamic) DataFixUtils.orElse(dynamic.get("CriteriaType").get().result().flatMap($$1 -> {
                Optional<String> $$2 = $$1.get(ChunkRegionIoEvent.Fields.TYPE).asString().result();
                Optional<String> $$3 = $$1.get(Entity.TAG_ID).asString().result();
                if ($$2.isPresent() && $$3.isPresent()) {
                    String $$4 = $$2.get();
                    if ($$4.equals(V1451_6.SPECIAL_OBJECTIVE_MARKER)) {
                        return Optional.of(dynamic.createString($$3.get()));
                    }
                    return Optional.of($$1.createString(V1451_6.packNamespacedWithDot($$4) + ":" + V1451_6.packNamespacedWithDot($$3.get())));
                }
                return Optional.empty();
            }).map($$12 -> {
                return dynamic.set("CriteriaName", $$12).remove("CriteriaType");
            }), dynamic)).getValue();
        }
    };

    public V1451_6(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
        super.registerTypes($$0, $$1, $$2);
        Supplier<TypeTemplate> $$3 = () -> {
            return DSL.compoundList(References.ITEM_NAME.in($$0), DSL.constType(DSL.intType()));
        };
        $$0.registerType(false, References.STATS, () -> {
            return DSL.optionalFields("stats", DSL.optionalFields(new Pair[]{Pair.of("minecraft:mined", DSL.compoundList(References.BLOCK_NAME.in($$0), DSL.constType(DSL.intType()))), Pair.of("minecraft:crafted", (TypeTemplate) $$3.get()), Pair.of("minecraft:used", (TypeTemplate) $$3.get()), Pair.of("minecraft:broken", (TypeTemplate) $$3.get()), Pair.of("minecraft:picked_up", (TypeTemplate) $$3.get()), Pair.of("minecraft:dropped", (TypeTemplate) $$3.get()), Pair.of("minecraft:killed", DSL.compoundList(References.ENTITY_NAME.in($$0), DSL.constType(DSL.intType()))), Pair.of("minecraft:killed_by", DSL.compoundList(References.ENTITY_NAME.in($$0), DSL.constType(DSL.intType()))), Pair.of("minecraft:custom", DSL.compoundList(DSL.constType(namespacedString()), DSL.constType(DSL.intType())))}));
        });
        Map<String, Supplier<TypeTemplate>> $$4 = createCriterionTypes($$0);
        $$0.registerType(false, References.OBJECTIVE, () -> {
            return DSL.hook(DSL.optionalFields("CriteriaType", DSL.taggedChoiceLazy(ChunkRegionIoEvent.Fields.TYPE, DSL.string(), $$4), "DisplayName", References.TEXT_COMPONENT.in($$0)), UNPACK_OBJECTIVE_ID, REPACK_OBJECTIVE_ID);
        });
    }

    protected static Map<String, Supplier<TypeTemplate>> createCriterionTypes(Schema $$0) {
        Supplier<TypeTemplate> $$1 = () -> {
            return DSL.optionalFields(Entity.TAG_ID, References.ITEM_NAME.in($$0));
        };
        Supplier<TypeTemplate> $$2 = () -> {
            return DSL.optionalFields(Entity.TAG_ID, References.BLOCK_NAME.in($$0));
        };
        Supplier<TypeTemplate> $$3 = () -> {
            return DSL.optionalFields(Entity.TAG_ID, References.ENTITY_NAME.in($$0));
        };
        Map<String, Supplier<TypeTemplate>> $$4 = Maps.newHashMap();
        $$4.put("minecraft:mined", $$2);
        $$4.put("minecraft:crafted", $$1);
        $$4.put("minecraft:used", $$1);
        $$4.put("minecraft:broken", $$1);
        $$4.put("minecraft:picked_up", $$1);
        $$4.put("minecraft:dropped", $$1);
        $$4.put("minecraft:killed", $$3);
        $$4.put("minecraft:killed_by", $$3);
        $$4.put("minecraft:custom", () -> {
            return DSL.optionalFields(Entity.TAG_ID, DSL.constType(namespacedString()));
        });
        $$4.put(SPECIAL_OBJECTIVE_MARKER, () -> {
            return DSL.optionalFields(Entity.TAG_ID, DSL.constType(DSL.string()));
        });
        return $$4;
    }

    public static String packNamespacedWithDot(String $$0) {
        Identifier $$1 = Identifier.tryParse($$0);
        return $$1 != null ? $$1.getNamespace() + "." + $$1.getPath() : $$0;
    }
}
