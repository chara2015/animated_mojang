package net.labymod.core.client.render.model.animation.molang;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.laby.lib.bedrock.molang.MolangContext;
import net.labymod.api.client.render.model.animation.AnimationRenderState;
import net.labymod.core.client.render.model.animation.molang.providers.PlayerQueryProvider;
import net.labymod.core.client.render.model.animation.molang.providers.TimeQueryProvider;
import net.labymod.core.client.render.model.animation.molang.providers.WorldQueryProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/animation/molang/MolangQueryRegistry.class */
public final class MolangQueryRegistry {
    private static final List<MolangQueryProvider> PROVIDERS = new CopyOnWriteArrayList();

    static {
        register(new TimeQueryProvider());
        register(new PlayerQueryProvider());
        register(new WorldQueryProvider());
    }

    private MolangQueryRegistry() {
    }

    public static void register(MolangQueryProvider provider) {
        PROVIDERS.add(provider);
    }

    public static void populate(MolangContext context, AnimationRenderState state, long progressMs) {
        for (MolangQueryProvider provider : PROVIDERS) {
            provider.populate(context, state, progressMs);
        }
    }
}
