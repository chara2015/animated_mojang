package net.labymod.v1_21_3.laby3d.pipeline;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Optional;
import net.labymod.api.laby3d.pipeline.material.LevelMaterial;
import net.labymod.api.laby3d.pipeline.material.TextureBinding;
import net.labymod.laby3d.api.opengl.util.GlUtil;
import net.labymod.laby3d.api.pipeline.ComparisonStrategy;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.blend.BlendFunction;
import net.labymod.laby3d.api.pipeline.blend.DefaultBlendFunctions;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/laby3d/pipeline/MinecraftRenderState.class */
public final class MinecraftRenderState {
    private final RenderState renderState;
    private final fgx vertexFormat;
    private final c drawMode;
    private final alz configId;
    private final boolean affectsCrumbling;
    private final boolean sortOnUpload;

    public MinecraftRenderState(RenderState renderState, fgx vertexFormat, c drawMode, alz configId) {
        this(renderState, vertexFormat, drawMode, configId, false);
    }

    public MinecraftRenderState(RenderState renderState, fgx vertexFormat, c drawMode, alz configId, boolean affectsCrumbling) {
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

    public fgx getVertexFormat() {
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
            cVar = glu.ay;
        } else {
            cVar = glu.az;
        }
        a aVarA2 = aVarA.a(cVar).a(configureDepthTest(this.renderState.depthTestStrategy())).a(configureWriteMask(this.renderState.writeColor(), this.renderState.writeDepth())).a(configureTransparency(this.renderState.blendFunction())).a(new m(createShaderProgram())).a(new n((alz) ((TextureBinding) material.getTextures().get(0)).location().getMinecraftLocation(), bbi.b, false));
        if (material.isUseLightmap()) {
            gVar = glu.au;
        } else {
            gVar = glu.av;
        }
        a aVarA3 = aVarA2.a(gVar);
        if (material.isUseOverlay()) {
            lVar = glu.aw;
        } else {
            lVar = glu.ax;
        }
        return aVarA3.a(lVar).a(outline);
    }

    private gmd createShaderProgram() {
        return new gmd(this.configId, getVertexFormat(), gmb.a().a("v1_21_3").a());
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_3.laby3d.pipeline.MinecraftRenderState$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/laby3d/pipeline/MinecraftRenderState$1.class */
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
                return glu.aA;
            case 3:
                return glu.aC;
            case 4:
                return glu.aD;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(strategy));
        }
    }

    private q configureWriteMask(boolean color, boolean depth) {
        if (color && depth) {
            return glu.aE;
        }
        if (color) {
            return glu.aF;
        }
        return glu.aG;
    }

    private p configureTransparency(Optional<BlendFunction> blendFunction) {
        if (blendFunction.isEmpty()) {
            return glu.c;
        }
        BlendFunction function = blendFunction.get();
        if (DefaultBlendFunctions.ADDITIVE == function) {
            return glu.d;
        }
        if (DefaultBlendFunctions.TRANSLUCENT == function) {
            return glu.h;
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
