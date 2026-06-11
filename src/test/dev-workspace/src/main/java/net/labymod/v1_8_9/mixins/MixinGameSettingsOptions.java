package net.labymod.v1_8_9.mixins;

import net.labymod.v1_8_9.client.GameSettingsOptionsAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/MixinGameSettingsOptions.class */
@Mixin({a.class})
public class MixinGameSettingsOptions implements GameSettingsOptionsAccessor {

    @Shadow
    private float X;

    @Shadow
    @Final
    private float W;

    @Override // net.labymod.v1_8_9.client.GameSettingsOptionsAccessor
    public float getMinValue() {
        return this.X;
    }

    @Override // net.labymod.v1_8_9.client.GameSettingsOptionsAccessor
    public float getStep() {
        return this.W;
    }
}
