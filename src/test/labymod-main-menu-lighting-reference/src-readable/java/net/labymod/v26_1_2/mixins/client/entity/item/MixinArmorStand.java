package net.labymod.v26_1_2.mixins.client.entity.item;

import net.labymod.v26_1_2.mixins.client.entity.MixinLivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/entity/item/MixinArmorStand.class */
@Mixin({ArmorStand.class})
public abstract class MixinArmorStand extends MixinLivingEntity implements net.labymod.api.client.entity.item.ArmorStand {
}
