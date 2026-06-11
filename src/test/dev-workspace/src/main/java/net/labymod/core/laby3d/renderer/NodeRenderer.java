package net.labymod.core.laby3d.renderer;

import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.DefaultStackProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.compiler.ModelCompiler;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.model.ModelInstance;
import net.labymod.api.laby3d.pipeline.ItemRenderStates;
import net.labymod.api.laby3d.renderer.GeometrySubmitter;
import net.labymod.api.laby3d.shaders.block.CosmeticsUniformBlockData;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.api.util.RenderUtil;
import net.labymod.core.laby3d.buffer.VertexConsumerProvider;
import net.labymod.core.laby3d.renderer.GeometryNodeSubmitter;
import net.labymod.core.laby3d.renderer.ModelSubmitter;
import net.labymod.core.main.user.shop.item.renderer.ItemModel;
import net.labymod.core.main.user.shop.item.renderer.ItemModelCompiler;
import net.labymod.core.main.user.shop.item.renderer.ItemModelInstance;
import net.labymod.laby3d.api.buffers.ByteBufferBuilder;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/renderer/NodeRenderer.class */
public class NodeRenderer {
    private static final Comparator<ModelSubmitter.TranslucentModelNode<?>> TRANSLUCENT_MODEL_COMPARATOR = Comparator.comparingDouble(value -> {
        return -value.position().lengthSquared();
    }).thenComparingInt((v0) -> {
        return v0.order();
    });
    private final GeometryNodeSubmitter submitter;
    private final Stack stack = Stack.create((StackProvider) new DefaultStackProvider());
    private final Laby3D laby3D = Laby.references().laby3D();
    private final VertexConsumerProvider.BufferProvider bufferProvider = new VertexConsumerProvider.BufferProvider(new ByteBufferBuilder(8388608));
    private final ModelCompiler modelCompiler = new ModelCompiler(false);
    private final ItemModelCompiler itemModelCompiler = new ItemModelCompiler(ItemModelCompiler.CompilerMode.IMMEDIATE);

    public NodeRenderer(@NotNull GeometryNodeSubmitter submitter) {
        this.submitter = submitter;
    }

    public void render(@NotNull CommandBuffer cmd) {
        ModelSubmitter modelSubmitter = this.submitter.modelSubmitter();
        renderOpaqueModels(cmd, modelSubmitter.opaqueModelNodes());
        renderTranslucentModels(cmd, modelSubmitter.translucentModelNodes());
        renderCustomGeometry(cmd, this.submitter.customGeometryNodes());
        this.bufferProvider.endBatch(cmd);
    }

    private void renderCustomGeometry(CommandBuffer cmd, Map<RenderState, List<GeometryNodeSubmitter.CustomGeometryNode>> customGeometryNodes) {
        RenderUtil.setCosmeticsData(new CosmeticsUniformBlockData());
        for (Map.Entry<RenderState, List<GeometryNodeSubmitter.CustomGeometryNode>> entry : customGeometryNodes.entrySet()) {
            for (GeometryNodeSubmitter.CustomGeometryNode node : entry.getValue()) {
                VertexConsumer consumer = this.bufferProvider.getBuffer(cmd, entry.getKey(), node.textureBindingSet());
                this.stack.push();
                this.stack.mul(node.pose());
                GeometrySubmitter.CustomGeometryRenderer renderer = node.renderer();
                renderer.render(this.stack.getProvider().getPose(), consumer);
                this.stack.pop();
            }
        }
    }

    private void renderOpaqueModels(CommandBuffer cmd, Int2ObjectAVLTreeMap<Map<RenderState, List<ModelSubmitter.ModelNode<?>>>> opaqueModels) {
        ObjectIterator it = opaqueModels.values().iterator();
        while (it.hasNext()) {
            Map<RenderState, List<ModelSubmitter.ModelNode<?>>> nodes = (Map) it.next();
            for (Map.Entry<RenderState, List<ModelSubmitter.ModelNode<?>>> entry : nodes.entrySet()) {
                RenderState renderState = entry.getKey();
                for (ModelSubmitter.ModelNode<?> modelNode : entry.getValue()) {
                    renderModel(cmd, modelNode, renderState);
                }
            }
        }
    }

    private void renderTranslucentModels(CommandBuffer cmd, List<ModelSubmitter.TranslucentModelNode<?>> translucentModelNodes) {
        translucentModelNodes.sort(TRANSLUCENT_MODEL_COMPARATOR);
        for (ModelSubmitter.TranslucentModelNode<?> translucentModelNode : translucentModelNodes) {
            renderModel(cmd, translucentModelNode.node(), translucentModelNode.renderState());
        }
    }

    private <S> void renderModel(CommandBuffer cmd, ModelSubmitter.ModelNode<S> node, RenderState renderState) {
        this.stack.push();
        this.stack.mul(node.pose());
        ModelInstance<S> modelInstance = node.modelInstance();
        modelInstance.update(node.state());
        if (modelInstance instanceof ItemModelInstance) {
            ItemModelInstance itemModelInstance = (ItemModelInstance) modelInstance;
            int lightCoords = node.lightCoords();
            ItemModel itemModel = itemModelInstance.getItemModel();
            short u = (short) (lightCoords & 65535);
            short v = (short) ((lightCoords >> 16) & 65535);
            if (itemModelInstance.isRetained()) {
                itemModelInstance.renderRetained(cmd, renderState, node.pose(), node.bindingSet(), lightCoords);
            } else {
                boolean hasOutline = itemModel.hasOutlineParts();
                if (hasOutline) {
                    try {
                        this.itemModelCompiler.setInvertedFilter(false);
                    } finally {
                        if (hasOutline) {
                            this.itemModelCompiler.setInvertedFilter(null);
                        }
                    }
                }
                VertexConsumer consumer = this.bufferProvider.getBuffer(cmd, renderState, node.bindingSet());
                this.itemModelCompiler.compile(itemModel, this.stack, consumer, lightCoords, itemModelInstance.appearanceStore());
                CosmeticsUniformBlockData cosmeticsData = new CosmeticsUniformBlockData();
                cosmeticsData.lightCoords().set(new Vector2i(u, v));
                RenderUtil.setCosmeticsData(cosmeticsData);
                this.bufferProvider.endLastBatch(cmd);
                if (hasOutline) {
                    this.itemModelCompiler.setInvertedFilter(true);
                    VertexConsumer outlineConsumer = this.bufferProvider.getBuffer(cmd, ItemRenderStates.OUTLINE_COSMETICS, node.bindingSet());
                    this.itemModelCompiler.compile(itemModel, this.stack, outlineConsumer, lightCoords, itemModelInstance.appearanceStore());
                    this.bufferProvider.endLastBatch(cmd);
                }
            }
        } else {
            renderImmediateModel(cmd, modelInstance, renderState, node.bindingSet(), node.lightCoords(), node.overlayCoords());
        }
        modelInstance.resetPose();
        this.stack.pop();
    }

    private <S> void renderImmediateModel(CommandBuffer cmd, ModelInstance<S> modelInstance, RenderState renderState, TextureBindingSet textureBindingSet, int lightCoords, int overlayCoords) {
        VertexConsumer consumer = this.bufferProvider.getBuffer(cmd, renderState, textureBindingSet);
        for (ModelPart child : modelInstance.model().getChildren().values()) {
            this.modelCompiler.compile(this.stack, consumer, child, lightCoords, overlayCoords);
        }
    }
}
