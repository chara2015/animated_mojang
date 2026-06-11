package net.labymod.v1_8_9.laby3d.buffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.buffer.GameBufferProvider;
import net.labymod.api.laby3d.buffer.GameVertexConsumer;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.api.models.Implements;
import net.labymod.api.util.function.FunctionMemoizeStorage;
import net.labymod.laby3d.api.opengl.GlRenderDevice;
import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.laby3d.api.opengl.util.GlUtil;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.shader.ShaderProgramDescription;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.laby3d.api.vertex.DefaultVertexDescription;
import net.labymod.laby3d.api.vertex.VertexAttributes;
import net.labymod.laby3d.api.vertex.VertexDescription;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/laby3d/buffer/VersionedGameBufferProvider.class */
@Singleton
@Implements(GameBufferProvider.class)
public class VersionedGameBufferProvider implements GameBufferProvider {
    private static final FunctionMemoizeStorage MEMOIZE = Laby.references().functionMemoizeStorage();
    private static final Function<bmu, VertexDescription> VERTEX_DESCRIPTION = MEMOIZE.memoize(vertexFormat -> {
        DefaultVertexDescription.Builder builder = DefaultVertexDescription.builder();
        for (bmv element : vertexFormat.h()) {
            if (element == bms.m) {
                builder.addAttribute(VertexDescriptions.POSITION_NAME, VertexAttributes.POSITION);
            } else if (element == bms.o) {
                builder.addAttribute(VertexDescriptions.UV_NAME, VertexAttributes.UV);
            } else if (element == bms.q) {
                builder.addAttribute(VertexDescriptions.NORMAL_NAME, VertexAttributes.NORMAL);
            } else if (element == bms.n) {
                builder.addAttribute(VertexDescriptions.COLOR_NAME, VertexAttributes.COLOR);
            } else if (element == bms.r) {
                builder.addPadding(1);
            } else {
                throw new IllegalArgumentException("Unsupported vertex format element: " + String.valueOf(element));
            }
        }
        return builder.build();
    });
    private static final BiFunction<RenderState, bmu, RenderState> MAPPED_RENDER_STATE = MEMOIZE.memoize((renderState, vertexFormat) -> {
        return RenderState.builder(new RenderState.Snippet[]{renderState.toSnippet()}).setId(renderState.id().append("_vanilla")).setVertexDescription(VERTEX_DESCRIPTION.apply(vertexFormat)).setShaderProgramDescription(ShaderProgramDescription.builder(new ShaderProgramDescription.Snippet[]{renderState.shaderProgramDescription().toSnippet()}).setId(renderState.shaderProgramDescription().id().append("_vanilla")).setFlag("LEGACY_SHADER").build()).build();
    });
    private final bfe bufferUploader = new bfe();
    private final Map<RenderType, bfd> startedBuffers = new HashMap();
    private final List<bfd> bufferBuilders = new ArrayList();
    private final Set<bfd> usedBufferBuilders = new HashSet();
    private RenderType last;

    @Override // net.labymod.api.laby3d.buffer.GameBufferProvider
    public GameVertexConsumer getBuffer(RenderState renderState, TextureBindingSet bindingSet) {
        bmu format = bms.l;
        RenderState mappedRenderState = MAPPED_RENDER_STATE.apply(renderState, format);
        RenderType renderType = RenderType.of(mappedRenderState, bindingSet);
        bfd bufferBuilder = this.startedBuffers.get(renderType);
        if (bufferBuilder != null) {
            return new VersionedGameVertexConsumer(bufferBuilder);
        }
        if (this.last != null) {
            endBatch(this.last);
        }
        bfd bufferBuilder2 = allocateBufferBuilder();
        bufferBuilder2.a(GlUtil.toGlMode(renderState.drawingMode()), format);
        this.last = renderType;
        this.startedBuffers.put(renderType, bufferBuilder2);
        return new VersionedGameVertexConsumer(bufferBuilder2);
    }

    @Override // net.labymod.api.laby3d.buffer.GameBufferProvider
    public void endBatch() {
        endBatch(this.last);
        this.last = null;
    }

    private bfd allocateBufferBuilder() {
        for (bfd bufferBuilder : this.bufferBuilders) {
            if (this.usedBufferBuilders.add(bufferBuilder)) {
                return bufferBuilder;
            }
        }
        bfd worldRenderer = new bfd(2097152);
        this.bufferBuilders.add(worldRenderer);
        this.usedBufferBuilders.add(worldRenderer);
        return worldRenderer;
    }

    private void endBatch(RenderType renderTyp) {
        bfd bufferBuilder = this.startedBuffers.remove(renderTyp);
        if (bufferBuilder != null) {
            endBatch(renderTyp, bufferBuilder);
            this.usedBufferBuilders.remove(bufferBuilder);
        }
    }

    private void endBatch(RenderType renderType, bfd bufferBuilder) {
        bufferBuilder.e();
        if (bufferBuilder.h() > 0) {
            GlRenderDevice renderDevice = Laby.references().laby3D().renderDevice();
            renderDevice.storeState();
            LegacyDrawCommand legacyDrawCommand = new LegacyDrawCommand();
            try {
                DeviceTextureView[] textures = renderType.textureBindingSet.textures();
                for (int index = 0; index < textures.length; index++) {
                    DeviceTextureView texture = textures[index];
                    if (texture != null) {
                        legacyDrawCommand.setTexture(index, texture);
                    }
                }
                renderType.setup(legacyDrawCommand);
                this.bufferUploader.a(bufferBuilder);
                legacyDrawCommand.close();
                renderDevice.restoreState();
            } catch (Throwable th) {
                try {
                    legacyDrawCommand.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        bufferBuilder.b();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/laby3d/buffer/VersionedGameBufferProvider$LegacyDrawCommand.class */
    public static class LegacyDrawCommand implements AutoCloseable {
        private static final int MAX_TEXTURES = 16;
        private final DeviceTextureView[] textures = new DeviceTextureView[16];

        public void setTexture(int index, DeviceTextureView texture) {
            this.textures[index] = texture;
        }

        public DeviceTextureView[] textures() {
            return this.textures;
        }

        public void execute(RenderTarget target) {
            GlRenderDevice renderDevice = Laby.references().laby3D().renderDevice();
            renderDevice.invalidateRenderState();
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            Arrays.fill(this.textures, (Object) null);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/laby3d/buffer/VersionedGameBufferProvider$RenderType.class */
    public static class RenderType {
        private static final FunctionMemoizeStorage MEMOIZE = Laby.references().functionMemoizeStorage();
        private static final BiFunction<RenderState, TextureBindingSet, RenderType> FACTORY = MEMOIZE.memoize(RenderType::new);
        private final String name;
        private final RenderState renderState;
        private final TextureBindingSet textureBindingSet;

        private RenderType(RenderState renderState, TextureBindingSet textureBindingSet) {
            this.name = renderState.id().toString();
            this.renderState = renderState;
            this.textureBindingSet = textureBindingSet;
        }

        public static RenderType of(RenderState renderState, TextureBindingSet textureBindingSet) {
            return FACTORY.apply(renderState, textureBindingSet);
        }

        public void setup(LegacyDrawCommand command) {
            Laby3D laby3D = Laby.references().laby3D();
            GlRenderDevice renderDevice = laby3D.renderDevice();
            renderDevice.renderStateController().applyRenderState(this.renderState);
            int activeTextureUnit = bfl.o + GlConst.GL_TEXTURE0;
            for (int index = 0; index < command.textures().length; index++) {
                DeviceTextureView texture = command.textures()[index];
                if (texture != null) {
                    GlResource glResource = texture.texture();
                    bfl.g(GlConst.GL_TEXTURE0 + index);
                    bfl.w();
                    bfl.i(glResource.getId());
                    glResource.flushChanges();
                }
            }
            bfl.g(activeTextureUnit);
            command.execute(laby3D.resolveDrawRenderTarget());
        }

        public boolean equals(Object object) {
            if (object == null || getClass() != object.getClass()) {
                return false;
            }
            RenderType that = (RenderType) object;
            return this.name.equals(that.name) && this.renderState.equals(that.renderState) && Objects.equals(this.textureBindingSet, that.textureBindingSet);
        }

        public int hashCode() {
            int result = this.name.hashCode();
            return (31 * ((31 * result) + this.renderState.hashCode())) + Objects.hashCode(this.textureBindingSet);
        }
    }
}
