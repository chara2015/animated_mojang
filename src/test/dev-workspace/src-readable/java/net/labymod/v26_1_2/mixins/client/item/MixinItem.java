package net.labymod.v26_1_2.mixins.client.item;

import net.labymod.api.client.resources.ResourceLocation;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/item/MixinItem.class */
@Mixin({Item.class})
public abstract class MixinItem implements net.labymod.api.client.world.item.Item {
    private ResourceLocation labyMod$identifier;

    @Shadow
    public abstract int getDefaultMaxStackSize();

    @Shadow
    public abstract DataComponentMap components();

    @Override // net.labymod.api.client.world.item.Item
    public ResourceLocation getIdentifier() {
        if (this.labyMod$identifier == null) {
            this.labyMod$identifier = BuiltInRegistries.ITEM.getKey((Item) this);
        }
        return this.labyMod$identifier;
    }

    @Override // net.labymod.api.client.world.item.Item
    public int getMaximumStackSize() {
        return getDefaultMaxStackSize();
    }

    @Override // net.labymod.api.client.world.item.Item
    public int getMaximumDamage() {
        return ((Integer) components().getOrDefault(DataComponents.MAX_DAMAGE, 0)).intValue();
    }

    @Override // net.labymod.api.client.world.item.Item
    public boolean isAir() {
        return this == Items.AIR;
    }
}
