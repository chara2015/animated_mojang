package net.labymod.v1_21_3.mixins.client.world.block.properties;

import net.labymod.api.client.world.block.properties.BlockProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/world/block/properties/MixinProperty_API.class */
@Mixin({dyx.class})
public abstract class MixinProperty_API implements BlockProperty {
    @Shadow
    public abstract String f();

    @Override // net.labymod.api.client.world.block.properties.BlockProperty
    public String getKey() {
        return f();
    }
}
