package net.labymod.v1_21_11.mixins.client.world.block.properties;

import net.labymod.api.client.world.block.properties.BlockProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/world/block/properties/MixinProperty_API.class */
@Mixin({Property.class})
public abstract class MixinProperty_API implements BlockProperty {
    @Shadow
    public abstract String getName();

    public String getKey() {
        return getName();
    }
}

