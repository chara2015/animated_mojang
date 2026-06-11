package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.ExtraDataFixUtils;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/TextComponentHoverAndClickEventFix.class */
public class TextComponentHoverAndClickEventFix extends DataFix {
    public TextComponentHoverAndClickEventFix(Schema $$0) {
        super($$0, true);
    }

    protected TypeRewriteRule makeRule() {
        Type<? extends Pair<String, ?>> $$0 = getInputSchema().getType(References.TEXT_COMPONENT).findFieldType("hoverEvent");
        return createFixer(getInputSchema().getTypeRaw(References.TEXT_COMPONENT), getOutputSchema().getType(References.TEXT_COMPONENT), $$0);
    }

    private <C1, C2, H extends Pair<String, ?>> TypeRewriteRule createFixer(Type<C1> $$0, Type<C2> $$1, Type<H> $$2) {
        Type<Pair<String, Either<Either<String, List<C1>>, Pair<Either<List<C1>, Unit>, Pair<Either<C1, Unit>, Pair<Either<H, Unit>, Dynamic<?>>>>>>> $$3 = DSL.named(References.TEXT_COMPONENT.typeName(), DSL.or(DSL.or(DSL.string(), DSL.list($$0)), DSL.and(DSL.optional(DSL.field("extra", DSL.list($$0))), DSL.optional(DSL.field("separator", $$0)), DSL.optional(DSL.field("hoverEvent", $$2)), DSL.remainderType())));
        if (!$$3.equals(getInputSchema().getType(References.TEXT_COMPONENT))) {
            throw new IllegalStateException("Text component type did not match, expected " + String.valueOf($$3) + " but got " + String.valueOf(getInputSchema().getType(References.TEXT_COMPONENT)));
        }
        Type<?> $$4 = ExtraDataFixUtils.patchSubType($$3, $$3, $$1);
        return fixTypeEverywhere("TextComponentHoverAndClickEventFix", $$3, $$1, $$22 -> {
            return $$32 -> {
                boolean $$42 = ((Boolean) ((Either) $$32.getSecond()).map($$02 -> {
                    return false;
                }, $$03 -> {
                    Pair pair = (Pair) ((Pair) $$03.getSecond()).getSecond();
                    boolean $$22 = ((Either) pair.getFirst()).left().isPresent();
                    boolean $$32 = ((Dynamic) pair.getSecond()).get("clickEvent").result().isPresent();
                    return Boolean.valueOf($$22 || $$32);
                })).booleanValue();
                if (!$$42) {
                    return $$32;
                }
                return Util.writeAndReadTypedOrThrow(ExtraDataFixUtils.cast($$4, $$32, $$22), $$1, TextComponentHoverAndClickEventFix::fixTextComponent).getValue();
            };
        });
    }

    private static Dynamic<?> fixTextComponent(Dynamic<?> $$0) {
        return $$0.renameAndFixField("hoverEvent", "hover_event", TextComponentHoverAndClickEventFix::fixHoverEvent).renameAndFixField("clickEvent", "click_event", TextComponentHoverAndClickEventFix::fixClickEvent);
    }

    private static Dynamic<?> copyFields(Dynamic<?> $$0, Dynamic<?> $$1, String... $$2) {
        for (String $$3 : $$2) {
            $$0 = Dynamic.copyField($$1, $$3, $$0, $$3);
        }
        return $$0;
    }

    private static Dynamic<?> fixHoverEvent(Dynamic<?> $$0) {
        String $$1 = $$0.get("action").asString("");
        switch ($$1) {
            case "show_text":
                return $$0.renameField("contents", "value");
            case "show_item":
                Dynamic<?> $$2 = $$0.get("contents").orElseEmptyMap();
                Optional<String> $$3 = $$2.asString().result();
                return $$3.isPresent() ? $$0.renameField("contents", Entity.TAG_ID) : copyFields($$0.remove("contents"), $$2, Entity.TAG_ID, "count", "components");
            case "show_entity":
                Dynamic<?> $$4 = $$0.get("contents").orElseEmptyMap();
                return copyFields($$0.remove("contents"), $$4, Entity.TAG_ID, ChunkRegionIoEvent.Fields.TYPE, JigsawBlockEntity.NAME).renameField(Entity.TAG_ID, "uuid").renameField(ChunkRegionIoEvent.Fields.TYPE, Entity.TAG_ID);
            default:
                return $$0;
        }
    }

    private static <T> Dynamic<T> fixClickEvent(Dynamic<T> $$0) {
        String $$2;
        String $$1 = $$0.get("action").asString("");
        $$2 = $$0.get("value").asString("");
        switch ($$1) {
            case "open_url":
                if (!validateUri($$2)) {
                    return null;
                }
                return $$0.renameField("value", "url");
            case "open_file":
                return $$0.renameField("value", "path");
            case "run_command":
            case "suggest_command":
                if (!validateChat($$2)) {
                    return null;
                }
                return $$0.renameField("value", "command");
            case "change_page":
                Integer $$3 = (Integer) $$0.get("value").result().map(TextComponentHoverAndClickEventFix::parseOldPage).orElse(null);
                if ($$3 == null) {
                    return null;
                }
                int $$4 = Math.max($$3.intValue(), 1);
                return $$0.remove("value").set("page", $$0.createInt($$4));
            default:
                return $$0;
        }
    }

    private static Integer parseOldPage(Dynamic<?> $$0) {
        Optional<Number> $$1 = $$0.asNumber().result();
        if ($$1.isPresent()) {
            return Integer.valueOf($$1.get().intValue());
        }
        try {
            return Integer.valueOf(Integer.parseInt($$0.asString("")));
        } catch (Exception e) {
            return null;
        }
    }

    private static boolean validateUri(String $$0) {
        try {
            URI $$1 = new URI($$0);
            String $$2 = $$1.getScheme();
            if ($$2 == null) {
                return false;
            }
            String $$3 = $$2.toLowerCase(Locale.ROOT);
            if (!"http".equals($$3)) {
                if (!"https".equals($$3)) {
                    return false;
                }
            }
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    private static boolean validateChat(String $$0) {
        for (int $$1 = 0; $$1 < $$0.length(); $$1++) {
            char $$2 = $$0.charAt($$1);
            if ($$2 == 167 || $$2 < ' ' || $$2 == 127) {
                return false;
            }
        }
        return true;
    }
}
