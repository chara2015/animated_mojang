package net.minecraft.client.renderer.item.properties.numeric;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.CompassAngleState;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/properties/numeric/CompassAngle.class */
public class CompassAngle implements RangeSelectItemModelProperty {
    public static final MapCodec<CompassAngle> MAP_CODEC = CompassAngleState.MAP_CODEC.xmap(CompassAngle::new, $$0 -> {
        return $$0.state;
    });
    private final CompassAngleState state;

    public CompassAngle(boolean $$0, CompassAngleState.CompassTarget $$1) {
        this(new CompassAngleState($$0, $$1));
    }

    private CompassAngle(CompassAngleState $$0) {
        this.state = $$0;
    }

    @Override // net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty
    public float get(ItemStack $$0, ClientLevel $$1, ItemOwner $$2, int $$3) {
        return this.state.get($$0, $$1, $$2, $$3);
    }

    @Override // net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty
    public MapCodec<CompassAngle> type() {
        return MAP_CODEC;
    }
}
