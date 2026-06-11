package net.labymod.core.client.render.model;

import java.util.Objects;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.ModelUploader;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.client.render.model.box.ModelBoxQuad;
import net.labymod.api.client.render.model.box.ModelBoxVertex;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.models.Implements;
import net.labymod.api.util.Color;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.mesh.GeometryData;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/DefaultModelUploader.class */
@Singleton
@Implements(ModelUploader.class)
public class DefaultModelUploader implements ModelUploader {
    private static final Vector3f POSITION = new Vector3f();
    private static final Vector3f NORMAL = new Vector3f();
    private Model model;
    private ModelUploader.ModelVertexWriter modelVertexWriter = ModelUploader.DEFAULT_WRITER;
    private final Laby3D laby3D = Laby.references().laby3D();

    @Override // net.labymod.api.client.render.model.ModelUploader
    public ModelUploader model(Model model) {
        Objects.requireNonNull(model, "model must not be null");
        this.model = model;
        return this;
    }

    @Override // net.labymod.api.client.render.model.ModelUploader
    public ModelUploader modelVertexWriter(ModelUploader.ModelVertexWriter writer) {
        this.modelVertexWriter = writer;
        return this;
    }

    @Override // net.labymod.api.client.render.model.ModelUploader
    @Nullable
    public GeometryData upload(RenderState renderState) {
        Model model = this.model;
        if (model == null) {
            return null;
        }
        BufferBuilder buffer = this.laby3D.begin(renderState.drawingMode(), renderState.vertexDescription());
        Stack stack = Stack.getDefaultEmptyStack();
        for (ModelPart modelPart : model.getChildren().values()) {
            uploadModelPart(modelPart, stack, buffer, ColorFormat.ARGB32.packTo(ColorFormat.ABGR32, Color.WHITE.get()), 0L);
        }
        invalidate();
        return buffer.build();
    }

    private void invalidate() {
        this.model = null;
        this.modelVertexWriter = DEFAULT_WRITER;
    }

    private void prepareMatrix(Stack stack, ModelPart modelPart) {
        modelPart.getModelPartTransform().transform(stack, modelPart.getAnimationTransformation());
    }

    private void uploadModelPart(ModelPart modelPart, Stack stack, VertexConsumer consumer, int packedColor, long rainbowCycle) {
        if (modelPart.isVisible()) {
            if (modelPart.getBoxes().isEmpty() && modelPart.getChildren().isEmpty()) {
                return;
            }
            stack.push();
            prepareMatrix(stack, modelPart);
            compileBoxes(modelPart, stack, consumer, packedColor, rainbowCycle);
            ModelPart[] modelParts = (ModelPart[]) modelPart.getChildren().values().toArray(new ModelPart[0]);
            for (int i = modelParts.length - 1; i >= 0; i--) {
                uploadModelPart(modelParts[i], stack, consumer, packedColor, rainbowCycle);
            }
            stack.pop();
        }
    }

    private void compileBoxes(ModelPart modelPart, Stack stack, VertexConsumer consumer, int packedColor, long rainbowCycle) {
        StackProvider provider = stack.getProvider();
        Matrix4f pose = provider.getPose();
        Matrix3f normalMatrix = provider.getNormal();
        for (ModelBox box : modelPart.getBoxes()) {
            for (ModelBoxQuad quad : box.getQuads()) {
                if (quad.isVisible()) {
                    normalMatrix.transform(quad.getNormal(), NORMAL);
                    float normalX = NORMAL.x();
                    float normalY = NORMAL.y();
                    float normalZ = NORMAL.z();
                    ModelBoxVertex[] vertices = quad.getVertices();
                    for (ModelBoxVertex vertex : vertices) {
                        Vector3f pos = vertex.getPosition();
                        float x = pos.x() * 0.0625f;
                        float y = pos.y() * 0.0625f;
                        float z = pos.z() * 0.0625f;
                        pose.transformPosition(x, y, z, POSITION);
                        Vector2f uv = vertex.getUV();
                        writeVertex(consumer, POSITION.x(), POSITION.y(), POSITION.z(), uv.x(), uv.y(), packedColor, normalX, normalY, normalZ, modelPart.getId(), false, rainbowCycle);
                    }
                }
            }
        }
    }

    private void writeVertex(VertexConsumer consumer, float x, float y, float z, float u, float v, int packedColor, float normalX, float normalY, float normalZ, float modelId, boolean glowing, long rainbowCycle) {
        this.modelVertexWriter.write(consumer, x, y, z, u, v, packedColor, normalX, normalY, normalZ, modelId, glowing, rainbowCycle);
    }
}
