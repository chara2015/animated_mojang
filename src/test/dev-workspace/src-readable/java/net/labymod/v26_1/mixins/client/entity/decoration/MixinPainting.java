package net.labymod.v26_1.mixins.client.entity.decoration;

import net.labymod.v26_1.mixins.client.entity.MixinEntity;
import net.minecraft.world.entity.decoration.painting.Painting;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/entity/decoration/MixinPainting.class */
@Mixin({Painting.class})
public abstract class MixinPainting extends MixinEntity implements net.labymod.api.client.entity.decoration.Painting {
}
