package net.labymod.v1_12_2.mixins.client.world.block.properties;

import net.labymod.api.client.world.block.properties.BlockProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/world/block/properties/MixinProperty_API.class */
@Mixin({axj.class})
public interface MixinProperty_API extends BlockProperty {
    @Shadow
    String a();

    @Override // net.labymod.api.client.world.block.properties.BlockProperty
    default String getKey() {
        return a();
    }
}
