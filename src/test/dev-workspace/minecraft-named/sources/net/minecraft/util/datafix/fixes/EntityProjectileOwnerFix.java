package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.Arrays;
import java.util.function.Function;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EntityProjectileOwnerFix.class */
public class EntityProjectileOwnerFix extends DataFix {
    public EntityProjectileOwnerFix(Schema $$0) {
        super($$0, false);
    }

    protected TypeRewriteRule makeRule() {
        Schema $$0 = getInputSchema();
        return fixTypeEverywhereTyped("EntityProjectileOwner", $$0.getType(References.ENTITY), this::updateProjectiles);
    }

    private Typed<?> updateProjectiles(Typed<?> $$0) {
        return updateEntity(updateEntity(updateEntity(updateEntity(updateEntity(updateEntity(updateEntity(updateEntity(updateEntity($$0, "minecraft:egg", this::updateOwnerThrowable), "minecraft:ender_pearl", this::updateOwnerThrowable), "minecraft:experience_bottle", this::updateOwnerThrowable), "minecraft:snowball", this::updateOwnerThrowable), "minecraft:potion", this::updateOwnerThrowable), "minecraft:llama_spit", this::updateOwnerLlamaSpit), "minecraft:arrow", this::updateOwnerArrow), "minecraft:spectral_arrow", this::updateOwnerArrow), "minecraft:trident", this::updateOwnerArrow);
    }

    private Dynamic<?> updateOwnerArrow(Dynamic<?> $$0) {
        long $$1 = $$0.get("OwnerUUIDMost").asLong(0L);
        long $$2 = $$0.get("OwnerUUIDLeast").asLong(0L);
        return setUUID($$0, $$1, $$2).remove("OwnerUUIDMost").remove("OwnerUUIDLeast");
    }

    private Dynamic<?> updateOwnerLlamaSpit(Dynamic<?> $$0) {
        OptionalDynamic<?> $$1 = $$0.get("Owner");
        long $$2 = $$1.get("OwnerUUIDMost").asLong(0L);
        long $$3 = $$1.get("OwnerUUIDLeast").asLong(0L);
        return setUUID($$0, $$2, $$3).remove("Owner");
    }

    private Dynamic<?> updateOwnerThrowable(Dynamic<?> $$0) {
        OptionalDynamic<?> $$2 = $$0.get("owner");
        long $$3 = $$2.get("M").asLong(0L);
        long $$4 = $$2.get("L").asLong(0L);
        return setUUID($$0, $$3, $$4).remove("owner");
    }

    private Dynamic<?> setUUID(Dynamic<?> $$0, long $$1, long $$2) {
        if ($$1 != 0 && $$2 != 0) {
            return $$0.set("OwnerUUID", $$0.createIntList(Arrays.stream(createUUIDArray($$1, $$2))));
        }
        return $$0;
    }

    private static int[] createUUIDArray(long $$0, long $$1) {
        return new int[]{(int) ($$0 >> 32), (int) $$0, (int) ($$1 >> 32), (int) $$1};
    }

    private Typed<?> updateEntity(Typed<?> $$0, String $$1, Function<Dynamic<?>, Dynamic<?>> $$2) {
        Type<?> $$3 = getInputSchema().getChoiceType(References.ENTITY, $$1);
        Type<?> $$4 = getOutputSchema().getChoiceType(References.ENTITY, $$1);
        return $$0.updateTyped(DSL.namedChoice($$1, $$3), $$4, $$12 -> {
            return $$12.update(DSL.remainderFinder(), $$2);
        });
    }
}
