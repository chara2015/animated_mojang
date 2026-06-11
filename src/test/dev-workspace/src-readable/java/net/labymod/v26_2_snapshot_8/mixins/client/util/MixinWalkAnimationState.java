package net.labymod.v26_2_snapshot_8.mixins.client.util;

import net.labymod.v26_2_snapshot_8.client.util.WalkAnimationStateAccessor;
import net.minecraft.world.entity.WalkAnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/util/MixinWalkAnimationState.class */
@Mixin({WalkAnimationState.class})
public class MixinWalkAnimationState implements WalkAnimationStateAccessor {

    @Shadow
    private float speedOld;

    @Override // net.labymod.v26_2_snapshot_8.client.util.WalkAnimationStateAccessor
    public float getSpeedOld() {
        return this.speedOld;
    }
}
