package net.minecraft.client.color.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/color/item/ItemTintSources.class */
public class ItemTintSources {
    private static final ExtraCodecs.LateBoundIdMapper<Identifier, MapCodec<? extends ItemTintSource>> ID_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();
    public static final Codec<ItemTintSource> CODEC = ID_MAPPER.codec(Identifier.CODEC).dispatch((v0) -> {
        return v0.type();
    }, $$0 -> {
        return $$0;
    });

    public static void bootstrap() {
        ID_MAPPER.put(Identifier.withDefaultNamespace("custom_model_data"), CustomModelDataSource.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("constant"), Constant.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("dye"), Dye.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("grass"), GrassColorSource.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("firework"), Firework.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("potion"), Potion.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("map_color"), MapColor.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("team"), TeamColor.MAP_CODEC);
    }
}
