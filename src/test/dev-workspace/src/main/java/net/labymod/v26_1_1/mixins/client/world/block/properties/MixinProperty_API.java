package net.labymod.v26_1_1.mixins.client.world.block.properties;

import net.labymod.api.client.world.block.properties.BlockProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/world/block/properties/MixinProperty_API.class */
@Mixin({Property.class})
public abstract class MixinProperty_API implements BlockProperty {
    @Shadow
    public abstract String getName();

    @Override // net.labymod.api.client.world.block.properties.BlockProperty
    public String getKey() {
        return getName();
    }
}
