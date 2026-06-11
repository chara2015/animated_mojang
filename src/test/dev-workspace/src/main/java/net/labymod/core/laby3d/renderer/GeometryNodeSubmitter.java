package net.labymod.core.laby3d.renderer;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.laby3d.math.JomlMath;
import net.labymod.api.laby3d.model.ModelInstance;
import net.labymod.api.laby3d.renderer.GeometrySubmitter;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/renderer/GeometryNodeSubmitter.class */
public class GeometryNodeSubmitter implements GeometrySubmitter {
    private final GeometrySubmitter.Type type;
    private final ModelSubmitter modelSubmitter = new ModelSubmitter();
    private final Map<RenderState, List<CustomGeometryNode>> customGeometryNodes = new HashMap();
    private final NodeRenderer renderer = new NodeRenderer(this);
    private final RenderEnvironmentContext environmentContext = Laby.references().renderEnvironmentContext();

    public GeometryNodeSubmitter(GeometrySubmitter.Type type) {
        this.type = type;
    }

    @Override // net.labymod.api.laby3d.renderer.GeometrySubmitter
    public GeometrySubmitter.Type getType() {
        return this.type;
    }

    @Override // net.labymod.api.laby3d.renderer.GeometrySubmitter
    public void setType(GeometrySubmitter.Type type) {
        throw new UnsupportedOperationException("Cannot change the type of this submitter");
    }

    @Override // net.labymod.api.laby3d.renderer.GeometrySubmitter
    public <S> void submitModel(S state, ModelInstance<S> modelInstance, Stack stack, RenderState renderState, TextureBindingSet textureBindingSet, int lightCoords, int overlayCoords, int order) {
        this.modelSubmitter.submitModel(state, modelInstance, stack, renderState, textureBindingSet, lightCoords, overlayCoords, order);
    }

    @Override // net.labymod.api.laby3d.renderer.GeometrySubmitter
    public void submitCustomGeometry(Stack stack, RenderState renderState, TextureBindingSet textureBindingSet, GeometrySubmitter.CustomGeometryRenderer renderer) {
        CustomGeometryNode node = new CustomGeometryNode(JomlMath.extractMatrix(stack.getProvider().getPose()), textureBindingSet, renderer);
        this.customGeometryNodes.computeIfAbsent(renderState, k -> {
            return new ArrayList();
        }).add(node);
    }

    @Override // net.labymod.api.laby3d.renderer.GeometrySubmitter
    public void render(CommandBuffer cmd, boolean gui) {
        FrameProfiler.push("renderSubmissions");
        boolean prevState = this.environmentContext.isScreenContext();
        this.environmentContext.setScreenContext(gui);
        this.renderer.render(cmd);
        this.environmentContext.setScreenContext(prevState);
        FrameProfiler.swap("clearSubmissions");
        clear();
        FrameProfiler.pop();
    }

    public void clear() {
        this.modelSubmitter.clear();
        this.customGeometryNodes.clear();
    }

    public ModelSubmitter modelSubmitter() {
        return this.modelSubmitter;
    }

    public Map<RenderState, List<CustomGeometryNode>> customGeometryNodes() {
        return this.customGeometryNodes;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/renderer/GeometryNodeSubmitter$CustomGeometryNode.class */
    public static final class CustomGeometryNode extends Record {
        private final Matrix4f pose;
        private final TextureBindingSet textureBindingSet;
        private final GeometrySubmitter.CustomGeometryRenderer renderer;

        public CustomGeometryNode(Matrix4f pose, TextureBindingSet textureBindingSet, GeometrySubmitter.CustomGeometryRenderer renderer) {
            this.pose = pose;
            this.textureBindingSet = textureBindingSet;
            this.renderer = renderer;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CustomGeometryNode.class), CustomGeometryNode.class, "pose;textureBindingSet;renderer", "FIELD:Lnet/labymod/core/laby3d/renderer/GeometryNodeSubmitter$CustomGeometryNode;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/labymod/core/laby3d/renderer/GeometryNodeSubmitter$CustomGeometryNode;->textureBindingSet:Lnet/labymod/api/laby3d/textures/TextureBindingSet;", "FIELD:Lnet/labymod/core/laby3d/renderer/GeometryNodeSubmitter$CustomGeometryNode;->renderer:Lnet/labymod/api/laby3d/renderer/GeometrySubmitter$CustomGeometryRenderer;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CustomGeometryNode.class), CustomGeometryNode.class, "pose;textureBindingSet;renderer", "FIELD:Lnet/labymod/core/laby3d/renderer/GeometryNodeSubmitter$CustomGeometryNode;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/labymod/core/laby3d/renderer/GeometryNodeSubmitter$CustomGeometryNode;->textureBindingSet:Lnet/labymod/api/laby3d/textures/TextureBindingSet;", "FIELD:Lnet/labymod/core/laby3d/renderer/GeometryNodeSubmitter$CustomGeometryNode;->renderer:Lnet/labymod/api/laby3d/renderer/GeometrySubmitter$CustomGeometryRenderer;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CustomGeometryNode.class, Object.class), CustomGeometryNode.class, "pose;textureBindingSet;renderer", "FIELD:Lnet/labymod/core/laby3d/renderer/GeometryNodeSubmitter$CustomGeometryNode;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/labymod/core/laby3d/renderer/GeometryNodeSubmitter$CustomGeometryNode;->textureBindingSet:Lnet/labymod/api/laby3d/textures/TextureBindingSet;", "FIELD:Lnet/labymod/core/laby3d/renderer/GeometryNodeSubmitter$CustomGeometryNode;->renderer:Lnet/labymod/api/laby3d/renderer/GeometrySubmitter$CustomGeometryRenderer;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public Matrix4f pose() {
            return this.pose;
        }

        public TextureBindingSet textureBindingSet() {
            return this.textureBindingSet;
        }

        public GeometrySubmitter.CustomGeometryRenderer renderer() {
            return this.renderer;
        }
    }
}
