package net.minecraft.client.renderer.texture.atlas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.List;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.client.renderer.texture.atlas.sources.SourceFilter;
import net.minecraft.client.renderer.texture.atlas.sources.Unstitcher;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/atlas/SpriteSources.class */
public class SpriteSources {
    private static final ExtraCodecs.LateBoundIdMapper<Identifier, MapCodec<? extends SpriteSource>> ID_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();
    public static final Codec<SpriteSource> CODEC = ID_MAPPER.codec(Identifier.CODEC).dispatch((v0) -> {
        return v0.codec();
    }, $$0 -> {
        return $$0;
    });
    public static final Codec<List<SpriteSource>> FILE_CODEC = CODEC.listOf().fieldOf("sources").codec();

    public static void bootstrap() {
        ID_MAPPER.put(Identifier.withDefaultNamespace("single"), SingleFile.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("directory"), DirectoryLister.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("filter"), SourceFilter.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("unstitch"), Unstitcher.MAP_CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("paletted_permutations"), PalettedPermutations.MAP_CODEC);
    }
}
