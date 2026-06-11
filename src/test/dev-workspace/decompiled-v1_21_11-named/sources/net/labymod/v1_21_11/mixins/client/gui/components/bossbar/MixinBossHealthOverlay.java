package net.labymod.v1_21_11.mixins.client.gui.components.bossbar;

import net.labymod.api.Laby;
import net.labymod.core.event.client.render.overlay.IngameOverlayElementRenderEventCaller;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import net.labymod.v1_21_11.client.world.VersionedBossBarRegistry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/gui/components/bossbar/MixinBossHealthOverlay.class */
@Mixin({BossHealthOverlay.class})
public class MixinBossHealthOverlay {
    @Inject(method = {"render"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$preDrawBossBar(GuiGraphics graphics, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            boolean cancelled = IngameOverlayElementRenderEventCaller.callBossBarPre(screenContext);
            if (cancelled) {
                ci.cancel();
            }
        });
    }

    @Inject(method = {"render"}, at = {@At("TAIL")})
    private void labyMod$postDrawBossBar(GuiGraphics graphics, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callBossBarPost);
    }

    @Inject(method = {"reset"}, at = {@At("TAIL")})
    private void labyMod$resetBossBars(CallbackInfo ci) {
        ((VersionedBossBarRegistry) Laby.references().bossBarRegistry()).reset();
    }
}
