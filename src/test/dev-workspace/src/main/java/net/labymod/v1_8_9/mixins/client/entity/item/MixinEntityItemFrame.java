package net.labymod.v1_8_9.mixins.client.entity.item;

import net.labymod.api.client.entity.decoration.ItemFrame;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.v1_8_9.mixins.client.entity.MixinEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/entity/item/MixinEntityItemFrame.class */
@Mixin({uo.class})
public abstract class MixinEntityItemFrame extends MixinEntity implements ItemFrame {
    @Shadow
    public abstract zx o();

    @Override // net.labymod.api.client.entity.decoration.ItemFrame
    @Nullable
    public ItemStack getItem() {
        return o();
    }
}
