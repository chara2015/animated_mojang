package net.labymod.v26_2_snapshot_8.mixins.client.gui.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.state.CanvasSnapshot;
import net.labymod.v26_2_snapshot_8.client.renderer.state.gui.LabyGuiRenderState;
import net.labymod.v26_2_snapshot_8.client.renderer.state.gui.SentinelRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.gui.render.pip.GuiEntityRenderer;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/gui/render/MixinGuiRenderer.class */
@Mixin({GuiRenderer.class})
public class MixinGuiRenderer {

    @Shadow
    @Final
    private GuiRenderState renderState;

    @Shadow
    @Final
    private List<GuiRenderer.Draw> draws;

    @Unique
    private int labyMod$entityIndex;

    @Unique
    @Nullable
    private GuiElementRenderState labyMod$currentRenderState;

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
            this.labyMod$entityRenderers.add(new GuiEntityRenderer(Minecraft.getInstance().getEntityRenderDispatcher()));
        }
        this.labyMod$entityRenderers.get(this.labyMod$entityIndex).prepare(entityState, this.renderState, Minecraft.getInstance().gameRenderer.featureRenderDispatcher(), guiScale);
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

    @Inject(method = {"addElementToMesh"}, at = {@At("HEAD")})
    private void labyMod$storeCurrentRenderState(GuiElementRenderState elementState, CallbackInfo ci) {
        this.labyMod$currentRenderState = elementState;
    }

    @WrapOperation(method = {"addElementToMesh"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/gui/render/GuiRenderer;previousPipeline:Lcom/mojang/blaze3d/pipeline/RenderPipeline;", opcode = 180, ordinal = 0)})
    private RenderPipeline labyMod$wrapPipelineComparison(GuiRenderer instance, Operation<RenderPipeline> original) {
        RenderPipeline renderPipeline = (RenderPipeline) original.call(new Object[]{instance});
        if (renderPipeline == SentinelRenderState.SENTINEL_PIPELINE) {
            return null;
        }
        return renderPipeline;
    }

    @Inject(method = {"addElementToMesh"}, at = {@At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z")})
    private void labyMod$track(GuiElementRenderState elementState, CallbackInfo ci) {
        GuiElementRenderState guiElementRenderState = this.labyMod$currentRenderState;
        if (guiElementRenderState instanceof SentinelRenderState) {
            SentinelRenderState sentinelState = (SentinelRenderState) guiElementRenderState;
            this.labyMod$sentinelStates.put(this.draws.size(), sentinelState);
        }
    }

    @Inject(method = {"endFrame"}, at = {@At("TAIL")})
    private void labyMod$reset(CallbackInfo ci) {
        this.labyMod$currentRenderState = null;
        this.labyMod$sentinelStates.clear();
        this.labyMod$firstBlit = true;
        this.labyMod$currentIndex = -1;
    }

    @WrapOperation(method = {"executeDrawRange"}, at = {@At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;")})
    private Object labyMod$storeCurrentDrawIndex(List instance, int i, Operation<Object> original) {
        this.labyMod$currentIndex = i;
        return original.call(new Object[]{instance, Integer.valueOf(i)});
    }

    @WrapOperation(method = {"executeDrawRange"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/GuiRenderer;executeDraw(Lnet/minecraft/client/gui/render/GuiRenderer$Draw;Lcom/mojang/blaze3d/systems/RenderPass;)V")})
    private void labyMod$intercept(GuiRenderer instance, GuiRenderer.Draw draw, RenderPass renderPass, Operation<Void> original, @Local(name = {"renderPass"}) LocalRef<RenderPass> passRef, @Local(argsOnly = true) Supplier<String> label, @Local(argsOnly = true) RenderTarget mainRenderTarget, @Local(argsOnly = true, ordinal = 0) GpuBufferSlice dynamicTransforms) {
        SentinelRenderState sentinelState = (SentinelRenderState) this.labyMod$sentinelStates.get(this.labyMod$currentIndex);
        if (sentinelState == null) {
            if (renderPass == null) {
                renderPass = labyMod$createRenderPass(label, mainRenderTarget, dynamicTransforms);
                passRef.set(renderPass);
            }
            original.call(new Object[]{instance, draw, renderPass});
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
            passRef.set(labyMod$createRenderPass(label, mainRenderTarget, dynamicTransforms));
        }
    }

    @Unique
    private RenderPass labyMod$createRenderPass(Supplier<String> label, RenderTarget mainRenderTarget, GpuBufferSlice dynamicTransforms) {
        RenderPass pass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(label, mainRenderTarget.getColorTextureView(), Optional.empty(), mainRenderTarget.useDepth ? mainRenderTarget.getDepthTextureView() : null, OptionalDouble.empty());
        RenderSystem.bindDefaultUniforms(pass);
        pass.setUniform("DynamicTransforms", dynamicTransforms);
        return pass;
    }
}
