package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.ExtraDataFixUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ProjectileStoredWeaponFix.class */
public class ProjectileStoredWeaponFix extends DataFix {
    public ProjectileStoredWeaponFix(Schema $$0) {
        super($$0, true);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.ENTITY);
        Type<?> $$1 = getOutputSchema().getType(References.ENTITY);
        return fixTypeEverywhereTyped("Fix Arrow stored weapon", $$0, $$1, ExtraDataFixUtils.chainAllFilters(fixChoice("minecraft:arrow"), fixChoice("minecraft:spectral_arrow")));
    }

    private Function<Typed<?>, Typed<?>> fixChoice(String $$0) {
        Type<?> $$1 = getInputSchema().getChoiceType(References.ENTITY, $$0);
        Type<?> $$2 = getOutputSchema().getChoiceType(References.ENTITY, $$0);
        return fixChoiceCap($$0, $$1, $$2);
    }

    private static <T> Function<Typed<?>, Typed<?>> fixChoiceCap(String $$0, Type<?> $$1, Type<T> $$2) {
        OpticFinder<?> $$3 = DSL.namedChoice($$0, $$1);
        return $$22 -> {
            return $$22.updateTyped($$3, $$2, $$12 -> {
                return Util.writeAndReadTypedOrThrow($$12, $$2, UnaryOperator.identity());
            });
        };
    }
}
