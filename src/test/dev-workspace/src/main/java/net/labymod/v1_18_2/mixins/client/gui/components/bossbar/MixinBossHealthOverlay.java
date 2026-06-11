package net.labymod.v1_18_2.mixins.client.gui.components.bossbar;

import net.labymod.api.Laby;
import net.labymod.core.event.client.render.overlay.IngameOverlayElementRenderEventCaller;
import net.labymod.v1_18_2.client.util.MinecraftUtil;
import net.labymod.v1_18_2.client.world.VersionedBossBarRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/gui/components/bossbar/MixinBossHealthOverlay.class */
@Mixin({ead.class})
public class MixinBossHealthOverlay {
    @Inject(method = {"render"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$preDrawBossBar(dtm poseStack, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, screenContext -> {
            boolean cancelled = IngameOverlayElementRenderEventCaller.callBossBarPre(screenContext);
            if (cancelled) {
                ci.cancel();
            }
        });
    }

    @Inject(method = {"render"}, at = {@At("TAIL")})
    private void labyMod$postDrawBossBar(dtm poseStack, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, IngameOverlayElementRenderEventCaller::callBossBarPost);
    }

    @Inject(method = {"reset"}, at = {@At("TAIL")})
    private void labyMod$resetBossBars(CallbackInfo ci) {
        ((VersionedBossBarRegistry) Laby.references().bossBarRegistry()).reset();
    }
}
