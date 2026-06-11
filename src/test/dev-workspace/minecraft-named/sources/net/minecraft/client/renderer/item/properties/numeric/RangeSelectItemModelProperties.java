package net.minecraft.client.renderer.item.properties.numeric;

import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/properties/numeric/RangeSelectItemModelProperties.class */
public class RangeSelectItemModelProperties {
    private static final ExtraCodecs.LateBoundIdMapper<Identifier, MapCodec<? extends RangeSelectItemModelProperty>> ID_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();
    public static final MapCodec<RangeSelectItemModelProperty> MAP_CODEC = ID_MAPPER.codec(Identifier.CODEC).dispatchMap("property", (v0) -> {
        return v0.type();
    }, $$0 -> {
        return $$0;
    });

    public static void bootstrap() {
        ID_MAPPER.put(Identifier.withDefaultNamespace("custom_model_data"), CustomModelDataProperty.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("bundle/fullness"), BundleFullness.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("damage"), Damage.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("cooldown"), Cooldown.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("time"), Time.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("compass"), CompassAngle.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("crossbow/pull"), CrossbowPull.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("use_cycle"), UseCycle.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("use_duration"), UseDuration.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("count"), Count.MAP_CODEC);
    }
}
