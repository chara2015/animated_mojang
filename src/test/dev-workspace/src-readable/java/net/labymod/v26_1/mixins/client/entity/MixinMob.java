package net.labymod.v26_1.mixins.client.entity;

import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/entity/MixinMob.class */
@Mixin({Mob.class})
public abstract class MixinMob extends MixinLivingEntity implements net.labymod.api.client.entity.Mob {
}
