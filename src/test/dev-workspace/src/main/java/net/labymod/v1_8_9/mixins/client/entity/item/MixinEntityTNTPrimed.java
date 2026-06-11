package net.labymod.v1_8_9.mixins.client.entity.item;

import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.item.PrimedTnt;
import net.labymod.v1_8_9.mixins.client.entity.MixinEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/entity/item/MixinEntityTNTPrimed.class */
@Mixin({vj.class})
public abstract class MixinEntityTNTPrimed extends MixinEntity implements PrimedTnt {

    @Shadow
    public int a;

    @Shadow
    public abstract pr j();

    @Override // net.labymod.api.client.entity.item.PrimedTnt
    public int getFuse() {
        return this.a;
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
