package net.labymod.v1_17_1.laby3d.pipeline;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/laby3d/pipeline/MinecraftRenderState.class */
public final class MinecraftRenderState {
    private final RenderState renderState;
    private final dqq vertexFormat;
    private final b drawMode;
    private final ResourceLocation configId;
    private final boolean affectsCrumbling;
    private final boolean sortOnUpload;
    private final Lazy<ent> shaderInstance;

    public MinecraftRenderState(RenderState renderState, dqq vertexFormat, b drawMode, ResourceLocation configId) {
        this(renderState, vertexFormat, drawMode, configId, false);
    }

    public MinecraftRenderState(RenderState renderState, dqq vertexFormat, b drawMode, ResourceLocation configId, boolean affectsCrumbling) {
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

    public dqq getVertexFormat() {
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
        b bVar;
        f fVar;
        k kVar;
        a aVarA = b.a();
        if (this.renderState.cull()) {
            bVar = enp.aq;
        } else {
            bVar = enp.ar;
        }
        a aVarA2 = aVarA.a(bVar).a(configureDepthTest(this.renderState.depthTestStrategy())).a(configureWriteMask(this.renderState.writeColor(), this.renderState.writeDepth())).a(configureTransparency(this.renderState.blendFunction()));
        Lazy<ent> lazy = this.shaderInstance;
        Objects.requireNonNull(lazy);
        a aVarA3 = aVarA2.a(new l(lazy::get)).a(new m((ww) ((TextureBinding) material.getTextures().get(0)).location().getMinecraftLocation(), false, false));
        if (material.isUseLightmap()) {
            fVar = enp.am;
        } else {
            fVar = enp.an;
        }
        a aVarA4 = aVarA3.a(fVar);
        if (material.isUseOverlay()) {
            kVar = enp.ao;
        } else {
            kVar = enp.ap;
        }
        return aVarA4.a(kVar).a(outline);
    }

    private ent createShaderProgram() {
        try {
            return new LabyShaderInstance(dvp.C().N(), this.configId, getVertexFormat());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_17_1.laby3d.pipeline.MinecraftRenderState$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/laby3d/pipeline/MinecraftRenderState$1.class */
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
        }
    }

    private c configureDepthTest(ComparisonStrategy strategy) {
        switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$pipeline$ComparisonStrategy[strategy.ordinal()]) {
            case 1:
            case 2:
                return enp.as;
            case 3:
                return enp.au;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(strategy));
        }
    }

    private p configureWriteMask(boolean color, boolean depth) {
        if (color && depth) {
            return enp.av;
        }
        if (color) {
            return enp.aw;
        }
        return enp.ax;
    }

    private o configureTransparency(Optional<BlendFunction> blendFunction) {
        if (blendFunction.isEmpty()) {
            return enp.b;
        }
        BlendFunction function = blendFunction.get();
        if (DefaultBlendFunctions.ADDITIVE == function) {
            return enp.c;
        }
        if (DefaultBlendFunctions.TRANSLUCENT == function) {
            return enp.g;
        }
        return new o("custom_blending_" + String.valueOf(function), () -> {
            RenderSystem.enableBlend();
            RenderSystem.blendEquation(GlUtil.toGlBlendOp(function.blendOp()));
            RenderSystem.blendFuncSeparate(GlUtil.toGlBlendFactor(function.srcBlend()), GlUtil.toGlBlendFactor(function.dstBlend()), GlUtil.toGlBlendFactor(function.srcBlendAlpha()), GlUtil.toGlBlendFactor(function.dstBlendAlpha()));
        }, () -> {
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
        });
    }
}
