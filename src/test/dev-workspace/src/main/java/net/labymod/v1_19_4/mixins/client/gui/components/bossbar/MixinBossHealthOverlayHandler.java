package net.labymod.v1_19_4.mixins.client.gui.components.bossbar;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.world.BossBar;
import net.labymod.api.client.world.BossBarRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/gui/components/bossbar/MixinBossHealthOverlayHandler.class */
@Mixin(targets = {"net.minecraft.client.gui.components.BossHealthOverlay$1"})
public class MixinBossHealthOverlayHandler {
    @Redirect(method = {"add"}, at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    public Object labyMod$registerBossBar(Map instance, Object k, Object v) {
        instance.put(k, v);
        Laby.references().bossBarRegistry().registerBossBar((BossBar) v);
        return v;
    }

    @Inject(method = {"remove"}, at = {@At("HEAD")})
    public void labyMod$unregisterBossBar(UUID id, CallbackInfo ci) {
        BossBarRegistry bossBarRegistry = Laby.references().bossBarRegistry();
        BossBar bossBar = null;
        Iterator<BossBar> it = bossBarRegistry.getBossBars().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            BossBar bar = it.next();
            if (bar.getIdentifier().equals(id)) {
                bossBar = bar;
                break;
            }
        }
        if (bossBar == null) {
            return;
        }
        bossBarRegistry.unregisterBossBar(bossBar);
    }
}
