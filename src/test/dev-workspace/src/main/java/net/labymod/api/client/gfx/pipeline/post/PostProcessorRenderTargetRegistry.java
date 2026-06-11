package net.labymod.api.client.gfx.pipeline.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.post.data.PostProcessorTarget;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/PostProcessorRenderTargetRegistry.class */
public class PostProcessorRenderTargetRegistry {
    private final Map<String, RenderTarget> renderTargets = new HashMap();
    private final List<RenderTarget> fullSizedTargets = new ArrayList();
    private final Laby3D laby3D = Laby.references().laby3D();

    public RenderTarget getRenderTarget(@Nullable String name) {
        if (name == null) {
            return null;
        }
        return getRenderTarget(name, null);
    }

    @Nullable
    public RenderTarget getRenderTarget(@Nullable String name, @Nullable RenderTarget defaultTarget) {
        if (name == null) {
            return null;
        }
        return name.equals("minecraft:main") ? defaultTarget : this.renderTargets.get(name);
    }

    public void registerTarget(PostProcessorTarget target) {
        String name = target.getName();
        boolean isCustomSizedTarget = target instanceof PostProcessorTarget.CustomSizeTarget;
        if (isCustomSizedTarget && this.renderTargets.containsKey(name)) {
            throw new IllegalStateException(name + " is already defined");
        }
        RenderTarget renderTarget = target.create(this.laby3D.renderDevice());
        this.renderTargets.put(name, renderTarget);
        if (!isCustomSizedTarget) {
            this.fullSizedTargets.add(renderTarget);
        }
    }

    public void resizeFullSizedTargets(int width, int height) {
        for (RenderTarget target : this.fullSizedTargets) {
            target.resize(width, height);
        }
    }
}
