package net.labymod.v1_12_2.mixins;

import net.labymod.v1_12_2.client.GameSettingsOptionsAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/MixinGameSettingsOptions.class */
@Mixin({a.class})
public class MixinGameSettingsOptions implements GameSettingsOptionsAccessor {

    @Shadow
    private float S;

    @Shadow
    @Final
    private float R;

    @Override // net.labymod.v1_12_2.client.GameSettingsOptionsAccessor
    public float getMinValue() {
        return this.S;
    }

    @Override // net.labymod.v1_12_2.client.GameSettingsOptionsAccessor
    public float getStep() {
        return this.R;
    }
}
