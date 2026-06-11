package net.labymod.v1_16_5.mixins.client.gui.components.bossbar;

import java.util.Iterator;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.world.BossBar;
import net.labymod.api.client.world.BossBarRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/gui/components/bossbar/MixinBossHealthOverlayHandler.class */
@Mixin({dli.class})
public class MixinBossHealthOverlayHandler {
    @Redirect(method = {"update"}, at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    public Object labyMod$update(Map instance, Object k, Object v) {
        instance.put(k, v);
        Laby.references().bossBarRegistry().registerBossBar((BossBar) v);
        return v;
    }

    @Redirect(method = {"update"}, at = @At(value = "INVOKE", target = "Ljava/util/Map;remove(Ljava/lang/Object;)Ljava/lang/Object;"))
    public Object labyMod$remove(Map instance, Object k) {
        BossBarRegistry bossBarRegistry = Laby.references().bossBarRegistry();
        BossBar bossBar = null;
        Iterator<BossBar> it = bossBarRegistry.getBossBars().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            BossBar bar = it.next();
            if (bar.getIdentifier().equals(k)) {
                bossBar = bar;
                break;
            }
        }
        if (bossBar != null) {
            bossBarRegistry.unregisterBossBar(bossBar);
        }
        return instance.remove(k);
    }
}
