package net.labymod.api.client.gfx.pipeline.util;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/util/RenderStateShardAttachment.class */
public interface RenderStateShardAttachment {
    void addAttachment(Runnable runnable);

    static RenderStateShardAttachment self(Object obj) {
        if (!(obj instanceof RenderStateShardAttachment)) {
            return null;
        }
        return (RenderStateShardAttachment) obj;
    }

    static void addAttachment(Object obj, Runnable renderer) {
        RenderStateShardAttachment self = self(obj);
        if (self == null) {
            renderer.run();
        } else {
            self.addAttachment(renderer);
        }
    }
}
