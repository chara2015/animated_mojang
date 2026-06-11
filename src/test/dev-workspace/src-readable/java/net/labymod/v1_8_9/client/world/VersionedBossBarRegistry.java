package net.labymod.v1_8_9.client.world;

import java.util.Objects;
import javax.inject.Singleton;
import net.labymod.api.client.world.BossBar;
import net.labymod.api.client.world.BossBarRegistry;
import net.labymod.api.models.Implements;
import net.labymod.v1_8_9.client.world.phys.hit.VersionedBossBar;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/world/VersionedBossBarRegistry.class */
@Singleton
@Implements(BossBarRegistry.class)
public class VersionedBossBarRegistry extends BossBarRegistry {
    private VersionedBossBar bossBar = new VersionedBossBar(null);

    @Override // net.labymod.api.client.world.BossBarRegistry
    public void registerBossBar(BossBar bossBar) {
        if (Objects.equals(this.bossBar, bossBar) || !(bossBar instanceof VersionedBossBar)) {
            return;
        }
        this.bossBar = (VersionedBossBar) bossBar;
        getBossBars().clear();
        super.registerBossBar(bossBar);
    }

    @Override // net.labymod.api.client.world.BossBarRegistry
    public void unregisterBossBar(BossBar bossBar) {
        if (this.bossBar == null) {
            return;
        }
        super.unregisterBossBar(this.bossBar);
        this.bossBar = null;
    }

    @Override // net.labymod.api.client.world.BossBarRegistry
    public float getOffset() {
        if (this.bossBar == null) {
            return 0.0f;
        }
        return 19.0f;
    }

    public void registerBossBar(uc displayData) {
        if (this.bossBar != null && Objects.equals(this.bossBar.getLastDisplayData(), displayData)) {
            this.bossBar.setIdentifier(displayData);
        } else {
            registerBossBar(new VersionedBossBar(displayData));
        }
    }
}
