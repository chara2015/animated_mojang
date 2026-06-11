package net.labymod.v1_12_2.mixins.client.entity.item;

import net.labymod.api.client.entity.decoration.ItemFrame;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.v1_12_2.mixins.client.entity.MixinEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/entity/item/MixinEntityItemFrame.class */
@Mixin({acb.class})
public abstract class MixinEntityItemFrame extends MixinEntity implements ItemFrame {
    @Shadow
    public abstract aip r();

    @Override // net.labymod.api.client.entity.decoration.ItemFrame
    @Nullable
    public ItemStack getItem() {
        return r();
    }
}
