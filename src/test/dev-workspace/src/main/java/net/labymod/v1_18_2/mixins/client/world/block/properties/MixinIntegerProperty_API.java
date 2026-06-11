package net.labymod.v1_18_2.mixins.client.world.block.properties;

import net.labymod.api.client.world.block.properties.IntegerBlockProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/world/block/properties/MixinIntegerProperty_API.class */
@Mixin({cpv.class})
public abstract class MixinIntegerProperty_API extends MixinProperty_API implements IntegerBlockProperty {
    private int labyMod$min;
    private int labyMod$max;

    @Inject(method = {"<init>"}, at = {@At("RETURN")})
    private void labyMod$init(String name, int min, int max, CallbackInfo ci) {
        this.labyMod$min = min;
        this.labyMod$max = max;
    }

    @Override // net.labymod.api.client.world.block.properties.IntegerBlockProperty
    public int getMin() {
        return this.labyMod$min;
    }

    @Override // net.labymod.api.client.world.block.properties.IntegerBlockProperty
    public int getMax() {
        return this.labyMod$max;
    }
}
