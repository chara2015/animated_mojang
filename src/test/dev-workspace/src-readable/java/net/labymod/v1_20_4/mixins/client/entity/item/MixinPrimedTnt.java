package net.labymod.v1_20_4.mixins.client.entity.item;

import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.item.PrimedTnt;
import net.labymod.v1_20_4.mixins.client.entity.MixinEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/entity/item/MixinPrimedTnt.class */
@Mixin({cbv.class})
@Implements({@Interface(iface = PrimedTnt.class, prefix = "primedTnt$", remap = Interface.Remap.NONE)})
public abstract class MixinPrimedTnt extends MixinEntity implements PrimedTnt {
    @Shadow
    public abstract int shadow$s();

    @Shadow
    public abstract bml shadow$q();

    @Intrinsic
    public int primedTnt$getFuse() {
        return shadow$s();
    }

    @Intrinsic
    @Nullable
    public LivingEntity primedTnt$getOwner() {
        LivingEntity livingEntityShadow$q = shadow$q();
        if (livingEntityShadow$q == null) {
            return null;
        }
        return livingEntityShadow$q;
    }
}
