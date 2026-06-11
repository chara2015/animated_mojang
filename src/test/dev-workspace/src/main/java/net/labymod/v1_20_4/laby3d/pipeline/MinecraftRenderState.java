package net.labymod.v1_20_4.laby3d.pipeline;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.pipeline.material.LevelMaterial;
import net.labymod.api.laby3d.pipeline.material.TextureBinding;
import net.labymod.api.util.Lazy;
import net.labymod.laby3d.api.opengl.util.GlUtil;
import net.labymod.laby3d.api.pipeline.ComparisonStrategy;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.blend.BlendFunction;
import net.labymod.laby3d.api.pipeline.blend.DefaultBlendFunctions;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/laby3d/pipeline/MinecraftRenderState.class */
public final class MinecraftRenderState {
    private final RenderState renderState;
    private final eqg vertexFormat;
    private final b drawMode;
    private final ResourceLocation configId;
    private final boolean affectsCrumbling;
    private final boolean sortOnUpload;
    private final Lazy<ftv> shaderInstance;

    public MinecraftRenderState(RenderState renderState, eqg vertexFormat, b drawMode, ResourceLocation configId) {
        this(renderState, vertexFormat, drawMode, configId, false);
    }

    public MinecraftRenderState(RenderState renderState, eqg vertexFormat, b drawMode, ResourceLocation configId, boolean affectsCrumbling) {
        this.renderState = renderState;
        this.vertexFormat = vertexFormat;
        this.drawMode = drawMode;
        this.configId = configId;
        this.affectsCrumbling = affectsCrumbling;
        this.sortOnUpload = renderState.blendFunction().isPresent();
        this.shaderInstance = Lazy.of(this::createShaderProgram);
    }

    public RenderState getRenderState() {
        return this.renderState;
    }

    public eqg getVertexFormat() {
        return this.vertexFormat;
    }

    public b getDrawMode() {
        return this.drawMode;
    }

    public boolean affectsCrumbling() {
        return this.affectsCrumbling;
    }

    public boolean sortOnUpload() {
        return this.sortOnUpload;
    }

    public b create(LevelMaterial material, boolean outline) {
        c cVar;
        g gVar;
        l lVar;
        a aVarA = b.a();
        if (this.renderState.cull()) {
            cVar = fto.aw;
        } else {
            cVar = fto.ax;
        }
        a aVarA2 = aVarA.a(cVar).a(configureDepthTest(this.renderState.depthTestStrategy())).a(configureWriteMask(this.renderState.writeColor(), this.renderState.writeDepth())).a(configureTransparency(this.renderState.blendFunction()));
        Lazy<ftv> lazy = this.shaderInstance;
        Objects.requireNonNull(lazy);
        a aVarA3 = aVarA2.a(new m(lazy::get)).a(new n((ahg) ((TextureBinding) material.getTextures().get(0)).location().getMinecraftLocation(), false, false));
        if (material.isUseLightmap()) {
            gVar = fto.as;
        } else {
            gVar = fto.at;
        }
        a aVarA4 = aVarA3.a(gVar);
        if (material.isUseOverlay()) {
            lVar = fto.au;
        } else {
            lVar = fto.av;
        }
        return aVarA4.a(lVar).a(outline);
    }

    private ftv createShaderProgram() {
        try {
            return new LabyShaderInstance(evi.O().Z(), this.configId, getVertexFormat());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_20_4.laby3d.pipeline.MinecraftRenderState$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/laby3d/pipeline/MinecraftRenderState$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$pipeline$ComparisonStrategy = new int[ComparisonStrategy.values().length];

        static {
            try {
                $SwitchMap$net$labymod$laby3d$api$pipeline$ComparisonStrategy[ComparisonStrategy.NEVER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$pipeline$ComparisonStrategy[ComparisonStrategy.ALWAYS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$pipeline$ComparisonStrategy[ComparisonStrategy.LEQUAL.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$pipeline$ComparisonStrategy[ComparisonStrategy.GREATER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private d configureDepthTest(ComparisonStrategy strategy) {
        switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$pipeline$ComparisonStrategy[strategy.ordinal()]) {
            case 1:
            case 2:
                return fto.ay;
            case 3:
                return fto.aA;
            case 4:
                return fto.aB;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(strategy));
        }
    }

    private q configureWriteMask(boolean color, boolean depth) {
        if (color && depth) {
            return fto.aC;
        }
        if (color) {
            return fto.aD;
        }
        return fto.aE;
    }

    private p configureTransparency(Optional<BlendFunction> blendFunction) {
        if (blendFunction.isEmpty()) {
            return fto.c;
        }
        BlendFunction function = blendFunction.get();
        if (DefaultBlendFunctions.ADDITIVE == function) {
            return fto.d;
        }
        if (DefaultBlendFunctions.TRANSLUCENT == function) {
            return fto.h;
        }
        return new p("custom_blending_" + String.valueOf(function), () -> {
            RenderSystem.enableBlend();
            RenderSystem.blendEquation(GlUtil.toGlBlendOp(function.blendOp()));
            RenderSystem.blendFuncSeparate(GlUtil.toGlBlendFactor(function.srcBlend()), GlUtil.toGlBlendFactor(function.dstBlend()), GlUtil.toGlBlendFactor(function.srcBlendAlpha()), GlUtil.toGlBlendFactor(function.dstBlendAlpha()));
        }, () -> {
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
        });
    }
}
