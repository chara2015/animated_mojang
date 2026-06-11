package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/FireResistantToDamageResistantComponentFix.class */
public class FireResistantToDamageResistantComponentFix extends DataComponentRemainderFix {
    public FireResistantToDamageResistantComponentFix(Schema $$0) {
        super($$0, "FireResistantToDamageResistantComponentFix", "minecraft:fire_resistant", "minecraft:damage_resistant");
    }

    @Override // net.minecraft.util.datafix.fixes.DataComponentRemainderFix
    protected <T> Dynamic<T> fixComponent(Dynamic<T> $$0) {
        return $$0.emptyMap().set("types", $$0.createString("#minecraft:is_fire"));
    }
}
