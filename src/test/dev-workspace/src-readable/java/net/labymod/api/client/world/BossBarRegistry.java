package net.labymod.api.client.world;

import java.util.LinkedHashSet;
import java.util.Set;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/BossBarRegistry.class */
@Referenceable
public abstract class BossBarRegistry {
    private final Set<BossBar> bossBars = new LinkedHashSet();

    public abstract float getOffset();

    protected BossBarRegistry() {
    }

    public Set<BossBar> getBossBars() {
        return this.bossBars;
    }

    public void registerBossBar(BossBar bossBar) {
        this.bossBars.add(bossBar);
    }

    public void unregisterBossBar(BossBar bossBar) {
        this.bossBars.remove(bossBar);
    }
}
