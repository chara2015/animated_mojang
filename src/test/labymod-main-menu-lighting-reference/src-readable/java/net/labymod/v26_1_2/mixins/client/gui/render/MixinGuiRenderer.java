package net.labymod.v26_1_2.mixins.client.gui.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexFormat;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.state.CanvasSnapshot;
import net.labymod.api.laby3d.shaders.block.vanilla.VanillaFogUniformBlock;
import net.labymod.v26_1_2.client.renderer.state.gui.LabyGuiRenderState;
import net.labymod.v26_1_2.client.renderer.state.gui.SentinelRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.render.pip.GuiEntityRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import net.minecraft.client.renderer.state.gui.pip.GuiEntityRenderState;
import net.minecraft.client.renderer.state.gui.pip.PictureInPictureRenderState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/gui/render/MixinGuiRenderer.class */
@Mixin({GuiRenderer.class})
public class MixinGuiRenderer {

    @Shadow
    @Final
    private List<GuiRenderer.MeshToDraw> meshesToDraw;

    @Shadow
    @Final
    private GuiRenderState renderState;

    @Shadow
    @Final
    private MultiBufferSource.BufferSource bufferSource;

    @Unique
    private int labyMod$entityIndex;

    @Unique
    @Nullable
    private GuiElementRenderState labyMod$previousRenderState;

    @Unique
    private int labyMod$currentIndex;

    @Unique
    private boolean labyMod$firstBlit;

    @Unique
    private final List<GuiEntityRenderer> labyMod$entityRenderers = new ArrayList();

    @Unique
    private final Int2ObjectMap<SentinelRenderState> labyMod$sentinelStates = new Int2ObjectArrayMap();

    @Inject(method = {"preparePictureInPicture"}, at = {@At("HEAD")})
    private void labyMod$resetEntityPool(CallbackInfo ci) {
        this.labyMod$entityIndex = 0;
    }

    @Inject(method = {"preparePictureInPictureState"}, at = {@At("HEAD")}, cancellable = true)
    private <T extends PictureInPictureRenderState> void labyMod$poolEntityRenderer(T state, int guiScale, CallbackInfo ci) {
        if (!(state instanceof GuiEntityRenderState)) {
            return;
        }
        GuiEntityRenderState entityState = (GuiEntityRenderState) state;
        if (this.labyMod$entityIndex >= this.labyMod$entityRenderers.size()) {
            this.labyMod$entityRenderers.add(new GuiEntityRenderer(this.bufferSource, Minecraft.getInstance().getEntityRenderDispatcher()));
        }
        this.labyMod$entityRenderers.get(this.labyMod$entityIndex).prepare(entityState, this.renderState, guiScale);
        this.labyMod$entityIndex++;
        ci.cancel();
    }

    @Inject(method = {"endFrame"}, at = {@At("HEAD")})
    private void labyMod$trimEntityPool(CallbackInfo ci) {
        while (this.labyMod$entityRenderers.size() > this.labyMod$entityIndex) {
            ((GuiEntityRenderer) this.labyMod$entityRenderers.removeLast()).close();
        }
    }

    @Inject(method = {"close"}, at = {@At("HEAD")})
    private void labyMod$closeEntityPool(CallbackInfo ci) {
        for (GuiEntityRenderer renderer : this.labyMod$entityRenderers) {
            renderer.close();
        }
        this.labyMod$entityRenderers.clear();
    }

    @Inject(method = {"prepare"}, at = {@At("HEAD")})
    private void labyMod$flushLastPending(CallbackInfo ci) {
        LabyGuiRenderState.flushPending(this.renderState);
    }

    @Inject(method = {"addElementToMesh"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/GuiRenderer;getBufferBuilder(Lcom/mojang/blaze3d/pipeline/RenderPipeline;)Lcom/mojang/blaze3d/vertex/BufferBuilder;")})
    private void labyMod$storePreviousRenderState(GuiElementRenderState elementState, CallbackInfo ci) {
        this.labyMod$previousRenderState = elementState;
    }

    @WrapOperation(method = {"addElementToMesh"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/gui/render/GuiRenderer;previousPipeline:Lcom/mojang/blaze3d/pipeline/RenderPipeline;", opcode = 180, ordinal = 0)})
    private RenderPipeline labyMod$wrapPipelineComparison(GuiRenderer instance, Operation<RenderPipeline> original) {
        RenderPipeline renderPipeline = (RenderPipeline) original.call(new Object[]{instance});
        if (renderPipeline == SentinelRenderState.SENTINEL_PIPELINE) {
            return null;
        }
        return renderPipeline;
    }

    @Inject(method = {"recordMesh"}, at = {@At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z")})
    private void labyMod$track(BufferBuilder bufferBuilder, RenderPipeline pipeline, TextureSetup textureSetup, ScreenRectangle scissorArea, CallbackInfo ci) {
        GuiElementRenderState guiElementRenderState = this.labyMod$previousRenderState;
        if (guiElementRenderState instanceof SentinelRenderState) {
            SentinelRenderState sentinelState = (SentinelRenderState) guiElementRenderState;
            this.labyMod$sentinelStates.put(this.meshesToDraw.size(), sentinelState);
        }
    }

    @Inject(method = {"endFrame"}, at = {@At("TAIL")})
    private void labyMod$reset(CallbackInfo ci) {
        this.labyMod$previousRenderState = null;
        this.labyMod$sentinelStates.clear();
        this.labyMod$firstBlit = true;
        this.labyMod$currentIndex = -1;
    }

    @WrapOperation(method = {"executeDrawRange"}, at = {@At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;")})
    private Object labyMod$storeCurrentDrawIndex(List instance, int i, Operation<Object> original) {
        this.labyMod$currentIndex = i;
        return original.call(new Object[]{instance, Integer.valueOf(i)});
    }

    @WrapOperation(method = {"executeDrawRange"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/GuiRenderer;executeDraw(Lnet/minecraft/client/gui/render/GuiRenderer$Draw;Lcom/mojang/blaze3d/systems/RenderPass;Lcom/mojang/blaze3d/buffers/GpuBuffer;Lcom/mojang/blaze3d/vertex/VertexFormat$IndexType;)V")})
    private void labyMod$intercept(GuiRenderer instance, GuiRenderer.Draw draw, RenderPass renderPass, GpuBuffer indexBuffer, VertexFormat.IndexType indexType, Operation<Void> original, @Local(name = {"renderPass"}) LocalRef<RenderPass> passRef, @Local(argsOnly = true) Supplier<String> label, @Local(argsOnly = true) RenderTarget mainRenderTarget, @Local(argsOnly = true, ordinal = 0) GpuBufferSlice fogBuffer, @Local(argsOnly = true, ordinal = 1) GpuBufferSlice dynamicTransforms) {
        SentinelRenderState sentinelState = (SentinelRenderState) this.labyMod$sentinelStates.get(this.labyMod$currentIndex);
        if (sentinelState == null) {
            if (renderPass == null) {
                renderPass = labyMod$createRenderPass(label, mainRenderTarget, fogBuffer, dynamicTransforms);
                passRef.set(renderPass);
            }
            original.call(new Object[]{instance, draw, renderPass, indexBuffer, indexType});
            return;
        }
        if (renderPass != null) {
            renderPass.close();
        }
        CanvasSnapshot snapshot = sentinelState.snapshot();
        boolean firstBlit = this.labyMod$firstBlit;
        this.labyMod$firstBlit = false;
        Laby.references().renderEnvironmentContext().screenContext().renderSnapshot(snapshot, firstBlit);
        int nextIndex = this.labyMod$currentIndex + 1;
        if (this.labyMod$sentinelStates.containsKey(nextIndex)) {
            passRef.set((Object) null);
        } else {
            passRef.set(labyMod$createRenderPass(label, mainRenderTarget, fogBuffer, dynamicTransforms));
        }
    }

    @Unique
    private RenderPass labyMod$createRenderPass(Supplier<String> label, RenderTarget mainRenderTarget, GpuBufferSlice fogBuffer, GpuBufferSlice dynamicTransforms) {
        RenderPass pass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(label, mainRenderTarget.getColorTextureView(), OptionalInt.empty(), mainRenderTarget.useDepth ? mainRenderTarget.getDepthTextureView() : null, OptionalDouble.empty());
        RenderSystem.bindDefaultUniforms(pass);
        pass.setUniform(VanillaFogUniformBlock.NAME, fogBuffer);
        pass.setUniform("DynamicTransforms", dynamicTransforms);
        return pass;
    }
}
