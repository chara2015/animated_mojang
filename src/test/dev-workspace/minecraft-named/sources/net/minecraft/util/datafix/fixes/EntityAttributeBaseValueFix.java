package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.function.DoubleUnaryOperator;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.levelgen.Density;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EntityAttributeBaseValueFix.class */
public class EntityAttributeBaseValueFix extends NamedEntityFix {
    private final String attributeId;
    private final DoubleUnaryOperator valueFixer;

    public EntityAttributeBaseValueFix(Schema $$0, String $$1, String $$2, String $$3, DoubleUnaryOperator $$4) {
        super($$0, false, $$1, References.ENTITY, $$2);
        this.attributeId = $$3;
        this.valueFixer = $$4;
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityFix
    protected Typed<?> fix(Typed<?> $$0) {
        return $$0.update(DSL.remainderFinder(), this::fixValue);
    }

    private Dynamic<?> fixValue(Dynamic<?> $$0) {
        return $$0.update(LivingEntity.TAG_ATTRIBUTES, $$1 -> {
            return $$0.createList($$1.asStream().map($$02 -> {
                String $$1 = NamespacedSchema.ensureNamespaced($$02.get(Entity.TAG_ID).asString(""));
                if (!$$1.equals(this.attributeId)) {
                    return $$02;
                }
                double $$2 = $$02.get("base").asDouble(Density.SURFACE);
                return $$02.set("base", $$02.createDouble(this.valueFixer.applyAsDouble($$2)));
            }));
        });
    }
}
