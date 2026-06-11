package net.labymod.v1_8_9.client.render;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.models.Implements;
import net.labymod.core.client.render.DefaultAbstractRenderPipeline;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/render/VersionedRenderPipeline.class */
@Singleton
@Implements(RenderPipeline.class)
public class VersionedRenderPipeline extends DefaultAbstractRenderPipeline {
    @Inject
    public VersionedRenderPipeline() {
    }
}
