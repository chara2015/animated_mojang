package net.labymod.v1_16_5.client.gfx.pipeline.renderer.text;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontFlags;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.pipeline.material.LevelMaterial;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.util.function.FunctionMemoizeStorage;
import net.labymod.api.util.function.TriFunction;
import net.labymod.v1_16_5.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/gfx/pipeline/renderer/text/VanillaGlyphUtil.class */
public final class VanillaGlyphUtil {
    private static final FunctionMemoizeStorage MEMOIZE = Laby.references().functionMemoizeStorage();
    private static final TriFunction<Boolean, ResourceLocation, Boolean, Material> MATERIAL = MEMOIZE.memoize((seeThrough, location, colored) -> {
        return LevelMaterial.builder(MinecraftUtil.resolveTextState(select(seeThrough.booleanValue()), colored.booleanValue())).setTexture(0, location).useLightmap().build();
    });

    private static TextDrawMode select(boolean seeThrough) {
        return seeThrough ? TextDrawMode.SEE_THROUGH : TextDrawMode.NORMAL;
    }

    public static Material material(boolean seeThrough, BakedGlyphExtension extension) {
        return MATERIAL.apply(Boolean.valueOf(seeThrough), extension.labyMod$getTextureLocation(), Boolean.valueOf(extension.labyMod$isColored()));
    }

    public static Material material(BakedGlyphExtension extension, int flags) {
        boolean seeThrough = select(flags);
        return material(seeThrough, extension);
    }

    private static boolean select(int flags) {
        if (!FontFlags.hasFlag(flags, 4) && FontFlags.hasFlag(flags, 8)) {
            return true;
        }
        return false;
    }
}
