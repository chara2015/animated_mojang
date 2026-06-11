package net.minecraft.client.gui.components;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.BossEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/components/LerpingBossEvent.class */
public class LerpingBossEvent extends BossEvent {
    private static final long LERP_MILLISECONDS = 100;
    protected float targetPercent;
    protected long setTime;

    public LerpingBossEvent(UUID $$0, Component $$1, float $$2, BossEvent.BossBarColor $$3, BossEvent.BossBarOverlay $$4, boolean $$5, boolean $$6, boolean $$7) {
        super($$0, $$1, $$3, $$4);
        this.targetPercent = $$2;
        this.progress = $$2;
        this.setTime = Util.getMillis();
        setDarkenScreen($$5);
        setPlayBossMusic($$6);
        setCreateWorldFog($$7);
    }

    @Override // net.minecraft.world.BossEvent
    public void setProgress(float $$0) {
        this.progress = getProgress();
        this.targetPercent = $$0;
        this.setTime = Util.getMillis();
    }

    @Override // net.minecraft.world.BossEvent
    public float getProgress() {
        long $$0 = Util.getMillis() - this.setTime;
        float $$1 = Mth.clamp($$0 / 100.0f, 0.0f, 1.0f);
        return Mth.lerp($$1, this.progress, this.targetPercent);
    }
}
