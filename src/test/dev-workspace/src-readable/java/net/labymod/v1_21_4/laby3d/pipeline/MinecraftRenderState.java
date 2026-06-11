package net.labymod.v1_21_4.laby3d.pipeline;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Optional;
import net.labymod.api.laby3d.pipeline.material.LevelMaterial;
import net.labymod.api.laby3d.pipeline.material.TextureBinding;
import net.labymod.laby3d.api.opengl.util.GlUtil;
import net.labymod.laby3d.api.pipeline.ComparisonStrategy;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.blend.BlendFunction;
import net.labymod.laby3d.api.pipeline.blend.DefaultBlendFunctions;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/laby3d/pipeline/MinecraftRenderState.class */
public final class MinecraftRenderState {
    private final RenderState renderState;
    private final fga vertexFormat;
    private final c drawMode;
    private final akv configId;
    private final boolean affectsCrumbling;
    private final boolean sortOnUpload;

    public MinecraftRenderState(RenderState renderState, fga vertexFormat, c drawMode, akv configId) {
        this(renderState, vertexFormat, drawMode, configId, false);
    }

    public MinecraftRenderState(RenderState renderState, fga vertexFormat, c drawMode, akv configId, boolean affectsCrumbling) {
        this.renderState = renderState;
        this.vertexFormat = vertexFormat;
        this.drawMode = drawMode;
        this.configId = configId;
        this.affectsCrumbling = affectsCrumbling;
        this.sortOnUpload = renderState.blendFunction().isPresent();
    }

    public RenderState getRenderState() {
        return this.renderState;
    }

    public fga getVertexFormat() {
        return this.vertexFormat;
    }

    public c getDrawMode() {
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
            cVar = gmi.aA;
        } else {
            cVar = gmi.aB;
        }
        a aVarA2 = aVarA.a(cVar).a(configureDepthTest(this.renderState.depthTestStrategy())).a(configureWriteMask(this.renderState.writeColor(), this.renderState.writeDepth())).a(configureTransparency(this.renderState.blendFunction())).a(new m(createShaderProgram())).a(new n((akv) ((TextureBinding) material.getTextures().get(0)).location().getMinecraftLocation(), bad.b, false));
        if (material.isUseLightmap()) {
            gVar = gmi.aw;
        } else {
            gVar = gmi.ax;
        }
        a aVarA3 = aVarA2.a(gVar);
        if (material.isUseOverlay()) {
            lVar = gmi.ay;
        } else {
            lVar = gmi.az;
        }
        return aVarA3.a(lVar).a(outline);
    }

    private gmr createShaderProgram() {
        return new gmr(this.configId, getVertexFormat(), gmp.a().a("v1_21_4").a());
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_4.laby3d.pipeline.MinecraftRenderState$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/laby3d/pipeline/MinecraftRenderState$1.class */
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
                return gmi.aC;
            case 3:
                return gmi.aE;
            case 4:
                return gmi.aF;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(strategy));
        }
    }

    private q configureWriteMask(boolean color, boolean depth) {
        if (color && depth) {
            return gmi.aG;
        }
        if (color) {
            return gmi.aH;
        }
        return gmi.aI;
    }

    private p configureTransparency(Optional<BlendFunction> blendFunction) {
        if (blendFunction.isEmpty()) {
            return gmi.c;
        }
        BlendFunction function = blendFunction.get();
        if (DefaultBlendFunctions.ADDITIVE == function) {
            return gmi.d;
        }
        if (DefaultBlendFunctions.TRANSLUCENT == function) {
            return gmi.i;
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
