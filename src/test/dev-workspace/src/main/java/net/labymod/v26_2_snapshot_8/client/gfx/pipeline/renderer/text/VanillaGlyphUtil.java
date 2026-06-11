package net.labymod.v26_2_snapshot_8.client.gfx.pipeline.renderer.text;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontFlags;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderStates;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.pipeline.material.LevelMaterial;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.function.FunctionMemoizeStorage;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/gfx/pipeline/renderer/text/VanillaGlyphUtil.class */
public final class VanillaGlyphUtil {
    private static final FunctionMemoizeStorage MEMOIZE = Laby.references().functionMemoizeStorage();
    private static final Function<RenderPipeline, Boolean> COLORED = MEMOIZE.memoize(pipeline -> {
        return Boolean.valueOf(!pipeline.toString().contains("intensity"));
    });
    private static final BiFunction<Font.DisplayMode, RenderType, Material> MATERIAL = MEMOIZE.memoize((displayMode, renderType) -> {
        LevelMaterial.Builder materialBuilder = LevelMaterial.builder(FontRenderStates.resolve(select(displayMode), COLORED.apply(renderType.pipeline()).booleanValue()));
        Map<String, RenderSetup.TextureBinding> textures = renderType.state.textures;
        setLocation(materialBuilder, textures, 0);
        return materialBuilder.useLightmap().build();
    });

    private static void setLocation(LevelMaterial.Builder builder, Map<String, RenderSetup.TextureBinding> textures, int index) {
        RenderSetup.TextureBinding textureBinding = textures.get("Sampler" + index);
        if (textureBinding != null) {
            builder.setTexture(index, (ResourceLocation) CastUtil.requireInstanceOf(textureBinding.location(), ResourceLocation.class));
        }
    }

    /* JADX INFO: renamed from: net.labymod.v26_2_snapshot_8.client.gfx.pipeline.renderer.text.VanillaGlyphUtil$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/gfx/pipeline/renderer/text/VanillaGlyphUtil$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$gui$Font$DisplayMode = new int[Font.DisplayMode.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$client$gui$Font$DisplayMode[Font.DisplayMode.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$client$gui$Font$DisplayMode[Font.DisplayMode.SEE_THROUGH.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$client$gui$Font$DisplayMode[Font.DisplayMode.POLYGON_OFFSET.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private static TextDrawMode select(Font.DisplayMode displayMode) throws MatchException {
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

    public static Material material(Font.DisplayMode displayMode, RenderType renderType) {
        return MATERIAL.apply(displayMode, renderType);
    }

    public static Material material(TextRenderable renderable, int flags) {
        Font.DisplayMode displayMode = select(flags);
        RenderType renderType = renderable.renderType(displayMode);
        return material(displayMode, renderType);
    }

    private static Font.DisplayMode select(int flags) {
        if (FontFlags.hasFlag(flags, 4)) {
            return Font.DisplayMode.NORMAL;
        }
        if (FontFlags.hasFlag(flags, 8)) {
            return Font.DisplayMode.SEE_THROUGH;
        }
        if (FontFlags.hasFlag(flags, 16)) {
            return Font.DisplayMode.POLYGON_OFFSET;
        }
        return Font.DisplayMode.NORMAL;
    }
}
