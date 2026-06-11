package net.labymod.api.client.render.model.animation;

import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/animation/DynamicValue.class */
@FunctionalInterface
public interface DynamicValue {
    double resolve(@Nullable EvaluationContext evaluationContext);
}
