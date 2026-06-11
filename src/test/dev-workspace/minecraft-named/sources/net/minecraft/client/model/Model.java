package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/Model.class */
public abstract class Model<S> {
    protected final ModelPart root;
    protected final Function<Identifier, RenderType> renderType;
    private final List<ModelPart> allParts;

    public Model(ModelPart $$0, Function<Identifier, RenderType> $$1) {
        this.root = $$0;
        this.renderType = $$1;
        this.allParts = $$0.getAllParts();
    }

    public final RenderType renderType(Identifier $$0) {
        return this.renderType.apply($$0);
    }

    public final void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, int $$4) {
        root().render($$0, $$1, $$2, $$3, $$4);
    }

    public final void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3) {
        renderToBuffer($$0, $$1, $$2, $$3, -1);
    }

    public final ModelPart root() {
        return this.root;
    }

    public final List<ModelPart> allParts() {
        return this.allParts;
    }

    public void setupAnim(S $$0) {
        resetPose();
    }

    public final void resetPose() {
        for (ModelPart $$0 : this.allParts) {
            $$0.resetPose();
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/Model$Simple.class */
    public static class Simple extends Model<Unit> {
        public Simple(ModelPart $$0, Function<Identifier, RenderType> $$1) {
            super($$0, $$1);
        }

        @Override // net.minecraft.client.model.Model
        public void setupAnim(Unit $$0) {
        }
    }
}
