package net.labymod.api.client.render.model.compiler;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.model.EnhancedModelPart;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.client.render.model.box.ModelBoxQuad;
import net.labymod.api.client.render.model.box.ModelBoxVertex;
import net.labymod.api.client.render.model.geometry.Shape;
import net.labymod.api.client.render.model.geometry.ShapeQuad;
import net.labymod.api.client.render.model.geometry.ShapeVertex;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/compiler/ModelCompiler.class */
public final class ModelCompiler {
    private static final int[] LEGACY_INDEX_BUFFER = {0, 1, 2, 2, 3, 0};
    private final boolean legacy;
    private VertexCompiler vertexCompiler = VertexCompiler.DEFAULT;

    public ModelCompiler(boolean legacy) {
        this.legacy = legacy;
    }

    @NotNull
    public static ModelCompiler create() {
        return new ModelCompiler(PlatformEnvironment.isAncientOpenGL());
    }

    @NotNull
    public VertexCompiler getVertexCompiler() {
        return this.vertexCompiler;
    }

    public void setVertexCompiler(@NotNull VertexCompiler vertexCompiler) {
        this.vertexCompiler = vertexCompiler;
    }

    public void compile(@NotNull Stack stack, @NotNull VertexConsumer consumer, @NotNull ModelPart part, int lightCoords, int overlayCoords) {
        compile(stack, consumer, part, 1.0f, 1.0f, 1.0f, 1.0f, lightCoords, overlayCoords);
    }

    public void compile(@NotNull Stack stack, @NotNull VertexConsumer consumer, @NotNull ModelPart part, float red, float green, float blue, float alpha, int lightCoords, int overlayCoords) {
        if (part.isInvisibleOrEmpty()) {
            return;
        }
        stack.push();
        part.getModelPartTransform().transform(stack, part.getAnimationTransformation());
        StackProvider provider = stack.getProvider();
        compileBoxes(part, provider, consumer, lightCoords, overlayCoords, red, green, blue, alpha);
        compileShapes(part, provider, consumer, lightCoords, overlayCoords, red, green, blue, alpha);
        for (ModelPart child : part.getChildren().values()) {
            compile(stack, consumer, child, red, green, blue, alpha, lightCoords, overlayCoords);
        }
        stack.pop();
    }

    private void compileShapes(ModelPart part, StackProvider provider, VertexConsumer consumer, int lightCoords, int overlayCoords, float red, float green, float blue, float alpha) {
        if (!(part instanceof EnhancedModelPart)) {
            return;
        }
        EnhancedModelPart enhancedPart = (EnhancedModelPart) part;
        for (Shape shape : enhancedPart.getShapes()) {
            compileShape(provider, consumer, shape, lightCoords, overlayCoords, red, green, blue, alpha);
        }
    }

    private void compileShape(StackProvider provider, VertexConsumer consumer, Shape shape, int lightCoords, int overlayCoords, float red, float green, float blue, float alpha) {
        Matrix4f pose = provider.getPose();
        Matrix3f normalMatrix = provider.getNormal();
        Vector3f dest = new Vector3f();
        for (ShapeQuad quad : shape.getQuads()) {
            if (quad != null && quad.isVisible()) {
                normalMatrix.transform(quad.getNormal(), dest);
                float normX = dest.x();
                float normY = dest.y();
                float normZ = dest.z();
                for (ShapeVertex vertex : quad.getVertices()) {
                    Vector3f pos = vertex.getPosition();
                    Vector2f uv = vertex.getUV();
                    emitVertex(dest, pose, consumer, pos, uv.x(), uv.y(), lightCoords, overlayCoords, red, green, blue, alpha, normX, normY, normZ);
                }
            }
        }
    }

    private void compileBoxes(ModelPart part, StackProvider provider, VertexConsumer consumer, int lightCoords, int overlayCoords, float red, float green, float blue, float alpha) {
        for (ModelBox box : part.getBoxes()) {
            compileBox(provider, consumer, box, lightCoords, overlayCoords, red, green, blue, alpha);
        }
    }

    private void compileBox(StackProvider provider, VertexConsumer consumer, ModelBox box, int lightCoords, int overlayCoords, float red, float green, float blue, float alpha) {
        Matrix4f pose = provider.getPose();
        Matrix3f normalMatrix = provider.getNormal();
        Vector3f dest = new Vector3f();
        for (ModelBoxQuad quad : box.getQuads()) {
            if (quad.isVisible()) {
                normalMatrix.transform(quad.getNormal(), dest);
                float normX = dest.x();
                float normY = dest.y();
                float normZ = dest.z();
                ModelBoxVertex[] vertices = quad.getVertices();
                if (this.legacy) {
                    for (int index : LEGACY_INDEX_BUFFER) {
                        ModelBoxVertex vertex = vertices[index];
                        Vector2f uv = vertex.getUV();
                        emitVertex(dest, pose, consumer, vertex.getPosition(), uv.x(), uv.y(), lightCoords, overlayCoords, red, green, blue, alpha, normX, normY, normZ);
                    }
                } else {
                    for (ModelBoxVertex vertex2 : vertices) {
                        Vector2f uv2 = vertex2.getUV();
                        emitVertex(dest, pose, consumer, vertex2.getPosition(), uv2.x(), uv2.y(), lightCoords, overlayCoords, red, green, blue, alpha, normX, normY, normZ);
                    }
                }
            }
        }
    }

    private void emitVertex(Vector3f dest, Matrix4f pose, VertexConsumer consumer, Vector3f position, float u, float v, int lightCoords, int overlayCoords, float red, float green, float blue, float alpha, float normX, float normY, float normZ) {
        float x = position.x() * 0.0625f;
        float y = position.y() * 0.0625f;
        float z = position.z() * 0.0625f;
        pose.transformPosition(x, y, z, dest);
        this.vertexCompiler.compile(consumer, dest.x(), dest.y(), dest.z(), red, green, blue, alpha, u, v, overlayCoords, lightCoords, normX, normY, normZ);
    }
}
