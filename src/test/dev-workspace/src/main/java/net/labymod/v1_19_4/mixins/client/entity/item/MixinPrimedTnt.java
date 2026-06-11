package net.labymod.v1_19_4.mixins.client.entity.item;

import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.item.PrimedTnt;
import net.labymod.v1_19_4.mixins.client.entity.MixinEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/entity/item/MixinPrimedTnt.class */
@Mixin({bvg.class})
@Implements({@Interface(iface = PrimedTnt.class, prefix = "primedTnt$", remap = Interface.Remap.NONE)})
public abstract class MixinPrimedTnt extends MixinEntity implements PrimedTnt {
    @Shadow
    public abstract int shadow$j();

    @Shadow
    public abstract bfx shadow$i();

    @Intrinsic
    public int primedTnt$getFuse() {
        return shadow$j();
    }

    @Intrinsic
    @Nullable
    public LivingEntity primedTnt$getOwner() {
        LivingEntity livingEntityShadow$i = shadow$i();
        if (livingEntityShadow$i == null) {
            return null;
        }
        return livingEntityShadow$i;
    }
}
