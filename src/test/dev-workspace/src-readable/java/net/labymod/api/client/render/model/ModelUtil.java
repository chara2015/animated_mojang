package net.labymod.api.client.render.model;

import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/ModelUtil.class */
public final class ModelUtil {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/ModelUtil$ScaleConsumer.class */
    @FunctionalInterface
    public interface ScaleConsumer {
        float consume(FloatVector3 floatVector3);
    }

    public static float getScale(ModelPart part, ScaleConsumer consumer) {
        return consumer.consume(part.getModelPartTransform().getScale()) * consumer.consume(part.getAnimationTransformation().getScale());
    }
}
