package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import com.mojang.blaze3d.resource.RenderTargetDescriptor;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mojang.blaze3d.shaders.UniformType;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.PostChainConfig;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.client.renderer.ShaderManager;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/PostChain.class */
public class PostChain implements AutoCloseable {
    public static final Identifier MAIN_TARGET_ID = Identifier.withDefaultNamespace("main");
    private final List<PostPass> passes;
    private final Map<Identifier, PostChainConfig.InternalTarget> internalTargets;
    private final Set<Identifier> externalTargets;
    private final Map<Identifier, RenderTarget> persistentTargets = new HashMap();
    private final CachedOrthoProjectionMatrixBuffer projectionMatrixBuffer;

    private PostChain(List<PostPass> $$0, Map<Identifier, PostChainConfig.InternalTarget> $$1, Set<Identifier> $$2, CachedOrthoProjectionMatrixBuffer $$3) {
        this.passes = $$0;
        this.internalTargets = $$1;
        this.externalTargets = $$2;
        this.projectionMatrixBuffer = $$3;
    }

    public static PostChain load(PostChainConfig $$0, TextureManager $$1, Set<Identifier> $$2, Identifier $$3, CachedOrthoProjectionMatrixBuffer $$4) throws ShaderManager.CompilationException {
        Set<Identifier> $$6 = (Set) $$0.passes().stream().flatMap((v0) -> {
            return v0.referencedTargets();
        }).filter($$12 -> {
            return !$$0.internalTargets().containsKey($$12);
        }).collect(Collectors.toSet());
        Sets.SetView setViewDifference = Sets.difference($$6, $$2);
        if (!setViewDifference.isEmpty()) {
            throw new ShaderManager.CompilationException("Referenced external targets are not available in this context: " + String.valueOf(setViewDifference));
        }
        ImmutableList.Builder<PostPass> $$8 = ImmutableList.builder();
        for (int $$9 = 0; $$9 < $$0.passes().size(); $$9++) {
            PostChainConfig.Pass $$10 = $$0.passes().get($$9);
            $$8.add(createPass($$1, $$10, $$3.withSuffix("/" + $$9)));
        }
        return new PostChain($$8.build(), $$0.internalTargets(), $$6, $$4);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private static PostPass createPass(TextureManager $$0, PostChainConfig.Pass $$1, Identifier $$2) throws MatchException, ShaderManager.CompilationException {
        RenderPipeline.Builder $$3 = RenderPipeline.builder(RenderPipelines.POST_PROCESSING_SNIPPET).withFragmentShader($$1.fragmentShaderId()).withVertexShader($$1.vertexShaderId()).withLocation($$2);
        for (PostChainConfig.Input $$4 : $$1.inputs()) {
            $$3.withSampler($$4.samplerName() + "Sampler");
        }
        $$3.withUniform("SamplerInfo", UniformType.UNIFORM_BUFFER);
        for (String $$5 : $$1.uniforms().keySet()) {
            $$3.withUniform($$5, UniformType.UNIFORM_BUFFER);
        }
        RenderPipeline $$6 = $$3.build();
        List<PostPass.Input> $$7 = new ArrayList<>();
        for (PostChainConfig.Input $$8 : $$1.inputs()) {
            Objects.requireNonNull($$8);
            switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), PostChainConfig.TextureInput.class, PostChainConfig.TargetInput.class).dynamicInvoker().invoke($$8, 0) /* invoke-custom */) {
                case 0:
                    PostChainConfig.TextureInput textureInput = (PostChainConfig.TextureInput) $$8;
                    try {
                        String $$9 = textureInput.samplerName();
                        Identifier $$10 = textureInput.location();
                        int $$11 = textureInput.width();
                        int $$12 = textureInput.height();
                        boolean $$13 = textureInput.bilinear();
                        AbstractTexture $$14 = $$0.getTexture($$10.withPath($$02 -> {
                            return "textures/effect/" + $$02 + ".png";
                        }));
                        $$7.add(new PostPass.TextureInput($$9, $$14, $$11, $$12, $$13));
                    } catch (Throwable th) {
                        throw new MatchException(th.toString(), th);
                    }
                    break;
                case 1:
                    PostChainConfig.TargetInput targetInput = (PostChainConfig.TargetInput) $$8;
                    String $$15 = targetInput.samplerName();
                    Identifier $$16 = targetInput.targetId();
                    boolean $$17 = targetInput.useDepthBuffer();
                    boolean $$18 = targetInput.bilinear();
                    $$7.add(new PostPass.TargetInput($$15, $$16, $$17, $$18));
                    break;
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }
        return new PostPass($$6, $$1.outputTarget(), $$1.uniforms(), $$7);
    }

    public void addToFrame(FrameGraphBuilder $$0, int $$1, int $$2, TargetBundle $$3) {
        GpuBufferSlice $$4 = this.projectionMatrixBuffer.getBuffer($$1, $$2);
        Map<Identifier, ResourceHandle<RenderTarget>> $$5 = new HashMap<>(this.internalTargets.size() + this.externalTargets.size());
        for (Identifier $$6 : this.externalTargets) {
            $$5.put($$6, $$3.getOrThrow($$6));
        }
        for (Map.Entry<Identifier, PostChainConfig.InternalTarget> $$7 : this.internalTargets.entrySet()) {
            Identifier $$8 = $$7.getKey();
            PostChainConfig.InternalTarget $$9 = $$7.getValue();
            RenderTargetDescriptor $$10 = new RenderTargetDescriptor($$9.width().orElse(Integer.valueOf($$1)).intValue(), $$9.height().orElse(Integer.valueOf($$2)).intValue(), true, $$9.clearColor());
            if ($$9.persistent()) {
                RenderTarget $$11 = getOrCreatePersistentTarget($$8, $$10);
                $$5.put($$8, $$0.importExternal($$8.toString(), $$11));
            } else {
                $$5.put($$8, $$0.createInternal($$8.toString(), $$10));
            }
        }
        for (PostPass $$12 : this.passes) {
            $$12.addToFrame($$0, $$5, $$4);
        }
        for (Identifier $$13 : this.externalTargets) {
            $$3.replace($$13, $$5.get($$13));
        }
    }

    @Deprecated
    public void process(RenderTarget $$0, GraphicsResourceAllocator $$1) {
        FrameGraphBuilder $$2 = new FrameGraphBuilder();
        TargetBundle $$3 = TargetBundle.of(MAIN_TARGET_ID, $$2.importExternal("main", $$0));
        addToFrame($$2, $$0.width, $$0.height, $$3);
        $$2.execute($$1);
    }

    private RenderTarget getOrCreatePersistentTarget(Identifier $$0, RenderTargetDescriptor $$1) {
        RenderTarget $$2 = this.persistentTargets.get($$0);
        if ($$2 == null || $$2.width != $$1.width() || $$2.height != $$1.height()) {
            if ($$2 != null) {
                $$2.destroyBuffers();
            }
            $$2 = $$1.allocate();
            $$1.prepare($$2);
            this.persistentTargets.put($$0, $$2);
        }
        return $$2;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.persistentTargets.values().forEach((v0) -> {
            v0.destroyBuffers();
        });
        this.persistentTargets.clear();
        for (PostPass $$0 : this.passes) {
            $$0.close();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/PostChain$TargetBundle.class */
    public interface TargetBundle {
        void replace(Identifier identifier, ResourceHandle<RenderTarget> resourceHandle);

        ResourceHandle<RenderTarget> get(Identifier identifier);

        static TargetBundle of(final Identifier $$0, final ResourceHandle<RenderTarget> $$1) {
            return new TargetBundle() { // from class: net.minecraft.client.renderer.PostChain.TargetBundle.1
                private ResourceHandle<RenderTarget> handle;

                {
                    this.handle = $$1;
                }

                @Override // net.minecraft.client.renderer.PostChain.TargetBundle
                public void replace(Identifier $$02, ResourceHandle<RenderTarget> $$12) {
                    if ($$02.equals($$0)) {
                        this.handle = $$12;
                        return;
                    }
                    throw new IllegalArgumentException("No target with id " + String.valueOf($$02));
                }

                @Override // net.minecraft.client.renderer.PostChain.TargetBundle
                public ResourceHandle<RenderTarget> get(Identifier $$02) {
                    if ($$02.equals($$0)) {
                        return this.handle;
                    }
                    return null;
                }
            };
        }

        default ResourceHandle<RenderTarget> getOrThrow(Identifier $$0) {
            ResourceHandle<RenderTarget> $$1 = get($$0);
            if ($$1 == null) {
                throw new IllegalArgumentException("Missing target with id " + String.valueOf($$0));
            }
            return $$1;
        }
    }
}
