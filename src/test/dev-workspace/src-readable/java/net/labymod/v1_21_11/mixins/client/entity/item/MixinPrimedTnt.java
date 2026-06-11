package net.labymod.v1_21_11.mixins.client.entity.item;

import net.labymod.v1_21_11.mixins.client.entity.MixinEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/entity/item/MixinPrimedTnt.class */
@Mixin({PrimedTnt.class})
@Implements({@Interface(iface = net.labymod.api.client.entity.item.PrimedTnt.class, prefix = "primedTnt$", remap = Interface.Remap.NONE)})
public abstract class MixinPrimedTnt extends MixinEntity implements net.labymod.api.client.entity.item.PrimedTnt {
    @Shadow
    public abstract int shadow$getFuse();

    @Shadow
    public abstract LivingEntity shadow$getOwner();

    @Intrinsic
    public int primedTnt$getFuse() {
        return shadow$getFuse();
    }

    @Intrinsic
    @Nullable
    public net.labymod.api.client.entity.LivingEntity primedTnt$getOwner() {
        net.labymod.api.client.entity.LivingEntity livingEntityShadow$e = shadow$getOwner();
        if (livingEntityShadow$e == null) {
            return null;
        }
        return livingEntityShadow$e;
    }
}

