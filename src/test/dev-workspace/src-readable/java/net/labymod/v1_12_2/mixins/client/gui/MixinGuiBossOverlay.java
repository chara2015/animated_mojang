package net.labymod.v1_12_2.mixins.client.gui;

import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.world.BossBar;
import net.labymod.core.event.client.render.overlay.IngameOverlayElementRenderEventCaller;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import net.labymod.v1_12_2.client.world.VersionedBossBarRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/MixinGuiBossOverlay.class */
@Mixin({biz.class})
public class MixinGuiBossOverlay {
    @Inject(method = {"renderBossHealth"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$preDrawBossBar(CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, context -> {
            if (IngameOverlayElementRenderEventCaller.callBossBarPre(context)) {
                ci.cancel();
            }
        });
    }

    @Inject(method = {"renderBossHealth"}, at = {@At("TAIL")})
    private void labyMod$postDrawBossBar(CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, IngameOverlayElementRenderEventCaller::callBossBarPost);
    }

    @Inject(method = {"clearBossInfos"}, at = {@At("TAIL")})
    private void labyMod$resetBossBars(CallbackInfo ci) {
        ((VersionedBossBarRegistry) Laby.references().bossBarRegistry()).reset();
    }

    @Redirect(method = {"read"}, at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object labyMod$registerBossBar(Map<Object, Object> instance, Object k, Object v) {
        instance.put(k, v);
        Laby.references().bossBarRegistry().registerBossBar((BossBar) v);
        return v;
    }

    @Redirect(method = {"read"}, at = @At(value = "INVOKE", target = "Ljava/util/Map;remove(Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object labyMod$unregisterBossBar(Map<Object, Object> instance, Object o) {
        Object remove = instance.remove(o);
        Laby.references().bossBarRegistry().unregisterBossBar((BossBar) remove);
        return remove;
    }
}
