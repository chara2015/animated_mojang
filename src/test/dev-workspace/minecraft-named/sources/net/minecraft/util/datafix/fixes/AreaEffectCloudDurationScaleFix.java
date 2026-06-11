package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/AreaEffectCloudDurationScaleFix.class */
public class AreaEffectCloudDurationScaleFix extends NamedEntityFix {
    public AreaEffectCloudDurationScaleFix(Schema $$0) {
        super($$0, false, "AreaEffectCloudDurationScaleFix", References.ENTITY, "minecraft:area_effect_cloud");
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityFix
    protected Typed<?> fix(Typed<?> $$0) {
        return $$0.update(DSL.remainderFinder(), $$02 -> {
            return $$02.set("potion_duration_scale", $$02.createFloat(0.25f));
        });
    }
}
