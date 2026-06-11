package net.labymod.v26_2_snapshot_8.mixins.client.world.block.properties;

import net.labymod.api.client.world.block.properties.IntegerBlockProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/world/block/properties/MixinIntegerProperty_API.class */
@Mixin({IntegerProperty.class})
public abstract class MixinIntegerProperty_API extends MixinProperty_API implements IntegerBlockProperty {

    @Shadow
    @Final
    private int min;

    @Shadow
    @Final
    private int max;

    @Override // net.labymod.api.client.world.block.properties.IntegerBlockProperty
    public int getMin() {
        return this.min;
    }

    @Override // net.labymod.api.client.world.block.properties.IntegerBlockProperty
    public int getMax() {
        return this.max;
    }
}
