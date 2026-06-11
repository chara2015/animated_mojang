package net.minecraft.util.datafix.fixes;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.nbt.TagParser;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.state.StateHolder;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ParticleUnflatteningFix.class */
public class ParticleUnflatteningFix extends DataFix {
    private static final Logger LOGGER = LogUtils.getLogger();

    public ParticleUnflatteningFix(Schema $$0) {
        super($$0, true);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.PARTICLE);
        Type<?> $$1 = getOutputSchema().getType(References.PARTICLE);
        return writeFixAndRead("ParticleUnflatteningFix", $$0, $$1, this::fix);
    }

    private <T> Dynamic<T> fix(Dynamic<T> $$0) {
        String[] $$3;
        Dynamic<T> $$5;
        Optional<String> $$1 = $$0.asString().result();
        if ($$1.isEmpty()) {
            return $$0;
        }
        String $$2 = $$1.get();
        $$3 = $$2.split(" ", 2);
        String $$4 = NamespacedSchema.ensureNamespaced($$3[0]);
        $$5 = $$0.createMap(Map.of($$0.createString(ChunkRegionIoEvent.Fields.TYPE), $$0.createString($$4)));
        switch ($$4) {
            case "minecraft:item":
                return $$3.length > 1 ? updateItem($$5, $$3[1]) : $$5;
            case "minecraft:block":
            case "minecraft:block_marker":
            case "minecraft:falling_dust":
            case "minecraft:dust_pillar":
                return $$3.length > 1 ? updateBlock($$5, $$3[1]) : $$5;
            case "minecraft:dust":
                return $$3.length > 1 ? updateDust($$5, $$3[1]) : $$5;
            case "minecraft:dust_color_transition":
                return $$3.length > 1 ? updateDustTransition($$5, $$3[1]) : $$5;
            case "minecraft:sculk_charge":
                return $$3.length > 1 ? updateSculkCharge($$5, $$3[1]) : $$5;
            case "minecraft:vibration":
                return $$3.length > 1 ? updateVibration($$5, $$3[1]) : $$5;
            case "minecraft:shriek":
                return $$3.length > 1 ? updateShriek($$5, $$3[1]) : $$5;
            default:
                return $$5;
        }
    }

    private <T> Dynamic<T> updateItem(Dynamic<T> $$0, String $$1) {
        Dynamic<T> $$3;
        int $$2 = $$1.indexOf("{");
        Dynamic<T> $$32 = $$0.createMap(Map.of($$0.createString("Count"), $$0.createInt(1)));
        if ($$2 == -1) {
            $$3 = $$32.set(Entity.TAG_ID, $$0.createString($$1));
        } else {
            $$3 = $$32.set(Entity.TAG_ID, $$0.createString($$1.substring(0, $$2)));
            Dynamic<T> $$4 = parseTag($$0.getOps(), $$1.substring($$2));
            if ($$4 != null) {
                $$3 = $$3.set("tag", $$4);
            }
        }
        return $$0.set(DecoratedPotBlockEntity.TAG_ITEM, $$3);
    }

    private static <T> Dynamic<T> parseTag(DynamicOps<T> $$0, String $$1) {
        try {
            return new Dynamic<>($$0, TagParser.create($$0).parseFully($$1));
        } catch (Exception $$2) {
            LOGGER.warn("Failed to parse tag: {}", $$1, $$2);
            return null;
        }
    }

    private <T> Dynamic<T> updateBlock(Dynamic<T> $$0, String $$1) {
        Dynamic<T> $$3;
        int $$2 = $$1.indexOf("[");
        Dynamic<T> $$32 = $$0.emptyMap();
        if ($$2 == -1) {
            $$3 = $$32.set(StateHolder.NAME_TAG, $$0.createString(NamespacedSchema.ensureNamespaced($$1)));
        } else {
            $$3 = $$32.set(StateHolder.NAME_TAG, $$0.createString(NamespacedSchema.ensureNamespaced($$1.substring(0, $$2))));
            Map<Dynamic<T>, Dynamic<T>> $$4 = parseBlockProperties($$0, $$1.substring($$2));
            if (!$$4.isEmpty()) {
                $$3 = $$3.set(StateHolder.PROPERTIES_TAG, $$0.createMap($$4));
            }
        }
        return $$0.set(Display.BlockDisplay.TAG_BLOCK_STATE, $$3);
    }

    private static <T> Map<Dynamic<T>, Dynamic<T>> parseBlockProperties(Dynamic<T> $$0, String $$1) {
        try {
            Map<Dynamic<T>, Dynamic<T>> $$2 = new HashMap<>();
            StringReader $$3 = new StringReader($$1);
            $$3.expect('[');
            $$3.skipWhitespace();
            while ($$3.canRead() && $$3.peek() != ']') {
                $$3.skipWhitespace();
                String $$4 = $$3.readString();
                $$3.skipWhitespace();
                $$3.expect('=');
                $$3.skipWhitespace();
                String $$5 = $$3.readString();
                $$3.skipWhitespace();
                $$2.put($$0.createString($$4), $$0.createString($$5));
                if ($$3.canRead()) {
                    if ($$3.peek() != ',') {
                        break;
                    }
                    $$3.skip();
                }
            }
            $$3.expect(']');
            return $$2;
        } catch (Exception $$6) {
            LOGGER.warn("Failed to parse block properties: {}", $$1, $$6);
            return Map.of();
        }
    }

    private static <T> Dynamic<T> readVector(Dynamic<T> $$0, StringReader $$1) throws CommandSyntaxException {
        float $$2 = $$1.readFloat();
        $$1.expect(' ');
        float $$3 = $$1.readFloat();
        $$1.expect(' ');
        float $$4 = $$1.readFloat();
        Stream streamOf = Stream.of((Object[]) new Float[]{Float.valueOf($$2), Float.valueOf($$3), Float.valueOf($$4)});
        Objects.requireNonNull($$0);
        return $$0.createList(streamOf.map((v1) -> {
            return r2.createFloat(v1);
        }));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    private <T> Dynamic<T> updateDust(Dynamic<T> $$0, String $$1) throws CommandSyntaxException {
        try {
            StringReader $$2 = new StringReader($$1);
            Dynamic<T> $$3 = readVector($$0, $$2);
            $$2.expect(' ');
            float $$4 = $$2.readFloat();
            return $$0.set("color", $$3).set("scale", $$0.createFloat($$4));
        } catch (Exception $$5) {
            LOGGER.warn("Failed to parse particle options: {}", $$1, $$5);
            return $$0;
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    private <T> Dynamic<T> updateDustTransition(Dynamic<T> $$0, String $$1) throws CommandSyntaxException {
        try {
            StringReader $$2 = new StringReader($$1);
            Dynamic<T> $$3 = readVector($$0, $$2);
            $$2.expect(' ');
            float $$4 = $$2.readFloat();
            $$2.expect(' ');
            Dynamic<T> $$5 = readVector($$0, $$2);
            return $$0.set("from_color", $$3).set("to_color", $$5).set("scale", $$0.createFloat($$4));
        } catch (Exception $$6) {
            LOGGER.warn("Failed to parse particle options: {}", $$1, $$6);
            return $$0;
        }
    }

    private <T> Dynamic<T> updateSculkCharge(Dynamic<T> $$0, String $$1) {
        try {
            StringReader $$2 = new StringReader($$1);
            float $$3 = $$2.readFloat();
            return $$0.set("roll", $$0.createFloat($$3));
        } catch (Exception $$4) {
            LOGGER.warn("Failed to parse particle options: {}", $$1, $$4);
            return $$0;
        }
    }

    private <T> Dynamic<T> updateVibration(Dynamic<T> $$0, String $$1) {
        try {
            StringReader $$2 = new StringReader($$1);
            float $$3 = (float) $$2.readDouble();
            $$2.expect(' ');
            float $$4 = (float) $$2.readDouble();
            $$2.expect(' ');
            float $$5 = (float) $$2.readDouble();
            $$2.expect(' ');
            int $$6 = $$2.readInt();
            Dynamic<T> $$7 = $$0.createIntList(IntStream.of(Mth.floor($$3), Mth.floor($$4), Mth.floor($$5)));
            Dynamic<T> $$8 = $$0.createMap(Map.of($$0.createString(ChunkRegionIoEvent.Fields.TYPE), $$0.createString("minecraft:block"), $$0.createString("pos"), $$7));
            return $$0.set("destination", $$8).set("arrival_in_ticks", $$0.createInt($$6));
        } catch (Exception $$9) {
            LOGGER.warn("Failed to parse particle options: {}", $$1, $$9);
            return $$0;
        }
    }

    private <T> Dynamic<T> updateShriek(Dynamic<T> $$0, String $$1) {
        try {
            StringReader $$2 = new StringReader($$1);
            int $$3 = $$2.readInt();
            return $$0.set("delay", $$0.createInt($$3));
        } catch (Exception $$4) {
            LOGGER.warn("Failed to parse particle options: {}", $$1, $$4);
            return $$0;
        }
    }
}
