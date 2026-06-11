package net.labymod.v1_21_11.client.gfx.pipeline.blaze3d.program;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import net.labymod.v1_21_11.laby3d.pipeline.opengl.Laby3DGlTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/gfx/pipeline/blaze3d/program/LabyRenderSetup.class */
public final class LabyRenderSetup extends ijr {
    private final Map<String, c> textures;

    public LabyRenderSetup(RenderPipeline pipeline, ijp layeringTransform, ijq outputTarget, iju textureTransform, a outlineProperty, boolean affectsCrumbling, boolean sortOnUpload, int bufferSize, Map<String, c> textures) {
        super(pipeline, Collections.emptyMap(), false, false, layeringTransform, outputTarget, textureTransform, outlineProperty, affectsCrumbling, sortOnUpload, bufferSize);
        this.textures = textures;
    }

    public static Builder labyBuilder(RenderPipeline pipeline) {
        return new Builder(pipeline);
    }

    public Map<String, c> a() {
        return this.textures;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/gfx/pipeline/blaze3d/program/LabyRenderSetup$Builder.class */
    public static class Builder {
        private final RenderPipeline pipeline;
        private ijp layeringTransform = ijp.a;
        private ijq outputTarget = ijq.a;
        private iju textureTransform = iju.b;
        private boolean affectsCrumbling = false;
        private boolean sortOnUpload = false;
        private int bufferSize = GlConst.GL_2D;
        private a outlineProperty = a.a;
        private final Map<String, c> textures = new HashMap();

        Builder(RenderPipeline pipeline) {
            this.pipeline = pipeline;
        }

        public Builder withTextures(TextureBindingSet textureBindingSet) {
            DeviceTextureView[] textures = textureBindingSet.textures();
            for (int index = 0; index < textures.length; index++) {
                DeviceTextureView textureView = textures[index];
                this.textures.put("Sampler" + index, new c(new Laby3DGlTextureView(textureView), MinecraftUtil.getSampler(textureView.texture().samplerDescription())));
            }
            return this;
        }

        public Builder affectsCrumbling() {
            this.affectsCrumbling = true;
            return this;
        }

        public Builder sortOnUpload() {
            this.sortOnUpload = true;
            return this;
        }

        public Builder bufferSize(int bufferSizeIn) {
            this.bufferSize = bufferSizeIn;
            return this;
        }

        public Builder setLayeringTransform(ijp layerTransformIn) {
            this.layeringTransform = layerTransformIn;
            return this;
        }

        public Builder setOutputTarget(ijq targetIn) {
            this.outputTarget = targetIn;
            return this;
        }

        public Builder setTextureTransform(iju texTransformIn) {
            this.textureTransform = texTransformIn;
            return this;
        }

        public Builder setOutline(a outlineIn) {
            this.outlineProperty = outlineIn;
            return this;
        }

        public ijr build() {
            return new LabyRenderSetup(this.pipeline, this.layeringTransform, this.outputTarget, this.textureTransform, this.outlineProperty, this.affectsCrumbling, this.sortOnUpload, this.bufferSize, this.textures);
        }
    }
}
