package net.labymod.v26_1_1.mixins.client.renderer;

import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.v26_1_1.client.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/renderer/MixinLevelRendererRenderWorldEvent.class */
@Mixin({LevelRenderer.class})
public class MixinLevelRendererRenderWorldEvent {
    @Inject(method = {"lambda$addMainPass$0"}, at = {@At("TAIL")})
    private void labyMod$fireWorldRenderEvent$transparencyChain(CallbackInfo ci) {
        RenderWorldEventCaller.callPost(MinecraftUtil.levelRenderContext().getPoseStack().stack(), Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false));
    }
}
