package net.labymod.v1_21_8.client.gfx.pipeline.renderer.text;

import com.google.common.collect.UnmodifiableIterator;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontFlags;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderStates;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.pipeline.material.LevelMaterial;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.function.FunctionMemoizeStorage;
import net.labymod.api.util.function.TriFunction;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/gfx/pipeline/renderer/text/VanillaGlyphUtil.class */
public final class VanillaGlyphUtil {
    private static final FunctionMemoizeStorage MEMOIZE = Laby.references().functionMemoizeStorage();
    private static final Function<RenderPipeline, Boolean> COLORED = MEMOIZE.memoize(pipeline -> {
        return Boolean.valueOf(!pipeline.toString().contains("intensity"));
    });
    private static final TriFunction<a, gxz, RenderPipeline, Material> MATERIAL = MEMOIZE.memoize((displayMode, renderType, pipeline) -> {
        ResourceLocation location = Textures.WHITE;
        if (renderType instanceof a) {
            a compositeRenderType = (a) renderType;
            UnmodifiableIterator it = compositeRenderType.E.d.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                j jVar = (gxy) it.next();
                if (jVar instanceof j) {
                    j textureStateShard = jVar;
                    location = (ResourceLocation) textureStateShard.d().map(rl -> {
                        return (ResourceLocation) CastUtil.requireInstanceOf(rl, ResourceLocation.class);
                    }).orElse(Textures.WHITE);
                    break;
                }
            }
        }
        return LevelMaterial.builder(FontRenderStates.resolve(select(displayMode), COLORED.apply(pipeline).booleanValue())).setTexture(0, location).useLightmap().build();
    });

    /* JADX INFO: renamed from: net.labymod.v1_21_8.client.gfx.pipeline.renderer.text.VanillaGlyphUtil$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/gfx/pipeline/renderer/text/VanillaGlyphUtil$1.class */
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

    public static boolean isColored(RenderPipeline pipeline) {
        return COLORED.apply(pipeline).booleanValue();
    }

    public static Material material(a displayMode, gxz renderType, RenderPipeline pipeline) {
        return MATERIAL.apply(displayMode, renderType, pipeline);
    }

    public static Material material(gas glyph, int flags) {
        a displayMode = select(flags);
        gxz renderType = glyph.a(displayMode);
        return material(displayMode, renderType, glyph.b());
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
