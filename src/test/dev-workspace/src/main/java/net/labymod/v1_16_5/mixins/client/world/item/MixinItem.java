package net.labymod.v1_16_5.mixins.client.world.item;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/world/item/MixinItem.class */
@Mixin({blx.class})
public abstract class MixinItem implements Item {
    private ResourceLocation labyMod$identifier;

    @Shadow
    public abstract int i();

    @Shadow
    public abstract int j();

    @Override // net.labymod.api.client.world.item.Item
    public ResourceLocation getIdentifier() {
        if (this.labyMod$identifier == null) {
            this.labyMod$identifier = gm.T.b((blx) this);
        }
        return this.labyMod$identifier;
    }

    @Override // net.labymod.api.client.world.item.Item
    public int getMaximumStackSize() {
        return i();
    }

    @Override // net.labymod.api.client.world.item.Item
    public int getMaximumDamage() {
        return j();
    }

    @Override // net.labymod.api.client.world.item.Item
    public boolean isAir() {
        return this == bmd.a;
    }
}
