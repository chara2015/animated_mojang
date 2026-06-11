package com.mojang.blaze3d.pipeline;

import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.platform.LogicOp;
import com.mojang.blaze3d.platform.PolygonMode;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.textures.TextureFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.SharedConstants;
import net.minecraft.client.renderer.ShaderDefines;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/pipeline/RenderPipeline.class */
@DontObfuscate
public class RenderPipeline {
    private final Identifier location;
    private final Identifier vertexShader;
    private final Identifier fragmentShader;
    private final ShaderDefines shaderDefines;
    private final List<String> samplers;
    private final List<UniformDescription> uniforms;
    private final DepthTestFunction depthTestFunction;
    private final PolygonMode polygonMode;
    private final boolean cull;
    private final LogicOp colorLogic;
    private final Optional<BlendFunction> blendFunction;
    private final boolean writeColor;
    private final boolean writeAlpha;
    private final boolean writeDepth;
    private final VertexFormat vertexFormat;
    private final VertexFormat.Mode vertexFormatMode;
    private final float depthBiasScaleFactor;
    private final float depthBiasConstant;
    private final int sortKey;
    private static int sortKeySeed;

    protected RenderPipeline(Identifier $$0, Identifier $$1, Identifier $$2, ShaderDefines $$3, List<String> $$4, List<UniformDescription> $$5, Optional<BlendFunction> $$6, DepthTestFunction $$7, PolygonMode $$8, boolean $$9, boolean $$10, boolean $$11, boolean $$12, LogicOp $$13, VertexFormat $$14, VertexFormat.Mode $$15, float $$16, float $$17, int $$18) {
        this.location = $$0;
        this.vertexShader = $$1;
        this.fragmentShader = $$2;
        this.shaderDefines = $$3;
        this.samplers = $$4;
        this.uniforms = $$5;
        this.depthTestFunction = $$7;
        this.polygonMode = $$8;
        this.cull = $$9;
        this.blendFunction = $$6;
        this.writeColor = $$10;
        this.writeAlpha = $$11;
        this.writeDepth = $$12;
        this.colorLogic = $$13;
        this.vertexFormat = $$14;
        this.vertexFormatMode = $$15;
        this.depthBiasScaleFactor = $$16;
        this.depthBiasConstant = $$17;
        this.sortKey = $$18;
    }

    public int getSortKey() {
        return SharedConstants.DEBUG_SHUFFLE_UI_RENDERING_ORDER ? super.hashCode() * (sortKeySeed + 1) : this.sortKey;
    }

    public static void updateSortKeySeed() {
        sortKeySeed = Math.round(100000.0f * ((float) Math.random()));
    }

    public String toString() {
        return this.location.toString();
    }

    public DepthTestFunction getDepthTestFunction() {
        return this.depthTestFunction;
    }

    public PolygonMode getPolygonMode() {
        return this.polygonMode;
    }

    public boolean isCull() {
        return this.cull;
    }

    public LogicOp getColorLogic() {
        return this.colorLogic;
    }

    public Optional<BlendFunction> getBlendFunction() {
        return this.blendFunction;
    }

    public boolean isWriteColor() {
        return this.writeColor;
    }

    public boolean isWriteAlpha() {
        return this.writeAlpha;
    }

    public boolean isWriteDepth() {
        return this.writeDepth;
    }

    public float getDepthBiasScaleFactor() {
        return this.depthBiasScaleFactor;
    }

    public float getDepthBiasConstant() {
        return this.depthBiasConstant;
    }

    public Identifier getLocation() {
        return this.location;
    }

    public VertexFormat getVertexFormat() {
        return this.vertexFormat;
    }

    public VertexFormat.Mode getVertexFormatMode() {
        return this.vertexFormatMode;
    }

    public Identifier getVertexShader() {
        return this.vertexShader;
    }

    public Identifier getFragmentShader() {
        return this.fragmentShader;
    }

    public ShaderDefines getShaderDefines() {
        return this.shaderDefines;
    }

    public List<String> getSamplers() {
        return this.samplers;
    }

    public List<UniformDescription> getUniforms() {
        return this.uniforms;
    }

    public boolean wantsDepthTexture() {
        return (this.depthTestFunction == DepthTestFunction.NO_DEPTH_TEST && this.depthBiasConstant == 0.0f && this.depthBiasScaleFactor == 0.0f && !this.writeDepth) ? false : true;
    }

    public static Builder builder(Snippet... $$0) {
        Builder $$1 = new Builder();
        for (Snippet $$2 : $$0) {
            $$1.withSnippet($$2);
        }
        return $$1;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/pipeline/RenderPipeline$Builder.class */
    @DontObfuscate
    public static class Builder {
        private static int nextPipelineSortKey;
        private Optional<Identifier> location = Optional.empty();
        private Optional<Identifier> fragmentShader = Optional.empty();
        private Optional<Identifier> vertexShader = Optional.empty();
        private Optional<ShaderDefines.Builder> definesBuilder = Optional.empty();
        private Optional<List<String>> samplers = Optional.empty();
        private Optional<List<UniformDescription>> uniforms = Optional.empty();
        private Optional<DepthTestFunction> depthTestFunction = Optional.empty();
        private Optional<PolygonMode> polygonMode = Optional.empty();
        private Optional<Boolean> cull = Optional.empty();
        private Optional<Boolean> writeColor = Optional.empty();
        private Optional<Boolean> writeAlpha = Optional.empty();
        private Optional<Boolean> writeDepth = Optional.empty();
        private Optional<LogicOp> colorLogic = Optional.empty();
        private Optional<BlendFunction> blendFunction = Optional.empty();
        private Optional<VertexFormat> vertexFormat = Optional.empty();
        private Optional<VertexFormat.Mode> vertexFormatMode = Optional.empty();
        private float depthBiasScaleFactor;
        private float depthBiasConstant;

        Builder() {
        }

        public Builder withLocation(String $$0) {
            this.location = Optional.of(Identifier.withDefaultNamespace($$0));
            return this;
        }

        public Builder withLocation(Identifier $$0) {
            this.location = Optional.of($$0);
            return this;
        }

        public Builder withFragmentShader(String $$0) {
            this.fragmentShader = Optional.of(Identifier.withDefaultNamespace($$0));
            return this;
        }

        public Builder withFragmentShader(Identifier $$0) {
            this.fragmentShader = Optional.of($$0);
            return this;
        }

        public Builder withVertexShader(String $$0) {
            this.vertexShader = Optional.of(Identifier.withDefaultNamespace($$0));
            return this;
        }

        public Builder withVertexShader(Identifier $$0) {
            this.vertexShader = Optional.of($$0);
            return this;
        }

        public Builder withShaderDefine(String $$0) {
            if (this.definesBuilder.isEmpty()) {
                this.definesBuilder = Optional.of(ShaderDefines.builder());
            }
            this.definesBuilder.get().define($$0);
            return this;
        }

        public Builder withShaderDefine(String $$0, int $$1) {
            if (this.definesBuilder.isEmpty()) {
                this.definesBuilder = Optional.of(ShaderDefines.builder());
            }
            this.definesBuilder.get().define($$0, $$1);
            return this;
        }

        public Builder withShaderDefine(String $$0, float $$1) {
            if (this.definesBuilder.isEmpty()) {
                this.definesBuilder = Optional.of(ShaderDefines.builder());
            }
            this.definesBuilder.get().define($$0, $$1);
            return this;
        }

        public Builder withSampler(String $$0) {
            if (this.samplers.isEmpty()) {
                this.samplers = Optional.of(new ArrayList());
            }
            this.samplers.get().add($$0);
            return this;
        }

        public Builder withUniform(String $$0, UniformType $$1) {
            if (this.uniforms.isEmpty()) {
                this.uniforms = Optional.of(new ArrayList());
            }
            if ($$1 == UniformType.TEXEL_BUFFER) {
                throw new IllegalArgumentException("Cannot use texel buffer without specifying texture format");
            }
            this.uniforms.get().add(new UniformDescription($$0, $$1));
            return this;
        }

        public Builder withUniform(String $$0, UniformType $$1, TextureFormat $$2) {
            if (this.uniforms.isEmpty()) {
                this.uniforms = Optional.of(new ArrayList());
            }
            if ($$1 != UniformType.TEXEL_BUFFER) {
                throw new IllegalArgumentException("Only texel buffer can specify texture format");
            }
            this.uniforms.get().add(new UniformDescription($$0, $$2));
            return this;
        }

        public Builder withDepthTestFunction(DepthTestFunction $$0) {
            this.depthTestFunction = Optional.of($$0);
            return this;
        }

        public Builder withPolygonMode(PolygonMode $$0) {
            this.polygonMode = Optional.of($$0);
            return this;
        }

        public Builder withCull(boolean $$0) {
            this.cull = Optional.of(Boolean.valueOf($$0));
            return this;
        }

        public Builder withBlend(BlendFunction $$0) {
            this.blendFunction = Optional.of($$0);
            return this;
        }

        public Builder withoutBlend() {
            this.blendFunction = Optional.empty();
            return this;
        }

        public Builder withColorWrite(boolean $$0) {
            this.writeColor = Optional.of(Boolean.valueOf($$0));
            this.writeAlpha = Optional.of(Boolean.valueOf($$0));
            return this;
        }

        public Builder withColorWrite(boolean $$0, boolean $$1) {
            this.writeColor = Optional.of(Boolean.valueOf($$0));
            this.writeAlpha = Optional.of(Boolean.valueOf($$1));
            return this;
        }

        public Builder withDepthWrite(boolean $$0) {
            this.writeDepth = Optional.of(Boolean.valueOf($$0));
            return this;
        }

        @Deprecated
        public Builder withColorLogic(LogicOp $$0) {
            this.colorLogic = Optional.of($$0);
            return this;
        }

        public Builder withVertexFormat(VertexFormat $$0, VertexFormat.Mode $$1) {
            this.vertexFormat = Optional.of($$0);
            this.vertexFormatMode = Optional.of($$1);
            return this;
        }

        public Builder withDepthBias(float $$0, float $$1) {
            this.depthBiasScaleFactor = $$0;
            this.depthBiasConstant = $$1;
            return this;
        }

        void withSnippet(Snippet $$0) {
            if ($$0.vertexShader.isPresent()) {
                this.vertexShader = $$0.vertexShader;
            }
            if ($$0.fragmentShader.isPresent()) {
                this.fragmentShader = $$0.fragmentShader;
            }
            if ($$0.shaderDefines.isPresent()) {
                if (this.definesBuilder.isEmpty()) {
                    this.definesBuilder = Optional.of(ShaderDefines.builder());
                }
                ShaderDefines $$1 = $$0.shaderDefines.get();
                for (Map.Entry<String, String> $$2 : $$1.values().entrySet()) {
                    this.definesBuilder.get().define($$2.getKey(), $$2.getValue());
                }
                for (String $$3 : $$1.flags()) {
                    this.definesBuilder.get().define($$3);
                }
            }
            $$0.samplers.ifPresent($$02 -> {
                if (this.samplers.isPresent()) {
                    this.samplers.get().addAll($$02);
                } else {
                    this.samplers = Optional.of(new ArrayList($$02));
                }
            });
            $$0.uniforms.ifPresent($$03 -> {
                if (this.uniforms.isPresent()) {
                    this.uniforms.get().addAll($$03);
                } else {
                    this.uniforms = Optional.of(new ArrayList($$03));
                }
            });
            if ($$0.depthTestFunction.isPresent()) {
                this.depthTestFunction = $$0.depthTestFunction;
            }
            if ($$0.cull.isPresent()) {
                this.cull = $$0.cull;
            }
            if ($$0.writeColor.isPresent()) {
                this.writeColor = $$0.writeColor;
            }
            if ($$0.writeAlpha.isPresent()) {
                this.writeAlpha = $$0.writeAlpha;
            }
            if ($$0.writeDepth.isPresent()) {
                this.writeDepth = $$0.writeDepth;
            }
            if ($$0.colorLogic.isPresent()) {
                this.colorLogic = $$0.colorLogic;
            }
            if ($$0.blendFunction.isPresent()) {
                this.blendFunction = $$0.blendFunction;
            }
            if ($$0.vertexFormat.isPresent()) {
                this.vertexFormat = $$0.vertexFormat;
            }
            if ($$0.vertexFormatMode.isPresent()) {
                this.vertexFormatMode = $$0.vertexFormatMode;
            }
        }

        public Snippet buildSnippet() {
            return new Snippet(this.vertexShader, this.fragmentShader, this.definesBuilder.map((v0) -> {
                return v0.build();
            }), this.samplers.map(Collections::unmodifiableList), this.uniforms.map(Collections::unmodifiableList), this.blendFunction, this.depthTestFunction, this.polygonMode, this.cull, this.writeColor, this.writeAlpha, this.writeDepth, this.colorLogic, this.vertexFormat, this.vertexFormatMode);
        }

        public RenderPipeline build() {
            if (this.location.isEmpty()) {
                throw new IllegalStateException("Missing location");
            }
            if (this.vertexShader.isEmpty()) {
                throw new IllegalStateException("Missing vertex shader");
            }
            if (this.fragmentShader.isEmpty()) {
                throw new IllegalStateException("Missing fragment shader");
            }
            if (this.vertexFormat.isEmpty()) {
                throw new IllegalStateException("Missing vertex buffer format");
            }
            if (this.vertexFormatMode.isEmpty()) {
                throw new IllegalStateException("Missing vertex mode");
            }
            Identifier identifier = this.location.get();
            Identifier identifier2 = this.vertexShader.get();
            Identifier identifier3 = this.fragmentShader.get();
            ShaderDefines shaderDefinesBuild = this.definesBuilder.orElse(ShaderDefines.builder()).build();
            List listCopyOf = List.copyOf(this.samplers.orElse(new ArrayList()));
            List<UniformDescription> listOrElse = this.uniforms.orElse(Collections.emptyList());
            Optional<BlendFunction> optional = this.blendFunction;
            DepthTestFunction depthTestFunctionOrElse = this.depthTestFunction.orElse(DepthTestFunction.LEQUAL_DEPTH_TEST);
            PolygonMode polygonModeOrElse = this.polygonMode.orElse(PolygonMode.FILL);
            boolean zBooleanValue = this.cull.orElse(true).booleanValue();
            boolean zBooleanValue2 = this.writeColor.orElse(true).booleanValue();
            boolean zBooleanValue3 = this.writeAlpha.orElse(true).booleanValue();
            boolean zBooleanValue4 = this.writeDepth.orElse(true).booleanValue();
            LogicOp logicOpOrElse = this.colorLogic.orElse(LogicOp.NONE);
            VertexFormat vertexFormat = this.vertexFormat.get();
            VertexFormat.Mode mode = this.vertexFormatMode.get();
            float f = this.depthBiasScaleFactor;
            float f2 = this.depthBiasConstant;
            int i = nextPipelineSortKey;
            nextPipelineSortKey = i + 1;
            return new RenderPipeline(identifier, identifier2, identifier3, shaderDefinesBuild, listCopyOf, listOrElse, optional, depthTestFunctionOrElse, polygonModeOrElse, zBooleanValue, zBooleanValue2, zBooleanValue3, zBooleanValue4, logicOpOrElse, vertexFormat, mode, f, f2, i);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/pipeline/RenderPipeline$UniformDescription.class */
    @DontObfuscate
    public static final class UniformDescription extends Record {
        private final String name;
        private final UniformType type;
        private final TextureFormat textureFormat;

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, UniformDescription.class), UniformDescription.class, "name;type;textureFormat", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$UniformDescription;->name:Ljava/lang/String;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$UniformDescription;->type:Lcom/mojang/blaze3d/shaders/UniformType;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$UniformDescription;->textureFormat:Lcom/mojang/blaze3d/textures/TextureFormat;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, UniformDescription.class), UniformDescription.class, "name;type;textureFormat", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$UniformDescription;->name:Ljava/lang/String;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$UniformDescription;->type:Lcom/mojang/blaze3d/shaders/UniformType;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$UniformDescription;->textureFormat:Lcom/mojang/blaze3d/textures/TextureFormat;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, UniformDescription.class, Object.class), UniformDescription.class, "name;type;textureFormat", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$UniformDescription;->name:Ljava/lang/String;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$UniformDescription;->type:Lcom/mojang/blaze3d/shaders/UniformType;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$UniformDescription;->textureFormat:Lcom/mojang/blaze3d/textures/TextureFormat;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String name() {
            return this.name;
        }

        public UniformType type() {
            return this.type;
        }

        public TextureFormat textureFormat() {
            return this.textureFormat;
        }

        public UniformDescription(String $$0, UniformType $$1, TextureFormat $$2) {
            this.name = $$0;
            this.type = $$1;
            this.textureFormat = $$2;
        }

        public UniformDescription(String $$0, UniformType $$1) {
            this($$0, $$1, null);
            if ($$1 == UniformType.TEXEL_BUFFER) {
                throw new IllegalArgumentException("Texel buffer needs a texture format");
            }
        }

        public UniformDescription(String $$0, TextureFormat $$1) {
            this($$0, UniformType.TEXEL_BUFFER, $$1);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/pipeline/RenderPipeline$Snippet.class */
    @DontObfuscate
    public static final class Snippet extends Record {
        private final Optional<Identifier> vertexShader;
        private final Optional<Identifier> fragmentShader;
        private final Optional<ShaderDefines> shaderDefines;
        private final Optional<List<String>> samplers;
        private final Optional<List<UniformDescription>> uniforms;
        private final Optional<BlendFunction> blendFunction;
        private final Optional<DepthTestFunction> depthTestFunction;
        private final Optional<PolygonMode> polygonMode;
        private final Optional<Boolean> cull;
        private final Optional<Boolean> writeColor;
        private final Optional<Boolean> writeAlpha;
        private final Optional<Boolean> writeDepth;
        private final Optional<LogicOp> colorLogic;
        private final Optional<VertexFormat> vertexFormat;
        private final Optional<VertexFormat.Mode> vertexFormatMode;

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Snippet.class), Snippet.class, "vertexShader;fragmentShader;shaderDefines;samplers;uniforms;blendFunction;depthTestFunction;polygonMode;cull;writeColor;writeAlpha;writeDepth;colorLogic;vertexFormat;vertexFormatMode", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->vertexShader:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->fragmentShader:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->shaderDefines:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->samplers:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->uniforms:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->blendFunction:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->depthTestFunction:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->polygonMode:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->cull:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->writeColor:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->writeAlpha:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->writeDepth:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->colorLogic:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->vertexFormat:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->vertexFormatMode:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Snippet.class), Snippet.class, "vertexShader;fragmentShader;shaderDefines;samplers;uniforms;blendFunction;depthTestFunction;polygonMode;cull;writeColor;writeAlpha;writeDepth;colorLogic;vertexFormat;vertexFormatMode", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->vertexShader:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->fragmentShader:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->shaderDefines:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->samplers:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->uniforms:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->blendFunction:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->depthTestFunction:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->polygonMode:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->cull:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->writeColor:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->writeAlpha:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->writeDepth:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->colorLogic:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->vertexFormat:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->vertexFormatMode:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Snippet.class, Object.class), Snippet.class, "vertexShader;fragmentShader;shaderDefines;samplers;uniforms;blendFunction;depthTestFunction;polygonMode;cull;writeColor;writeAlpha;writeDepth;colorLogic;vertexFormat;vertexFormatMode", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->vertexShader:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->fragmentShader:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->shaderDefines:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->samplers:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->uniforms:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->blendFunction:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->depthTestFunction:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->polygonMode:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->cull:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->writeColor:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->writeAlpha:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->writeDepth:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->colorLogic:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->vertexFormat:Ljava/util/Optional;", "FIELD:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;->vertexFormatMode:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Optional<Identifier> vertexShader() {
            return this.vertexShader;
        }

        public Optional<Identifier> fragmentShader() {
            return this.fragmentShader;
        }

        public Optional<ShaderDefines> shaderDefines() {
            return this.shaderDefines;
        }

        public Optional<List<String>> samplers() {
            return this.samplers;
        }

        public Optional<List<UniformDescription>> uniforms() {
            return this.uniforms;
        }

        public Optional<BlendFunction> blendFunction() {
            return this.blendFunction;
        }

        public Optional<DepthTestFunction> depthTestFunction() {
            return this.depthTestFunction;
        }

        public Optional<PolygonMode> polygonMode() {
            return this.polygonMode;
        }

        public Optional<Boolean> cull() {
            return this.cull;
        }

        public Optional<Boolean> writeColor() {
            return this.writeColor;
        }

        public Optional<Boolean> writeAlpha() {
            return this.writeAlpha;
        }

        public Optional<Boolean> writeDepth() {
            return this.writeDepth;
        }

        public Optional<LogicOp> colorLogic() {
            return this.colorLogic;
        }

        public Optional<VertexFormat> vertexFormat() {
            return this.vertexFormat;
        }

        public Optional<VertexFormat.Mode> vertexFormatMode() {
            return this.vertexFormatMode;
        }

        public Snippet(Optional<Identifier> $$0, Optional<Identifier> $$1, Optional<ShaderDefines> $$2, Optional<List<String>> $$3, Optional<List<UniformDescription>> $$4, Optional<BlendFunction> $$5, Optional<DepthTestFunction> $$6, Optional<PolygonMode> $$7, Optional<Boolean> $$8, Optional<Boolean> $$9, Optional<Boolean> $$10, Optional<Boolean> $$11, Optional<LogicOp> $$12, Optional<VertexFormat> $$13, Optional<VertexFormat.Mode> $$14) {
            this.vertexShader = $$0;
            this.fragmentShader = $$1;
            this.shaderDefines = $$2;
            this.samplers = $$3;
            this.uniforms = $$4;
            this.blendFunction = $$5;
            this.depthTestFunction = $$6;
            this.polygonMode = $$7;
            this.cull = $$8;
            this.writeColor = $$9;
            this.writeAlpha = $$10;
            this.writeDepth = $$11;
            this.colorLogic = $$12;
            this.vertexFormat = $$13;
            this.vertexFormatMode = $$14;
        }
    }
}
