package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Streams;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.util.datafix.LegacyComponentDataFixUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/BlockEntitySignDoubleSidedEditableTextFix.class */
public class BlockEntitySignDoubleSidedEditableTextFix extends NamedEntityWriteReadFix {
    public static final List<String> FIELDS_TO_DROP = List.of("Text1", "Text2", "Text3", "Text4", "FilteredText1", "FilteredText2", "FilteredText3", "FilteredText4", "Color", "GlowingText");
    public static final String FILTERED_CORRECT = "_filtered_correct";
    private static final String DEFAULT_COLOR = "black";

    public BlockEntitySignDoubleSidedEditableTextFix(Schema $$0, String $$1, String $$2) {
        super($$0, true, $$1, References.BLOCK_ENTITY, $$2);
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityWriteReadFix
    protected <T> Dynamic<T> fix(Dynamic<T> $$0) {
        Dynamic<T> $$02 = $$0.set("front_text", fixFrontTextTag($$0)).set("back_text", createDefaultText($$0)).set("is_waxed", $$0.createBoolean(false)).set(FILTERED_CORRECT, $$0.createBoolean(true));
        for (String $$1 : FIELDS_TO_DROP) {
            $$02 = $$02.remove($$1);
        }
        return $$02;
    }

    private static <T> Dynamic<T> fixFrontTextTag(Dynamic<T> $$0) {
        Dynamic<T> $$1 = LegacyComponentDataFixUtils.createEmptyComponent($$0.getOps());
        List<T> list = getLines($$0, "Text").map($$12 -> {
            return (Dynamic) $$12.orElse($$1);
        }).toList();
        Dynamic<T> $$3 = $$0.emptyMap().set("messages", $$0.createList(list.stream())).set("color", (Dynamic) $$0.get("Color").result().orElse($$0.createString(DEFAULT_COLOR))).set("has_glowing_text", (Dynamic) $$0.get("GlowingText").result().orElse($$0.createBoolean(false)));
        List<T> list2 = getLines($$0, "FilteredText").toList();
        if (list2.stream().anyMatch((v0) -> {
            return v0.isPresent();
        })) {
            $$3 = $$3.set("filtered_messages", $$0.createList(Streams.mapWithIndex(list2.stream(), ($$13, $$2) -> {
                return (Dynamic) $$13.orElse((Dynamic) list.get((int) $$2));
            })));
        }
        return $$3;
    }

    private static <T> Stream<Optional<Dynamic<T>>> getLines(Dynamic<T> $$0, String $$1) {
        return Stream.of((Object[]) new Optional[]{$$0.get($$1 + "1").result(), $$0.get($$1 + "2").result(), $$0.get($$1 + "3").result(), $$0.get($$1 + "4").result()});
    }

    private static <T> Dynamic<T> createDefaultText(Dynamic<T> $$0) {
        return $$0.emptyMap().set("messages", createEmptyLines($$0)).set("color", $$0.createString(DEFAULT_COLOR)).set("has_glowing_text", $$0.createBoolean(false));
    }

    private static <T> Dynamic<T> createEmptyLines(Dynamic<T> $$0) {
        Dynamic<T> $$1 = LegacyComponentDataFixUtils.createEmptyComponent($$0.getOps());
        return $$0.createList(Stream.of((Object[]) new Dynamic[]{$$1, $$1, $$1, $$1}));
    }
}
