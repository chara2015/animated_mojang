package net.labymod.v1_20_4.mixins.client.item;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/item/MixinItem.class */
@Mixin({cmt.class})
public abstract class MixinItem implements Item {
    private ResourceLocation labyMod$identifier;

    @Shadow
    public abstract int l();

    @Shadow
    public abstract int n();

    @Override // net.labymod.api.client.world.item.Item
    public ResourceLocation getIdentifier() {
        if (this.labyMod$identifier == null) {
            this.labyMod$identifier = kd.h.b((cmt) this);
        }
        return this.labyMod$identifier;
    }

    @Override // net.labymod.api.client.world.item.Item
    public int getMaximumStackSize() {
        return l();
    }

    @Override // net.labymod.api.client.world.item.Item
    public int getMaximumDamage() {
        return n();
    }

    @Override // net.labymod.api.client.world.item.Item
    public boolean isAir() {
        return this == cnb.a;
    }
}
