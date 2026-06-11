package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.Objects;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.object.cart.MinecartModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.MinecartRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.entity.vehicle.minecart.MinecartBehavior;
import net.minecraft.world.entity.vehicle.minecart.NewMinecartBehavior;
import net.minecraft.world.entity.vehicle.minecart.OldMinecartBehavior;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionfc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/AbstractMinecartRenderer.class */
public abstract class AbstractMinecartRenderer<T extends AbstractMinecart, S extends MinecartRenderState> extends EntityRenderer<T, S> {
    private static final Identifier MINECART_LOCATION = Identifier.withDefaultNamespace("textures/entity/minecart.png");
    private static final float DISPLAY_BLOCK_SCALE = 0.75f;
    protected final MinecartModel model;

    public AbstractMinecartRenderer(EntityRendererProvider.Context $$0, ModelLayerLocation $$1) {
        super($$0);
        this.shadowRadius = 0.7f;
        this.model = new MinecartModel($$0.bakeLayer($$1));
    }

    @Override // net.minecraft.client.renderer.entity.EntityRenderer
    public void submit(S $$0, PoseStack $$1, SubmitNodeCollector $$2, CameraRenderState $$3) {
        super.submit($$0, $$1, $$2, $$3);
        $$1.pushPose();
        long $$4 = $$0.offsetSeed;
        float $$5 = ((((($$4 >> 16) & 7) + 0.5f) / 8.0f) - 0.5f) * 0.004f;
        float $$6 = ((((($$4 >> 20) & 7) + 0.5f) / 8.0f) - 0.5f) * 0.004f;
        float $$7 = ((((($$4 >> 24) & 7) + 0.5f) / 8.0f) - 0.5f) * 0.004f;
        $$1.translate($$5, $$6, $$7);
        if ($$0.isNewRender) {
            newRender($$0, $$1);
        } else {
            oldRender($$0, $$1);
        }
        float $$8 = $$0.hurtTime;
        if ($$8 > 0.0f) {
            $$1.mulPose((Quaternionfc) Axis.XP.rotationDegrees((((Mth.sin($$8) * $$8) * $$0.damageTime) / 10.0f) * $$0.hurtDir));
        }
        BlockState $$9 = $$0.displayBlockState;
        if ($$9.getRenderShape() != RenderShape.INVISIBLE) {
            $$1.pushPose();
            $$1.scale(0.75f, 0.75f, 0.75f);
            $$1.translate(-0.5f, ($$0.displayOffset - 8) / 16.0f, 0.5f);
            $$1.mulPose((Quaternionfc) Axis.YP.rotationDegrees(90.0f));
            submitMinecartContents($$0, $$9, $$1, $$2, $$0.lightCoords);
            $$1.popPose();
        }
        $$1.scale(-1.0f, -1.0f, 1.0f);
        $$2.submitModel(this.model, $$0, $$1, this.model.renderType(MINECART_LOCATION), $$0.lightCoords, OverlayTexture.NO_OVERLAY, $$0.outlineColor, null);
        $$1.popPose();
    }

    private static <S extends MinecartRenderState> void newRender(S $$0, PoseStack $$1) {
        $$1.mulPose((Quaternionfc) Axis.YP.rotationDegrees($$0.yRot));
        $$1.mulPose((Quaternionfc) Axis.ZP.rotationDegrees(-$$0.xRot));
        $$1.translate(0.0f, 0.375f, 0.0f);
    }

    private static <S extends MinecartRenderState> void oldRender(S $$0, PoseStack $$1) {
        double $$2 = $$0.x;
        double $$3 = $$0.y;
        double $$4 = $$0.z;
        float $$5 = $$0.xRot;
        float $$6 = $$0.yRot;
        if ($$0.posOnRail != null && $$0.frontPos != null && $$0.backPos != null) {
            Vec3 $$7 = $$0.frontPos;
            Vec3 $$8 = $$0.backPos;
            $$1.translate($$0.posOnRail.x - $$2, (($$7.y + $$8.y) / 2.0d) - $$3, $$0.posOnRail.z - $$4);
            Vec3 $$9 = $$8.add(-$$7.x, -$$7.y, -$$7.z);
            if ($$9.length() != Density.SURFACE) {
                Vec3 $$92 = $$9.normalize();
                $$6 = (float) ((Math.atan2($$92.z, $$92.x) * 180.0d) / 3.141592653589793d);
                $$5 = (float) (Math.atan($$92.y) * 73.0d);
            }
        }
        $$1.translate(0.0f, 0.375f, 0.0f);
        $$1.mulPose((Quaternionfc) Axis.YP.rotationDegrees(180.0f - $$6));
        $$1.mulPose((Quaternionfc) Axis.ZP.rotationDegrees(-$$5));
    }

    @Override // net.minecraft.client.renderer.entity.EntityRenderer
    public void extractRenderState(T $$0, S $$1, float $$2) {
        super.extractRenderState($$0, $$1, $$2);
        MinecartBehavior behavior = $$0.getBehavior();
        if (behavior instanceof NewMinecartBehavior) {
            NewMinecartBehavior $$3 = (NewMinecartBehavior) behavior;
            newExtractState($$0, $$3, $$1, $$2);
            $$1.isNewRender = true;
        } else {
            MinecartBehavior behavior2 = $$0.getBehavior();
            if (behavior2 instanceof OldMinecartBehavior) {
                OldMinecartBehavior $$4 = (OldMinecartBehavior) behavior2;
                oldExtractState($$0, $$4, $$1, $$2);
                $$1.isNewRender = false;
            }
        }
        long $$5 = ((long) $$0.getId()) * 493286711;
        $$1.offsetSeed = ($$5 * $$5 * 4392167121L) + ($$5 * 98761);
        $$1.hurtTime = $$0.getHurtTime() - $$2;
        $$1.hurtDir = $$0.getHurtDir();
        $$1.damageTime = Math.max($$0.getDamage() - $$2, 0.0f);
        $$1.displayOffset = $$0.getDisplayOffset();
        $$1.displayBlockState = $$0.getDisplayBlockState();
    }

    private static <T extends AbstractMinecart, S extends MinecartRenderState> void newExtractState(T $$0, NewMinecartBehavior $$1, S $$2, float $$3) {
        if ($$1.cartHasPosRotLerp()) {
            $$2.renderPos = $$1.getCartLerpPosition($$3);
            $$2.xRot = $$1.getCartLerpXRot($$3);
            $$2.yRot = $$1.getCartLerpYRot($$3);
        } else {
            $$2.renderPos = null;
            $$2.xRot = $$0.getXRot();
            $$2.yRot = $$0.getYRot();
        }
    }

    private static <T extends AbstractMinecart, S extends MinecartRenderState> void oldExtractState(T $$0, OldMinecartBehavior $$1, S $$2, float $$3) {
        $$2.xRot = $$0.getXRot($$3);
        $$2.yRot = $$0.getYRot($$3);
        double $$5 = $$2.x;
        double $$6 = $$2.y;
        double $$7 = $$2.z;
        Vec3 $$8 = $$1.getPos($$5, $$6, $$7);
        if ($$8 != null) {
            $$2.posOnRail = $$8;
            Vec3 $$9 = $$1.getPosOffs($$5, $$6, $$7, 0.30000001192092896d);
            Vec3 $$10 = $$1.getPosOffs($$5, $$6, $$7, -0.30000001192092896d);
            $$2.frontPos = (Vec3) Objects.requireNonNullElse($$9, $$8);
            $$2.backPos = (Vec3) Objects.requireNonNullElse($$10, $$8);
            return;
        }
        $$2.posOnRail = null;
        $$2.frontPos = null;
        $$2.backPos = null;
    }

    protected void submitMinecartContents(S $$0, BlockState $$1, PoseStack $$2, SubmitNodeCollector $$3, int $$4) {
        $$3.submitBlock($$2, $$1, $$4, OverlayTexture.NO_OVERLAY, $$0.outlineColor);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.minecraft.client.renderer.entity.EntityRenderer
    public AABB getBoundingBoxForCulling(T $$0) {
        AABB $$1 = super.getBoundingBoxForCulling($$0);
        if (!$$0.getDisplayBlockState().isAir()) {
            return $$1.expandTowards(Density.SURFACE, ($$0.getDisplayOffset() * 0.75f) / 16.0f, Density.SURFACE);
        }
        return $$1;
    }

    @Override // net.minecraft.client.renderer.entity.EntityRenderer
    public Vec3 getRenderOffset(S $$0) {
        Vec3 $$1 = super.getRenderOffset($$0);
        if ($$0.isNewRender && $$0.renderPos != null) {
            return $$1.add($$0.renderPos.x - $$0.x, $$0.renderPos.y - $$0.y, $$0.renderPos.z - $$0.z);
        }
        return $$1;
    }
}
