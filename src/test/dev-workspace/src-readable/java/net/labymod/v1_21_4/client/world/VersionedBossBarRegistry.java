package net.labymod.v1_21_4.client.world;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.world.BossBar;
import net.labymod.api.client.world.BossBarRegistry;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/world/VersionedBossBarRegistry.class */
@Singleton
@Implements(BossBarRegistry.class)
public class VersionedBossBarRegistry extends BossBarRegistry {
    private final LabyAPI labyAPI;

    @Inject
    public VersionedBossBarRegistry(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    @Override // net.labymod.api.client.world.BossBarRegistry
    public float getOffset() {
        int size = getBossBars().size();
        if (size == 0) {
            return 0.0f;
        }
        float offset = (size * 18.0f) + (size - 1) + 1.0f;
        float maxHeight = this.labyAPI.minecraft().minecraftWindow().getScaledHeight() / 3.0f;
        return Math.min(offset, maxHeight);
    }

    public void reset() {
        for (BossBar bossBar : (BossBar[]) getBossBars().toArray(new BossBar[0])) {
            unregisterBossBar(bossBar);
        }
    }
}
