package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Optional;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.ExtraDataFixUtils;
import net.minecraft.util.datafix.LegacyComponentDataFixUtils;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EntityCustomNameToComponentFix.class */
public class EntityCustomNameToComponentFix extends DataFix {
    public EntityCustomNameToComponentFix(Schema $$0) {
        super($$0, true);
    }

    public TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.ENTITY);
        Type<?> $$1 = getOutputSchema().getType(References.ENTITY);
        OpticFinder<String> $$2 = DSL.fieldFinder(Entity.TAG_ID, NamespacedSchema.namespacedString());
        OpticFinder<String> $$3 = $$0.findField(Entity.TAG_CUSTOM_NAME);
        Type<?> $$4 = $$1.findFieldType(Entity.TAG_CUSTOM_NAME);
        return fixTypeEverywhereTyped("EntityCustomNameToComponentFix", $$0, $$1, $$42 -> {
            return fixEntity($$42, $$1, $$2, $$3, $$4);
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> Typed<?> fixEntity(Typed<?> $$0, Type<?> $$1, OpticFinder<String> $$2, OpticFinder<String> $$3, Type<T> $$4) {
        Optional<String> $$5 = $$0.getOptional($$3);
        if ($$5.isEmpty()) {
            return ExtraDataFixUtils.cast($$1, $$0);
        }
        if ($$5.get().isEmpty()) {
            return Util.writeAndReadTypedOrThrow($$0, $$1, $$02 -> {
                return $$02.remove(Entity.TAG_CUSTOM_NAME);
            });
        }
        String $$6 = (String) $$0.getOptional($$2).orElse("");
        Dynamic<?> $$7 = fixCustomName($$0.getOps(), $$5.get(), $$6);
        return $$0.set($$3, Util.readTypedOrThrow($$4, $$7));
    }

    private static <T> Dynamic<T> fixCustomName(DynamicOps<T> $$0, String $$1, String $$2) {
        if ("minecraft:commandblock_minecart".equals($$2)) {
            return new Dynamic<>($$0, $$0.createString($$1));
        }
        return LegacyComponentDataFixUtils.createPlainTextComponent($$0, $$1);
    }
}
