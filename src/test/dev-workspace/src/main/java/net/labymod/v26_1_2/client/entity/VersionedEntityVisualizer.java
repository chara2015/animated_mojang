package net.labymod.v26_1_2.client.entity;

import javax.inject.Singleton;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.EntityVisualizer;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.v26_1_2.client.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.EnderDragonPart;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/entity/VersionedEntityVisualizer.class */
@Singleton
@Implements(EntityVisualizer.class)
public class VersionedEntityVisualizer implements EntityVisualizer {
    @Override // net.labymod.api.client.entity.EntityVisualizer
    public void submitEntity(ScreenContext context, Entity entity, float x, float y, float rotationX, float rotationY, float rotationZ, float scale, float headRotationStrength, boolean renderName, boolean clipToBounds) {
        LivingEntityRenderState livingEntityRenderStateExtractRenderState = extractRenderState(entity, renderName);
        if (livingEntityRenderStateExtractRenderState instanceof LivingEntityRenderState) {
            LivingEntityRenderState living = livingEntityRenderStateExtractRenderState;
            if (headRotationStrength < 0.0f) {
                living.bodyRot = 180.0f;
                living.yRot = 0.0f;
                living.xRot = 0.0f;
            } else {
                float headOffsetY = living.yRot;
                float dollRotation = rotationY + 180.0f;
                living.bodyRot = dollRotation;
                living.yRot = headOffsetY * headRotationStrength;
            }
        }
        FloatVector3 floatVector3 = context.stack().transformVector(x, y, 0.0f);
        submitToGuiGraphics(livingEntityRenderStateExtractRenderState, x + floatVector3.getX(), y + floatVector3.getY(), rotationX, rotationZ, scale, clipToBounds);
    }

    private EntityRenderState extractRenderState(Entity entity, boolean renderName) {
        EnderDragon enderDragon = (net.minecraft.world.entity.Entity) entity;
        if (enderDragon instanceof EnderDragonPart) {
            EnderDragonPart dragonPart = (EnderDragonPart) enderDragon;
            enderDragon = dragonPart.parentMob;
        }
        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRenderer<? super net.minecraft.world.entity.Entity, ?> renderer = dispatcher.getRenderer(enderDragon);
        EntityRenderState renderState = renderer.createRenderState(enderDragon, 1.0f);
        renderState.shadowPieces.clear();
        renderState.outlineColor = 0;
        if (!renderName) {
            renderState.nameTag = null;
        }
        return renderState;
    }

    private void submitToGuiGraphics(EntityRenderState renderState, float x, float y, float rotationX, float rotationZ, float scale, boolean clipToBounds) {
        int x0;
        int y0;
        int x1;
        int y1;
        Quaternionf rotation = new Quaternionf().rotateZ(3.1415927f).rotateX(MathHelper.toRadiansFloat(rotationX));
        Quaternionf xRotation = new Quaternionf().rotateX(MathHelper.toRadiansFloat(rotationX));
        if (rotationZ != 0.0f) {
            rotation.rotateZ(MathHelper.toRadiansFloat(rotationZ));
        }
        if (renderState instanceof LivingEntityRenderState) {
            LivingEntityRenderState living = (LivingEntityRenderState) renderState;
            living.boundingBoxWidth /= living.scale;
            living.boundingBoxHeight /= living.scale;
            living.scale = 1.0f;
        }
        float bbHeight = renderState.boundingBoxHeight;
        Vector3f translation = new Vector3f(0.0f, bbHeight / 2.0f, 0.0f);
        int halfSize = (int) (scale * 1.5f);
        int fullHeight = (int) (scale * 2.0f);
        if (clipToBounds) {
            x0 = (int) (x - halfSize);
            y0 = (int) (y - fullHeight);
            x1 = (int) (x + halfSize);
            y1 = (int) y;
        } else {
            int padding = (int) (scale * 5.0f);
            int centerY = (int) (y - (fullHeight / 2.0f));
            x0 = (int) (x - padding);
            y0 = centerY - padding;
            x1 = (int) (x + padding);
            y1 = centerY + padding;
        }
        GuiGraphicsExtractor graphics = MinecraftUtil.getCurrentGuiGraphics();
        graphics.entity(renderState, scale, translation, rotation, xRotation, x0, y0, x1, y1);
    }
}
