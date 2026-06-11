package net.labymod.v1_21.mixins.client.world.block.properties;

import net.labymod.api.client.world.block.properties.IntegerBlockProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/world/block/properties/MixinIntegerProperty_API.class */
@Mixin({duc.class})
public abstract class MixinIntegerProperty_API extends MixinProperty_API implements IntegerBlockProperty {

    @Shadow
    @Final
    private int b;

    @Shadow
    @Final
    private int c;

    @Override // net.labymod.api.client.world.block.properties.IntegerBlockProperty
    public int getMin() {
        return this.b;
    }

    @Override // net.labymod.api.client.world.block.properties.IntegerBlockProperty
    public int getMax() {
        return this.c;
    }
}
