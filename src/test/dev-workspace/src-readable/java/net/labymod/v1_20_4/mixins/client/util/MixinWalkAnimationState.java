package net.labymod.v1_20_4.mixins.client.util;

import net.labymod.v1_20_4.client.util.WalkAnimationStateAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/util/MixinWalkAnimationState.class */
@Mixin({bnk.class})
public class MixinWalkAnimationState implements WalkAnimationStateAccessor {

    @Shadow
    private float a;

    @Override // net.labymod.v1_20_4.client.util.WalkAnimationStateAccessor
    public float getSpeedOld() {
        return this.a;
    }
}
