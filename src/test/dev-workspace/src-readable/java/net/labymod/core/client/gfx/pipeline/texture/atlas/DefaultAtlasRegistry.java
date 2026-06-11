package net.labymod.core.client.gfx.pipeline.texture.atlas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.gfx.pipeline.texture.atlas.AtlasRegistry;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.models.Implements;
import net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.DefaultBlockTextureAtlas;
import net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.DefaultLiquidTextureAtlas;
import net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.DefaultParticleTextureAtlas;
import net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.minecraft.DefaultBarsTextureAtlas;
import net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.minecraft.DefaultIconTextureAtlas;
import net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.minecraft.DefaultWidgetTextureAtlas;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/texture/atlas/DefaultAtlasRegistry.class */
@Singleton
@Implements(AtlasRegistry.class)
public class DefaultAtlasRegistry implements AtlasRegistry {
    private static final boolean GUI_ATLAS = MinecraftVersions.V23w31a.orNewer();
    private static final boolean ICONS_ATLAS = MinecraftVersions.V1_8_9.orOlder();
    private final Map<ResourceLocation, TextureAtlas> atlases = new HashMap();

    public DefaultAtlasRegistry() {
        register(new DefaultBlockTextureAtlas());
        register(new DefaultParticleTextureAtlas());
        register(new DefaultLiquidTextureAtlas(Textures.Splash.LAVA_FLOW, "lava_flow"));
        register(findBestAtlas(GUI_ATLAS, DefaultWidgetTextureAtlas::new));
        register(findBestAtlas(GUI_ATLAS, DefaultIconTextureAtlas::new));
        if (!ICONS_ATLAS) {
            register(findBestAtlas(GUI_ATLAS, DefaultBarsTextureAtlas::new));
        }
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.AtlasRegistry
    public void register(ResourceLocation location, TextureAtlas atlas) {
        this.atlases.put(location, atlas);
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.AtlasRegistry
    @NotNull
    public TextureAtlas getAtlas(ResourceLocation location) {
        TextureAtlas atlas = this.atlases.get(location);
        if (atlas == null) {
            throw new IllegalStateException("No texture atlas could be found with the resource location \"" + String.valueOf(location) + "\"");
        }
        return atlas;
    }

    private TextureAtlas findBestAtlas(boolean atlasGui, Supplier<TextureAtlas> atlasSupplier) {
        return atlasGui ? Laby.references().minecraftAtlases().getGuiAtlas() : atlasSupplier.get();
    }
}
