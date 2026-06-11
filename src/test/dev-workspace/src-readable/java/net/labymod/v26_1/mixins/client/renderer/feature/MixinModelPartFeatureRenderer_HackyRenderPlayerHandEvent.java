package net.labymod.v26_1.mixins.client.renderer.feature;

import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.v26_1.client.util.MinecraftUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.ModelPartFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/renderer/feature/MixinModelPartFeatureRenderer_HackyRenderPlayerHandEvent.class */
@Mixin({ModelPartFeatureRenderer.class})
public class MixinModelPartFeatureRenderer_HackyRenderPlayerHandEvent {
    private boolean labyMod$translucentPhase = false;

    @Inject(method = {"renderTranslucent"}, at = {@At("HEAD")})
    private void labyMod$beginTranslucent(CallbackInfo ci) {
        this.labyMod$translucentPhase = true;
    }

    @Inject(method = {"renderTranslucent"}, at = {@At("TAIL")})
    private void labyMod$endTranslucent(CallbackInfo ci) {
        this.labyMod$translucentPhase = false;
    }

    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void labyMod$fakePlayerHandPre(CallbackInfo ci) {
        if (this.labyMod$translucentPhase && MinecraftUtil.prePlayerModelRenderHandEvent != null) {
            Laby.fireEvent(MinecraftUtil.prePlayerModelRenderHandEvent);
            MinecraftUtil.prePlayerModelRenderHandEvent = null;
        }
    }

    @Inject(method = {"render"}, at = {@At("TAIL")})
    private void labyMod$fakePlayerHandPost(Map<RenderType, List<SubmitNodeStorage.ModelPartSubmit>> modelPartSubmitsMap, MultiBufferSource.BufferSource bufferSource, OutlineBufferSource outlineBufferSource, MultiBufferSource.BufferSource crumblingBufferSource, CallbackInfo ci) {
        if (this.labyMod$translucentPhase && MinecraftUtil.postPlayerModelRenderHandEvent != null) {
            bufferSource.endBatch();
            Laby.fireEvent(MinecraftUtil.postPlayerModelRenderHandEvent);
            MinecraftUtil.postPlayerModelRenderHandEvent = null;
        }
    }
}
