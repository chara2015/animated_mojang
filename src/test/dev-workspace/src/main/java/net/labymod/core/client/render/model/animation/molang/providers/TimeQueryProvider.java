package net.labymod.core.client.render.model.animation.molang.providers;

import net.laby.lib.bedrock.molang.MolangContext;
import net.labymod.api.Laby;
import net.labymod.api.client.render.model.animation.AnimationRenderState;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.GameRenderEvent;
import net.labymod.core.client.render.model.animation.molang.MolangQueryProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/animation/molang/providers/TimeQueryProvider.class */
public final class TimeQueryProvider implements MolangQueryProvider {
    private long lastFrameNanos;
    private double cachedDeltaSeconds;

    public TimeQueryProvider() {
        Laby.labyAPI().eventBus().registerListener(this);
    }

    @Subscribe
    public void onGameRender(GameRenderEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }
        long now = System.nanoTime();
        long previous = this.lastFrameNanos;
        this.lastFrameNanos = now;
        this.cachedDeltaSeconds = previous == 0 ? 0.0d : (now - previous) / 1.0E9d;
    }

    @Override // net.labymod.core.client.render.model.animation.molang.MolangQueryProvider
    public void populate(MolangContext context, AnimationRenderState state, long progressMs) {
        double animTimeSec = progressMs / 1000.0d;
        context.setQuery("query.anim_time", animTimeSec);
        context.setQuery("query.life_time", animTimeSec);
        context.setQuery("query.delta_time", this.cachedDeltaSeconds);
    }
}
