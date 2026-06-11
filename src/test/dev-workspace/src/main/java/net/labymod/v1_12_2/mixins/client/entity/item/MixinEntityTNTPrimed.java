package net.labymod.v1_12_2.mixins.client.entity.item;

import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.item.PrimedTnt;
import net.labymod.v1_12_2.mixins.client.entity.MixinEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/entity/item/MixinEntityTNTPrimed.class */
@Mixin({acm.class})
@Implements({@Interface(iface = PrimedTnt.class, prefix = "primedTnt$", remap = Interface.Remap.NONE)})
public abstract class MixinEntityTNTPrimed extends MixinEntity implements PrimedTnt {
    @Shadow
    public abstract vp j();

    @Shadow
    public abstract int shadow$l();

    @Intrinsic
    public int primedTnt$getFuse() {
        return shadow$l();
    }

    @Override // net.labymod.api.client.entity.item.PrimedTnt
    @Nullable
    public LivingEntity getOwner() {
        LivingEntity livingEntityJ = j();
        if (livingEntityJ == null) {
            return null;
        }
        return livingEntityJ;
    }
}
