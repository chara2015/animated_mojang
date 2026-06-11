package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/TippedArrowPotionToItemFix.class */
public class TippedArrowPotionToItemFix extends NamedEntityWriteReadFix {
    public TippedArrowPotionToItemFix(Schema $$0) {
        super($$0, false, "TippedArrowPotionToItemFix", References.ENTITY, "minecraft:arrow");
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityWriteReadFix
    protected <T> Dynamic<T> fix(Dynamic<T> $$0) {
        Optional<Dynamic<T>> $$1 = $$0.get("Potion").result();
        Optional<Dynamic<T>> $$2 = $$0.get("custom_potion_effects").result();
        Optional<Dynamic<T>> $$3 = $$0.get("Color").result();
        if ($$1.isEmpty() && $$2.isEmpty() && $$3.isEmpty()) {
            return $$0;
        }
        return $$0.remove("Potion").remove("custom_potion_effects").remove("Color").update(DecoratedPotBlockEntity.TAG_ITEM, $$32 -> {
            Dynamic<?> $$4 = $$32.get("tag").orElseEmptyMap();
            if ($$1.isPresent()) {
                $$4 = $$4.set("Potion", (Dynamic) $$1.get());
            }
            if ($$2.isPresent()) {
                $$4 = $$4.set("custom_potion_effects", (Dynamic) $$2.get());
            }
            if ($$3.isPresent()) {
                $$4 = $$4.set("CustomPotionColor", (Dynamic) $$3.get());
            }
            return $$32.set("tag", $$4);
        });
    }
}
