package net.labymod.api.laby3d.renderer;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.laby3d.model.ModelInstance;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/renderer/GeometrySubmitter.class */
@Referenceable
public interface GeometrySubmitter extends NodeCollector {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/renderer/GeometrySubmitter$CustomGeometryRenderer.class */
    @FunctionalInterface
    public interface CustomGeometryRenderer {
        void render(Matrix4f matrix4f, VertexConsumer vertexConsumer);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/renderer/GeometrySubmitter$Type.class */
    public enum Type {
        LEVEL,
        HAND,
        ENTITY_IN_UI
    }

    Type getType();

    void setType(Type type);

    <S> void submitModel(S s, ModelInstance<S> modelInstance, Stack stack, RenderState renderState, TextureBindingSet textureBindingSet, int i, int i2, int i3);

    void submitCustomGeometry(Stack stack, RenderState renderState, TextureBindingSet textureBindingSet, CustomGeometryRenderer customGeometryRenderer);

    void render(CommandBuffer commandBuffer, boolean z);

    default <S> void submitModel(S state, ModelInstance<S> modelInstance, Stack stack, RenderState renderState, TextureBindingSet textureBindingSet, int lightCoords, int overlayCoords) {
        submitModel(state, modelInstance, stack, renderState, textureBindingSet, lightCoords, overlayCoords, 0);
    }
}
