package net.labymod.core.client.render.draw;

import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.render.batch.RectangleRenderContext;
import net.labymod.api.client.render.batch.RenderContexts;
import net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/draw/DefaultRoundRectangleRenderer.class */
@Singleton
@Referenceable
public class DefaultRoundRectangleRenderer {
    private final RectangleRenderContext context;

    @Inject
    public DefaultRoundRectangleRenderer(RenderContexts renderContexts) {
        this.context = renderContexts.rectangleRenderContext();
    }

    public void renderSimpleRoundedRectangle(Stack stack, RoundedGeometryBuilder.RoundedData data, Consumer<RectangleRenderContext> renderer) {
        this.context.begin(stack);
        renderer.accept(this.context);
        this.context.uploadToBuffer(RenderStates.GUI_ROUNDED, command -> {
            RoundedGeometryBuilder.RoundedDataApplier.apply(data, command);
        });
    }
}
