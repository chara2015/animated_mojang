package net.labymod.v1_12_2.mixins.client.world.item;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/world/item/MixinItem.class */
@Mixin({ain.class})
public abstract class MixinItem implements Item {
    private ResourceLocation labyMod$identifier;

    @Shadow
    @Final
    public static fh<nf, ain> g;

    @Shadow
    public abstract int j();

    @Shadow
    public abstract int l();

    @Override // net.labymod.api.client.world.item.Item
    public ResourceLocation getIdentifier() {
        if (this.labyMod$identifier == null) {
            this.labyMod$identifier = (nf) g.b((ain) this);
        }
        return this.labyMod$identifier;
    }

    @Override // net.labymod.api.client.world.item.Item
    public int getMaximumStackSize() {
        return j();
    }

    @Override // net.labymod.api.client.world.item.Item
    public int getMaximumDamage() {
        return l();
    }

    @Override // net.labymod.api.client.world.item.Item
    public boolean isAir() {
        return getIdentifier().getPath().equals("air");
    }
}
