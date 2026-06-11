package net.labymod.v1_18_2.client.gfx.pipeline.renderer.text;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontFlags;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderStates;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.pipeline.material.LevelMaterial;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.util.function.FunctionMemoizeStorage;
import net.labymod.api.util.function.TriFunction;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/gfx/pipeline/renderer/text/VanillaGlyphUtil.class */
public final class VanillaGlyphUtil {
    private static final FunctionMemoizeStorage MEMOIZE = Laby.references().functionMemoizeStorage();
    private static final TriFunction<a, ResourceLocation, Boolean, Material> MATERIAL = MEMOIZE.memoize((displayMode, location, colored) -> {
        return LevelMaterial.builder(FontRenderStates.resolve(select(displayMode), colored.booleanValue())).setTexture(0, location).useLightmap().build();
    });

    /* JADX INFO: renamed from: net.labymod.v1_18_2.client.gfx.pipeline.renderer.text.VanillaGlyphUtil$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/gfx/pipeline/renderer/text/VanillaGlyphUtil$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$gui$Font$DisplayMode = new int[a.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$client$gui$Font$DisplayMode[a.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$client$gui$Font$DisplayMode[a.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$client$gui$Font$DisplayMode[a.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private static TextDrawMode select(a displayMode) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$client$gui$Font$DisplayMode[displayMode.ordinal()]) {
            case 1:
                return TextDrawMode.NORMAL;
            case 2:
                return TextDrawMode.SEE_THROUGH;
            case 3:
                return TextDrawMode.POLYGON_OFFSET;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public static Material material(a displayMode, BakedGlyphExtension extension) {
        return MATERIAL.apply(displayMode, extension.labyMod$getTextureLocation(), Boolean.valueOf(extension.labyMod$isColored()));
    }

    public static Material material(BakedGlyphExtension extension, int flags) {
        a displayMode = select(flags);
        return material(displayMode, extension);
    }

    private static a select(int flags) {
        if (FontFlags.hasFlag(flags, 4)) {
            return a.a;
        }
        if (FontFlags.hasFlag(flags, 8)) {
            return a.b;
        }
        if (FontFlags.hasFlag(flags, 16)) {
            return a.c;
        }
        return a.a;
    }
}
