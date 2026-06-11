package net.labymod.v1_21_11.mixins.client.entity;

import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/entity/MixinMob.class */
@Mixin({Mob.class})
public abstract class MixinMob extends MixinLivingEntity implements net.labymod.api.client.entity.Mob {
}
