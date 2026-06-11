package net.labymod.api.client.world.signobject.object;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.signobject.template.SignObjectMeta;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/signobject/object/SignObject3DRenderer.class */
public interface SignObject3DRenderer<M extends SignObjectMeta<?>> {
    void render3D(Stack stack, SignObject<M> signObject, double d, double d2, double d3, float f);
}
