package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Objects;
import net.minecraft.util.datafix.ExtraDataFixUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/BannerPatternFormatFix.class */
public class BannerPatternFormatFix extends NamedEntityFix {
    private static final Map<String, String> PATTERN_ID_MAP = Map.ofEntries(Map.entry("b", "minecraft:base"), Map.entry("bl", "minecraft:square_bottom_left"), Map.entry("br", "minecraft:square_bottom_right"), Map.entry("tl", "minecraft:square_top_left"), Map.entry("tr", "minecraft:square_top_right"), Map.entry("bs", "minecraft:stripe_bottom"), Map.entry("ts", "minecraft:stripe_top"), Map.entry("ls", "minecraft:stripe_left"), Map.entry("rs", "minecraft:stripe_right"), Map.entry("cs", "minecraft:stripe_center"), Map.entry("ms", "minecraft:stripe_middle"), Map.entry("drs", "minecraft:stripe_downright"), Map.entry("dls", "minecraft:stripe_downleft"), Map.entry("ss", "minecraft:small_stripes"), Map.entry("cr", "minecraft:cross"), Map.entry("sc", "minecraft:straight_cross"), Map.entry("bt", "minecraft:triangle_bottom"), Map.entry("tt", "minecraft:triangle_top"), Map.entry("bts", "minecraft:triangles_bottom"), Map.entry("tts", "minecraft:triangles_top"), Map.entry("ld", "minecraft:diagonal_left"), Map.entry("rd", "minecraft:diagonal_up_right"), Map.entry("lud", "minecraft:diagonal_up_left"), Map.entry("rud", "minecraft:diagonal_right"), Map.entry("mc", "minecraft:circle"), Map.entry("mr", "minecraft:rhombus"), Map.entry("vh", "minecraft:half_vertical"), Map.entry("hh", "minecraft:half_horizontal"), Map.entry("vhr", "minecraft:half_vertical_right"), Map.entry("hhb", "minecraft:half_horizontal_bottom"), Map.entry("bo", "minecraft:border"), Map.entry("cbo", "minecraft:curly_border"), Map.entry("gra", "minecraft:gradient"), Map.entry("gru", "minecraft:gradient_up"), Map.entry("bri", "minecraft:bricks"), Map.entry("glb", "minecraft:globe"), Map.entry("cre", "minecraft:creeper"), Map.entry("sku", "minecraft:skull"), Map.entry("flo", "minecraft:flower"), Map.entry("moj", "minecraft:mojang"), Map.entry("pig", "minecraft:piglin"));

    public BannerPatternFormatFix(Schema $$0) {
        super($$0, false, "BannerPatternFormatFix", References.BLOCK_ENTITY, "minecraft:banner");
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityFix
    protected Typed<?> fix(Typed<?> $$0) {
        return $$0.update(DSL.remainderFinder(), BannerPatternFormatFix::fixTag);
    }

    private static Dynamic<?> fixTag(Dynamic<?> $$0) {
        return $$0.renameAndFixField("Patterns", "patterns", $$02 -> {
            return $$02.createList($$02.asStream().map(BannerPatternFormatFix::fixLayer));
        });
    }

    private static Dynamic<?> fixLayer(Dynamic<?> $$0) {
        Dynamic<?> $$02 = $$0.renameAndFixField("Pattern", "pattern", $$03 -> {
            DataResult map = $$03.asString().map($$03 -> {
                return PATTERN_ID_MAP.getOrDefault($$03, $$03);
            });
            Objects.requireNonNull($$03);
            return (Dynamic) DataFixUtils.orElse(map.map($$03::createString).result(), $$03);
        });
        return $$02.set("color", $$02.createString(ExtraDataFixUtils.dyeColorIdToName($$02.get("Color").asInt(0)))).remove("Color");
    }
}
