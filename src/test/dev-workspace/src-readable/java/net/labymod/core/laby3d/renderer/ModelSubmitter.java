package net.labymod.core.laby3d.renderer;

import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.laby3d.math.JomlMath;
import net.labymod.api.laby3d.model.ModelInstance;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.laby3d.api.pipeline.RenderState;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/renderer/ModelSubmitter.class */
public class ModelSubmitter {
    private final Int2ObjectAVLTreeMap<Map<RenderState, List<ModelNode<?>>>> opaqueModelNodes = new Int2ObjectAVLTreeMap<>();
    private final List<TranslucentModelNode<?>> translucentModelNodes = new ArrayList();

    public <S> void submitModel(S state, ModelInstance<S> modelInstance, Stack stack, RenderState renderState, TextureBindingSet textureBindingSet, int lightCoords, int overlayCoords, int order) {
        ModelNode<S> node = new ModelNode<>(state, modelInstance, JomlMath.extractMatrix(stack.getProvider().getPose()), textureBindingSet, lightCoords, overlayCoords);
        if (renderState.blendFunction().isEmpty()) {
            ((List) ((Map) this.opaqueModelNodes.computeIfAbsent(order, k -> {
                return new HashMap();
            })).computeIfAbsent(renderState, k2 -> {
                return new ArrayList();
            })).add(node);
        } else {
            Vector3f position = node.pose().transformPosition(new Vector3f());
            this.translucentModelNodes.add(new TranslucentModelNode<>(node, renderState, order, position));
        }
    }

    public void clear() {
        this.translucentModelNodes.clear();
        this.opaqueModelNodes.clear();
    }

    public Int2ObjectAVLTreeMap<Map<RenderState, List<ModelNode<?>>>> opaqueModelNodes() {
        return this.opaqueModelNodes;
    }

    public List<TranslucentModelNode<?>> translucentModelNodes() {
        return this.translucentModelNodes;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode.class */
    public static final class ModelNode<S> extends Record {
        private final S state;
        private final ModelInstance<S> modelInstance;
        private final Matrix4f pose;
        private final TextureBindingSet bindingSet;
        private final int lightCoords;
        private final int overlayCoords;

        public ModelNode(S state, ModelInstance<S> modelInstance, Matrix4f pose, TextureBindingSet bindingSet, int lightCoords, int overlayCoords) {
            this.state = state;
            this.modelInstance = modelInstance;
            this.pose = pose;
            this.bindingSet = bindingSet;
            this.lightCoords = lightCoords;
            this.overlayCoords = overlayCoords;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ModelNode.class), ModelNode.class, "state;modelInstance;pose;bindingSet;lightCoords;overlayCoords", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;->state:Ljava/lang/Object;", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;->modelInstance:Lnet/labymod/api/laby3d/model/ModelInstance;", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;->bindingSet:Lnet/labymod/api/laby3d/textures/TextureBindingSet;", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;->lightCoords:I", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;->overlayCoords:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ModelNode.class), ModelNode.class, "state;modelInstance;pose;bindingSet;lightCoords;overlayCoords", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;->state:Ljava/lang/Object;", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;->modelInstance:Lnet/labymod/api/laby3d/model/ModelInstance;", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;->bindingSet:Lnet/labymod/api/laby3d/textures/TextureBindingSet;", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;->lightCoords:I", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;->overlayCoords:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ModelNode.class, Object.class), ModelNode.class, "state;modelInstance;pose;bindingSet;lightCoords;overlayCoords", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;->state:Ljava/lang/Object;", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;->modelInstance:Lnet/labymod/api/laby3d/model/ModelInstance;", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;->bindingSet:Lnet/labymod/api/laby3d/textures/TextureBindingSet;", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;->lightCoords:I", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;->overlayCoords:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public S state() {
            return this.state;
        }

        public ModelInstance<S> modelInstance() {
            return this.modelInstance;
        }

        public Matrix4f pose() {
            return this.pose;
        }

        public TextureBindingSet bindingSet() {
            return this.bindingSet;
        }

        public int lightCoords() {
            return this.lightCoords;
        }

        public int overlayCoords() {
            return this.overlayCoords;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/renderer/ModelSubmitter$TranslucentModelNode.class */
    public static final class TranslucentModelNode<S> extends Record {
        private final ModelNode<S> node;
        private final RenderState renderState;
        private final int order;
        private final Vector3f position;

        public TranslucentModelNode(ModelNode<S> node, RenderState renderState, int order, Vector3f position) {
            this.node = node;
            this.renderState = renderState;
            this.order = order;
            this.position = position;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TranslucentModelNode.class), TranslucentModelNode.class, "node;renderState;order;position", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$TranslucentModelNode;->node:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$TranslucentModelNode;->renderState:Lnet/labymod/laby3d/api/pipeline/RenderState;", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$TranslucentModelNode;->order:I", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$TranslucentModelNode;->position:Lorg/joml/Vector3f;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TranslucentModelNode.class), TranslucentModelNode.class, "node;renderState;order;position", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$TranslucentModelNode;->node:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$TranslucentModelNode;->renderState:Lnet/labymod/laby3d/api/pipeline/RenderState;", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$TranslucentModelNode;->order:I", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$TranslucentModelNode;->position:Lorg/joml/Vector3f;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TranslucentModelNode.class, Object.class), TranslucentModelNode.class, "node;renderState;order;position", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$TranslucentModelNode;->node:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$ModelNode;", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$TranslucentModelNode;->renderState:Lnet/labymod/laby3d/api/pipeline/RenderState;", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$TranslucentModelNode;->order:I", "FIELD:Lnet/labymod/core/laby3d/renderer/ModelSubmitter$TranslucentModelNode;->position:Lorg/joml/Vector3f;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public ModelNode<S> node() {
            return this.node;
        }

        public RenderState renderState() {
            return this.renderState;
        }

        public int order() {
            return this.order;
        }

        public Vector3f position() {
            return this.position;
        }
    }
}
