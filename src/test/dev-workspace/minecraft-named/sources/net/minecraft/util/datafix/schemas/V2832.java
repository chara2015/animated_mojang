package net.minecraft.util.datafix.schemas;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/schemas/V2832.class */
public class V2832 extends NamespacedSchema {
    public V2832(int $$0, Schema $$1) {
        super($$0, $$1);
    }

    public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
        super.registerTypes($$0, $$1, $$2);
        $$0.registerType(false, References.CHUNK, () -> {
            return DSL.fields("Level", DSL.optionalFields("Entities", DSL.list(References.ENTITY_TREE.in($$0)), "TileEntities", DSL.list(DSL.or(References.BLOCK_ENTITY.in($$0), DSL.remainder())), "TileTicks", DSL.list(DSL.fields("i", References.BLOCK_NAME.in($$0))), "Sections", DSL.list(DSL.optionalFields("biomes", DSL.optionalFields(StructureTemplate.PALETTE_TAG, DSL.list(References.BIOME.in($$0))), "block_states", DSL.optionalFields(StructureTemplate.PALETTE_TAG, DSL.list(References.BLOCK_STATE.in($$0))))), "Structures", DSL.optionalFields("Starts", DSL.compoundList(References.STRUCTURE_FEATURE.in($$0)))));
        });
        $$0.registerType(false, References.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST, () -> {
            return DSL.constType(namespacedString());
        });
        $$0.registerType(false, References.WORLD_GEN_SETTINGS, () -> {
            return DSL.fields("dimensions", DSL.compoundList(DSL.constType(namespacedString()), DSL.fields("generator", DSL.taggedChoiceLazy(ChunkRegionIoEvent.Fields.TYPE, DSL.string(), ImmutableMap.of("minecraft:debug", DSL::remainder, "minecraft:flat", () -> {
                return DSL.optionalFields("settings", DSL.optionalFields("biome", References.BIOME.in($$0), "layers", DSL.list(DSL.optionalFields("block", References.BLOCK_NAME.in($$0)))));
            }, "minecraft:noise", () -> {
                return DSL.optionalFields("biome_source", DSL.taggedChoiceLazy(ChunkRegionIoEvent.Fields.TYPE, DSL.string(), ImmutableMap.of("minecraft:fixed", () -> {
                    return DSL.fields("biome", References.BIOME.in($$0));
                }, "minecraft:multi_noise", () -> {
                    return DSL.or(DSL.fields("preset", References.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST.in($$0)), DSL.list(DSL.fields("biome", References.BIOME.in($$0))));
                }, "minecraft:checkerboard", () -> {
                    return DSL.fields("biomes", DSL.list(References.BIOME.in($$0)));
                }, "minecraft:the_end", DSL::remainder)), "settings", DSL.or(DSL.constType(DSL.string()), DSL.optionalFields("default_block", References.BLOCK_NAME.in($$0), "default_fluid", References.BLOCK_NAME.in($$0))));
            })))));
        });
    }
}
