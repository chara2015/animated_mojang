package net.labymod.api.client.world.signobject.object;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.signobject.SignObjectPosition;
import net.labymod.api.client.world.signobject.template.SignObjectMeta;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/signobject/object/SignObject3D.class */
public class SignObject3D<M extends SignObjectMeta<?>> extends AbstractSignObject<M> {
    private SignObject3DRenderer<M> renderer;
    private long lastRenderTimestamp;

    protected SignObject3D(M meta, SignObjectPosition position) {
        super(meta, position);
    }

    public void set3DRenderer(SignObject3DRenderer<M> renderer) {
        this.renderer = renderer;
    }

    @Override // net.labymod.api.client.world.signobject.object.SignObject
    public long lastRenderTimestamp() {
        return this.lastRenderTimestamp;
    }

    @Override // net.labymod.api.client.world.signobject.object.SignObject
    public void render(Stack stack, double x, double y, double z, float tickDelta) {
        if (this.renderer == null) {
            return;
        }
        this.lastRenderTimestamp = TimeUtil.getCurrentTimeMillis();
        this.renderer.render3D(stack, this, x, y, z, tickDelta);
    }

    @Override // net.labymod.api.client.world.signobject.object.SignObject
    public boolean hasRendering() {
        return true;
    }
}
