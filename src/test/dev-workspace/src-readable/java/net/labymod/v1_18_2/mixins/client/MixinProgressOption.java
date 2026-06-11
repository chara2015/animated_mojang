package net.labymod.v1_18_2.mixins.client;

import net.labymod.core.client.accessor.gui.ProgressOptionAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/MixinProgressOption.class */
@Mixin({dyz.class})
public class MixinProgressOption implements ProgressOptionAccessor {

    @Shadow
    @Final
    protected float af;

    @Override // net.labymod.core.client.accessor.gui.ProgressOptionAccessor
    public float getSteps() {
        return this.af;
    }
}
