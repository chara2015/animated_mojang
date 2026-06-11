package net.minecraft.client.renderer.blockentity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import java.util.Objects;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.util.Unit;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionfc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/blockentity/SignRenderer.class */
public class SignRenderer extends AbstractSignRenderer {
    public static final float RENDER_SCALE = 0.6666667f;
    private static final Vec3 TEXT_OFFSET = new Vec3(Density.SURFACE, 0.3333333432674408d, 0.046666666865348816d);
    private final Map<WoodType, Models> signModels;

    public SignRenderer(BlockEntityRendererProvider.Context $$0) {
        super($$0);
        this.signModels = (Map) WoodType.values().collect(ImmutableMap.toImmutableMap($$02 -> {
            return $$02;
        }, $$1 -> {
            return new Models(createSignModel($$0.entityModelSet(), $$1, true), createSignModel($$0.entityModelSet(), $$1, false));
        }));
    }

    @Override // net.minecraft.client.renderer.blockentity.AbstractSignRenderer
    protected Model.Simple getSignModel(BlockState $$0, WoodType $$1) {
        Models $$2 = this.signModels.get($$1);
        return $$0.getBlock() instanceof StandingSignBlock ? $$2.standing() : $$2.wall();
    }

    @Override // net.minecraft.client.renderer.blockentity.AbstractSignRenderer
    protected Material getSignMaterial(WoodType $$0) {
        return Sheets.getSignMaterial($$0);
    }

    @Override // net.minecraft.client.renderer.blockentity.AbstractSignRenderer
    protected float getSignModelRenderScale() {
        return 0.6666667f;
    }

    @Override // net.minecraft.client.renderer.blockentity.AbstractSignRenderer
    protected float getSignTextRenderScale() {
        return 0.6666667f;
    }

    private static void translateBase(PoseStack $$0, float $$1) {
        $$0.translate(0.5f, 0.5f, 0.5f);
        $$0.mulPose((Quaternionfc) Axis.YP.rotationDegrees($$1));
    }

    @Override // net.minecraft.client.renderer.blockentity.AbstractSignRenderer
    protected void translateSign(PoseStack $$0, float $$1, BlockState $$2) {
        translateBase($$0, $$1);
        if (!($$2.getBlock() instanceof StandingSignBlock)) {
            $$0.translate(0.0f, -0.3125f, -0.4375f);
        }
    }

    @Override // net.minecraft.client.renderer.blockentity.AbstractSignRenderer
    protected Vec3 getTextOffset() {
        return TEXT_OFFSET;
    }

    public static void submitSpecial(MaterialSet $$0, PoseStack $$1, SubmitNodeCollector $$2, int $$3, int $$4, Model.Simple $$5, Material $$6) {
        $$1.pushPose();
        applyInHandTransforms($$1);
        Unit unit = Unit.INSTANCE;
        Objects.requireNonNull($$5);
        $$2.submitModel($$5, unit, $$1, $$6.renderType($$5::renderType), $$3, $$4, -1, $$0.get($$6), 0, null);
        $$1.popPose();
    }

    public static void applyInHandTransforms(PoseStack $$0) {
        translateBase($$0, 0.0f);
        $$0.scale(0.6666667f, -0.6666667f, -0.6666667f);
    }

    public static Model.Simple createSignModel(EntityModelSet $$0, WoodType $$1, boolean $$2) {
        ModelLayerLocation $$3 = $$2 ? ModelLayers.createStandingSignModelName($$1) : ModelLayers.createWallSignModelName($$1);
        return new Model.Simple($$0.bakeLayer($$3), RenderTypes::entityCutoutNoCull);
    }

    public static LayerDefinition createSignLayer(boolean $$0) {
        MeshDefinition $$1 = new MeshDefinition();
        PartDefinition $$2 = $$1.getRoot();
        $$2.addOrReplaceChild("sign", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0f, -14.0f, -1.0f, 24.0f, 12.0f, 2.0f), PartPose.ZERO);
        if ($$0) {
            $$2.addOrReplaceChild("stick", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0f, -2.0f, -1.0f, 2.0f, 14.0f, 2.0f), PartPose.ZERO);
        }
        return LayerDefinition.create($$1, 64, 32);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/blockentity/SignRenderer$Models.class */
    static final class Models extends Record {
        private final Model.Simple standing;
        private final Model.Simple wall;

        Models(Model.Simple $$0, Model.Simple $$1) {
            this.standing = $$0;
            this.wall = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Models.class), Models.class, "standing;wall", "FIELD:Lnet/minecraft/client/renderer/blockentity/SignRenderer$Models;->standing:Lnet/minecraft/client/model/Model$Simple;", "FIELD:Lnet/minecraft/client/renderer/blockentity/SignRenderer$Models;->wall:Lnet/minecraft/client/model/Model$Simple;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Models.class), Models.class, "standing;wall", "FIELD:Lnet/minecraft/client/renderer/blockentity/SignRenderer$Models;->standing:Lnet/minecraft/client/model/Model$Simple;", "FIELD:Lnet/minecraft/client/renderer/blockentity/SignRenderer$Models;->wall:Lnet/minecraft/client/model/Model$Simple;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Models.class, Object.class), Models.class, "standing;wall", "FIELD:Lnet/minecraft/client/renderer/blockentity/SignRenderer$Models;->standing:Lnet/minecraft/client/model/Model$Simple;", "FIELD:Lnet/minecraft/client/renderer/blockentity/SignRenderer$Models;->wall:Lnet/minecraft/client/model/Model$Simple;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Model.Simple standing() {
            return this.standing;
        }

        public Model.Simple wall() {
            return this.wall;
        }
    }
}
