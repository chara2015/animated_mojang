package net.labymod.api.laby3d.model;

import net.labymod.api.client.render.model.Model;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/model/ModelInstance.class */
public abstract class ModelInstance<S> {
    private final Model model;

    public ModelInstance(Model model) {
        this.model = model;
    }

    public final Model model() {
        return this.model;
    }

    public void update(S s) {
    }

    public void resetPose() {
        this.model.reset();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/model/ModelInstance$Simple.class */
    public static class Simple extends ModelInstance<Void> {
        public Simple(Model model) {
            super(model);
        }
    }
}
