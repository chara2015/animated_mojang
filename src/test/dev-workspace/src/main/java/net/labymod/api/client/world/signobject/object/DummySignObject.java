package net.labymod.api.client.world.signobject.object;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.signobject.SignObjectPosition;
import net.labymod.api.client.world.signobject.template.SignObjectMeta;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/signobject/object/DummySignObject.class */
public class DummySignObject<M extends SignObjectMeta<?>> extends AbstractSignObject<M> {
    protected DummySignObject(M meta, SignObjectPosition position) {
        super(meta, position);
    }

    @Override // net.labymod.api.client.world.signobject.object.SignObject
    public long lastRenderTimestamp() {
        return 0L;
    }

    @Override // net.labymod.api.client.world.signobject.object.SignObject
    public void render(Stack stack, double x, double y, double z, float tickDelta) {
    }

    @Override // net.labymod.api.client.world.signobject.object.SignObject
    public boolean hasRendering() {
        return false;
    }
}
