package net.minecraft.world.entity.variant;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/variant/SpawnConditions.class */
public class SpawnConditions {
    public static MapCodec<? extends SpawnCondition> bootstrap(Registry<MapCodec<? extends SpawnCondition>> $$0) {
        Registry.register((Registry<? super MapCodec<StructureCheck>>) $$0, "structure", StructureCheck.MAP_CODEC);
        Registry.register((Registry<? super MapCodec<MoonBrightnessCheck>>) $$0, "moon_brightness", MoonBrightnessCheck.MAP_CODEC);
        return (MapCodec) Registry.register((Registry<? super MapCodec<BiomeCheck>>) $$0, "biome", BiomeCheck.MAP_CODEC);
    }
}
