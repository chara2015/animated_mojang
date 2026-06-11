package net.labymod.core.laby3d.renderer;

import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.DefaultStackProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.buffer.GameBufferProvider;
import net.labymod.api.laby3d.buffer.GameVertexConsumer;
import net.labymod.api.laby3d.model.ModelInstance;
import net.labymod.api.laby3d.renderer.GeometrySubmitter;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.core.main.user.shop.item.renderer.ItemModelCompiler;
import net.labymod.core.main.user.shop.item.renderer.ItemModelInstance;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.vertex.VertexConsumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/renderer/ImmediateGeometrySubmitter.class */
public class ImmediateGeometrySubmitter implements GeometrySubmitter {
    private static final boolean TRANSFORM_LOCAL_STACK = MinecraftVersions.V1_13.orNewer();
    private final Laby3D laby3D = Laby.references().laby3D();
    private final GameBufferProvider gameBufferProvider = Laby.references().gameBufferProvider();
    private final ItemModelCompiler compiler = new ItemModelCompiler(ItemModelCompiler.CompilerMode.VANILLA);
    private final Stack stack = Stack.create((StackProvider) new DefaultStackProvider());

    @Override // net.labymod.api.laby3d.renderer.GeometrySubmitter
    public GeometrySubmitter.Type getType() {
        return null;
    }

    @Override // net.labymod.api.laby3d.renderer.GeometrySubmitter
    public void setType(GeometrySubmitter.Type type) {
    }

    @Override // net.labymod.api.laby3d.renderer.GeometrySubmitter
    public <S> void submitModel(S state, ModelInstance<S> modelInstance, Stack stack, RenderState renderState, TextureBindingSet textureBindingSet, int lightCoords, int overlayCoords, int order) {
        this.stack.push();
        if (TRANSFORM_LOCAL_STACK) {
            this.stack.mul(stack.getProvider().getPose());
        }
        modelInstance.update(state);
        if (modelInstance instanceof ItemModelInstance) {
            ItemModelInstance itemModelInstance = (ItemModelInstance) modelInstance;
            GameVertexConsumer consumer = this.gameBufferProvider.getBuffer(renderState, textureBindingSet);
            this.compiler.compile(itemModelInstance.getItemModel(), this.stack, consumer, lightCoords, itemModelInstance.appearanceStore());
            this.gameBufferProvider.endBatch();
        }
        this.stack.pop();
        modelInstance.resetPose();
    }

    @Override // net.labymod.api.laby3d.renderer.GeometrySubmitter
    public void submitCustomGeometry(Stack stack, RenderState renderState, TextureBindingSet textureBindingSet, GeometrySubmitter.CustomGeometryRenderer renderer) {
        VertexConsumer consumer = this.gameBufferProvider.getBuffer(renderState, textureBindingSet);
        renderer.render(stack.getProvider().getPose(), consumer);
        this.gameBufferProvider.endBatch();
    }

    @Override // net.labymod.api.laby3d.renderer.GeometrySubmitter
    public void render(CommandBuffer cmd, boolean gui) {
    }
}
