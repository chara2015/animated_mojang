package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.MovingBlockRenderState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/OrderedSubmitNodeCollector.class */
public interface OrderedSubmitNodeCollector {
    void submitShadow(PoseStack poseStack, float f, List<EntityRenderState.ShadowPiece> list);

    void submitNameTag(PoseStack poseStack, Vec3 vec3, int i, Component component, boolean z, int i2, double d, CameraRenderState cameraRenderState);

    void submitText(PoseStack poseStack, float f, float f2, FormattedCharSequence formattedCharSequence, boolean z, Font.DisplayMode displayMode, int i, int i2, int i3, int i4);

    void submitFlame(PoseStack poseStack, EntityRenderState entityRenderState, Quaternionf quaternionf);

    void submitLeash(PoseStack poseStack, EntityRenderState.LeashState leashState);

    <S> void submitModel(Model<? super S> model, S s, PoseStack poseStack, RenderType renderType, int i, int i2, int i3, TextureAtlasSprite textureAtlasSprite, int i4, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay);

    void submitModelPart(ModelPart modelPart, PoseStack poseStack, RenderType renderType, int i, int i2, TextureAtlasSprite textureAtlasSprite, boolean z, boolean z2, int i3, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay, int i4);

    void submitBlock(PoseStack poseStack, BlockState blockState, int i, int i2, int i3);

    void submitMovingBlock(PoseStack poseStack, MovingBlockRenderState movingBlockRenderState);

    void submitBlockModel(PoseStack poseStack, RenderType renderType, BlockStateModel blockStateModel, float f, float f2, float f3, int i, int i2, int i3);

    void submitItem(PoseStack poseStack, ItemDisplayContext itemDisplayContext, int i, int i2, int i3, int[] iArr, List<BakedQuad> list, RenderType renderType, ItemStackRenderState.FoilType foilType);

    void submitCustomGeometry(PoseStack poseStack, RenderType renderType, SubmitNodeCollector.CustomGeometryRenderer customGeometryRenderer);

    void submitParticleGroup(SubmitNodeCollector.ParticleGroupRenderer particleGroupRenderer);

    default <S> void submitModel(Model<? super S> $$0, S $$1, PoseStack $$2, RenderType $$3, int $$4, int $$5, int $$6, ModelFeatureRenderer.CrumblingOverlay $$7) {
        submitModel($$0, $$1, $$2, $$3, $$4, $$5, -1, null, $$6, $$7);
    }

    default void submitModelPart(ModelPart $$0, PoseStack $$1, RenderType $$2, int $$3, int $$4, TextureAtlasSprite $$5) {
        submitModelPart($$0, $$1, $$2, $$3, $$4, $$5, false, false, -1, null, 0);
    }

    default void submitModelPart(ModelPart $$0, PoseStack $$1, RenderType $$2, int $$3, int $$4, TextureAtlasSprite $$5, int $$6, ModelFeatureRenderer.CrumblingOverlay $$7) {
        submitModelPart($$0, $$1, $$2, $$3, $$4, $$5, false, false, $$6, $$7, 0);
    }

    default void submitModelPart(ModelPart $$0, PoseStack $$1, RenderType $$2, int $$3, int $$4, TextureAtlasSprite $$5, boolean $$6, boolean $$7) {
        submitModelPart($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, -1, null, 0);
    }
}
