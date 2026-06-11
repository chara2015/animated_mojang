package net.labymod.v1_21_8.mixins.client.item;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/item/MixinItem.class */
@Mixin({dcr.class})
public abstract class MixinItem implements Item {
    private ResourceLocation labyMod$identifier;

    @Shadow
    public abstract int g();

    @Shadow
    public abstract kn f();

    @Override // net.labymod.api.client.world.item.Item
    public ResourceLocation getIdentifier() {
        if (this.labyMod$identifier == null) {
            this.labyMod$identifier = mm.g.b((dcr) this);
        }
        return this.labyMod$identifier;
    }

    @Override // net.labymod.api.client.world.item.Item
    public int getMaximumStackSize() {
        return g();
    }

    @Override // net.labymod.api.client.world.item.Item
    public int getMaximumDamage() {
        return ((Integer) f().a(kq.d, 0)).intValue();
    }

    @Override // net.labymod.api.client.world.item.Item
    public boolean isAir() {
        return this == dcz.a;
    }
}
