package net.labymod.v1_21_11.mixins.client.world.block.properties;

import net.labymod.api.client.world.block.properties.IntegerBlockProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/world/block/properties/MixinIntegerProperty_API.class */
@Mixin({IntegerProperty.class})
public abstract class MixinIntegerProperty_API extends MixinProperty_API implements IntegerBlockProperty {

    @Shadow
    @Final
    private int min;

    @Shadow
    @Final
    private int max;

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }
}

