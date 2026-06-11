package net.labymod.core.client.render.model.animation.molang;

import net.laby.lib.bedrock.molang.MolangContext;
import net.laby.lib.bedrock.molang.MolangEngine;
import net.labymod.api.client.render.model.animation.AnimationRenderState;
import net.labymod.api.client.render.model.animation.EvaluationContext;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/animation/molang/MolangEvaluationContext.class */
public final class MolangEvaluationContext implements EvaluationContext {
    private static final MolangEngine SHARED_ENGINE = new MolangEngine();
    private final MolangContext molangContext = new MolangContext();

    public MolangContext molangContext() {
        return this.molangContext;
    }

    public MolangEngine engine() {
        return SHARED_ENGINE;
    }

    public static MolangEngine sharedEngine() {
        return SHARED_ENGINE;
    }

    public void populate(AnimationRenderState state, long progressMs) {
        MolangQueryRegistry.populate(this.molangContext, state, progressMs);
    }
}
