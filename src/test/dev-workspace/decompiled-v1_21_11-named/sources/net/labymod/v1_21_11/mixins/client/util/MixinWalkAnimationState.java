package net.labymod.v1_21_11.mixins.client.util;

import net.labymod.v1_21_11.client.util.WalkAnimationStateAccessor;
import net.minecraft.world.entity.WalkAnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/util/MixinWalkAnimationState.class */
@Mixin({WalkAnimationState.class})
public class MixinWalkAnimationState implements WalkAnimationStateAccessor {

    @Shadow
    private float a;

    @Override // net.labymod.v1_21_11.client.util.WalkAnimationStateAccessor
    public float getSpeedOld() {
        return this.a;
    }
}
