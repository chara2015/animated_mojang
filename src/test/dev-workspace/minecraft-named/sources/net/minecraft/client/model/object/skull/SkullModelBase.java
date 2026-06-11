package net.minecraft.client.model.object.skull;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.rendertype.RenderTypes;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/object/skull/SkullModelBase.class */
public abstract class SkullModelBase extends Model<State> {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/object/skull/SkullModelBase$State.class */
    public static class State {
        public float animationPos;
        public float yRot;
        public float xRot;
    }

    public SkullModelBase(ModelPart $$0) {
        super($$0, RenderTypes::entityTranslucent);
    }
}
