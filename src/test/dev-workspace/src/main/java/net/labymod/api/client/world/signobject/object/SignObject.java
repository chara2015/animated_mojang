package net.labymod.api.client.world.signobject.object;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.signobject.SignObjectPosition;
import net.labymod.api.client.world.signobject.template.SignObjectMeta;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/signobject/object/SignObject.class */
public interface SignObject<M extends SignObjectMeta<?>> {
    M meta();

    SignObjectPosition position();

    long creationTimestamp();

    long lastRenderTimestamp();

    boolean isEnabled();

    void addListener(SignObjectListener<M> signObjectListener);

    void render(Stack stack, double d, double d2, double d3, float f);

    void dispose();

    boolean hasRendering();

    static <M extends SignObjectMeta<?>> SignObject<M> createDummy(M meta, SignObjectPosition position) {
        return new DummySignObject(meta, position);
    }

    static <M extends SignObjectMeta<?>> SignObject<M> create3D(M meta, SignObjectPosition position, SignObject3DRenderer<M> renderer) {
        SignObject3D<M> object = new SignObject3D<>(meta, position);
        object.set3DRenderer(renderer);
        return object;
    }
}
