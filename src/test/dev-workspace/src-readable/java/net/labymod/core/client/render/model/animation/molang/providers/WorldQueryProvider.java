package net.labymod.core.client.render.model.animation.molang.providers;

import net.laby.lib.bedrock.molang.MolangContext;
import net.labymod.api.Laby;
import net.labymod.api.client.render.model.animation.AnimationRenderState;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.core.client.render.model.animation.molang.MolangQueryProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/animation/molang/providers/WorldQueryProvider.class */
public final class WorldQueryProvider implements MolangQueryProvider {
    private static final long TICKS_PER_DAY = 24000;
    private static final double[] MOON_BRIGHTNESS = {1.0d, 0.75d, 0.5d, 0.25d, 0.0d, 0.25d, 0.5d, 0.75d};

    @Override // net.labymod.core.client.render.model.animation.molang.MolangQueryProvider
    public void populate(MolangContext context, AnimationRenderState state, long progressMs) {
        ClientWorld world = Laby.labyAPI().minecraft().clientWorld();
        if (world == null) {
            return;
        }
        long dayTime = world.getDayTime();
        double timeOfDay = (((dayTime % TICKS_PER_DAY) + TICKS_PER_DAY) % TICKS_PER_DAY) / 24000.0d;
        long day = Math.floorDiv(dayTime, TICKS_PER_DAY);
        int moonPhase = (int) Math.floorMod(day, 8L);
        context.setQuery("query.time_of_day", timeOfDay);
        context.setQuery("query.day", day);
        context.setQuery("query.moon_phase", moonPhase);
        context.setQuery("query.moon_brightness", MOON_BRIGHTNESS[moonPhase]);
    }
}
