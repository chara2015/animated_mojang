package net.minecraft.util.datafix.fixes;

import com.google.gson.JsonElement;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JavaOps;
import com.mojang.serialization.JsonOps;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Display;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/LegacyHoverEventFix.class */
public class LegacyHoverEventFix extends DataFix {
    public LegacyHoverEventFix(Schema $$0) {
        super($$0, false);
    }

    protected TypeRewriteRule makeRule() {
        Type<? extends Pair<String, ?>> $$0 = getInputSchema().getType(References.TEXT_COMPONENT).findFieldType("hoverEvent");
        return createFixer(getInputSchema().getTypeRaw(References.TEXT_COMPONENT), $$0);
    }

    private <C, H extends Pair<String, ?>> TypeRewriteRule createFixer(Type<C> $$0, Type<H> $$1) {
        Type<Pair<String, Either<Either<String, List<C>>, Pair<Either<List<C>, Unit>, Pair<Either<C, Unit>, Pair<Either<H, Unit>, Dynamic<?>>>>>>> $$2 = DSL.named(References.TEXT_COMPONENT.typeName(), DSL.or(DSL.or(DSL.string(), DSL.list($$0)), DSL.and(DSL.optional(DSL.field("extra", DSL.list($$0))), DSL.optional(DSL.field("separator", $$0)), DSL.optional(DSL.field("hoverEvent", $$1)), DSL.remainderType())));
        if (!$$2.equals(getInputSchema().getType(References.TEXT_COMPONENT))) {
            throw new IllegalStateException("Text component type did not match, expected " + String.valueOf($$2) + " but got " + String.valueOf(getInputSchema().getType(References.TEXT_COMPONENT)));
        }
        return fixTypeEverywhere("LegacyHoverEventFix", $$2, $$12 -> {
            return $$12 -> {
                return $$12.mapSecond($$12 -> {
                    return $$12.mapRight($$12 -> {
                        return $$12.mapSecond($$12 -> {
                            return $$12.mapSecond($$12 -> {
                                Dynamic<?> $$22 = (Dynamic) $$12.getSecond();
                                Optional<? extends Dynamic<?>> $$3 = $$22.get("hoverEvent").result();
                                if ($$3.isEmpty()) {
                                    return $$12;
                                }
                                Optional<? extends Dynamic<?>> $$4 = ((Dynamic) $$3.get()).get("value").result();
                                if ($$4.isEmpty()) {
                                    return $$12;
                                }
                                String $$5 = (String) ((Either) $$12.getFirst()).left().map((v0) -> {
                                    return v0.getFirst();
                                }).orElse("");
                                Pair pair = (Pair) fixHoverEvent($$1, $$5, (Dynamic) $$3.get());
                                return $$12.mapFirst($$12 -> {
                                    return Either.left(pair);
                                });
                            });
                        });
                    });
                });
            };
        });
    }

    private <H> H fixHoverEvent(Type<H> type, String str, Dynamic<?> dynamic) {
        if ("show_text".equals(str)) {
            return (H) fixShowTextHover(type, dynamic);
        }
        return (H) createPlaceholderHover(type, dynamic);
    }

    private static <H> H fixShowTextHover(Type<H> type, Dynamic<?> dynamic) {
        return (H) Util.readTypedOrThrow(type, dynamic.renameField("value", "contents")).getValue();
    }

    private static <H> H createPlaceholderHover(Type<H> type, Dynamic<?> dynamic) {
        return (H) Util.readTypedOrThrow(type, new Dynamic(JavaOps.INSTANCE, Map.of("action", "show_text", "contents", Map.of(Display.TextDisplay.TAG_TEXT, "Legacy hoverEvent: " + GsonHelper.toStableString((JsonElement) dynamic.convert(JsonOps.INSTANCE).getValue()))))).getValue();
    }
}
