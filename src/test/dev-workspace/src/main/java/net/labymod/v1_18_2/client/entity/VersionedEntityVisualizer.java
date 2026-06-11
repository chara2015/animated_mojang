package net.labymod.v1_18_2.client.entity;

import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.EntityVisualizer;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.user.shop.item.ItemUtil;
import org.joml.Quaternionf;
import org.lwjgl.opengl.GL11;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/entity/VersionedEntityVisualizer.class */
@Singleton
@Implements(EntityVisualizer.class)
public class VersionedEntityVisualizer implements EntityVisualizer {
    private float yRotBody;
    private float prevYRotBody;
    private float xRotHead;
    private float prevXRotHead;
    private float yRotHead;
    private float prevYRotHead;

    @Override // net.labymod.api.client.entity.EntityVisualizer
    public void submitEntity(ScreenContext context, Entity entity, float x, float y, float rotationX, float rotationY, float rotationZ, float scale, float headRotationStrength, boolean renderName, boolean clipToBounds) {
        if (clipToBounds) {
            applyScissor(context, x, y, scale);
        }
        float cameraRotation = renderName ? 0.0f : 180.0f;
        Quaternionf cameraOrientation = new Quaternionf();
        if (headRotationStrength < 0.0f) {
            applyLockedRotations(entity, cameraRotation);
            Stack stack = context.stack();
            stack.push();
            stack.translate(x, y, 50.0f);
            stack.scale(scale, scale, -scale);
            stack.rotate(rotationX, 1.0f, 0.0f, 0.0f);
            stack.rotate(cameraRotation, 0.0f, 1.0f, 0.0f);
            stack.rotate(rotationZ + 180.0f, 0.0f, 0.0f, 1.0f);
            renderEntity(context, entity, cameraOrientation);
            stack.pop();
        } else if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            applyHeadRotation(livingEntity, rotationY, headRotationStrength, cameraRotation);
            Stack stack2 = context.stack();
            stack2.push();
            stack2.translate(x, y, 50.0f);
            stack2.scale(scale, scale, -scale);
            stack2.rotate(rotationX, 1.0f, 0.0f, 0.0f);
            stack2.rotate(rotationZ + 180.0f, 0.0f, 0.0f, 1.0f);
            cameraOrientation.rotateY(MathHelper.toRadiansFloat(cameraRotation));
            renderEntity(context, entity, cameraOrientation);
            stack2.pop();
        }
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity2 = (LivingEntity) entity;
            restoreRotations(livingEntity2);
        }
        if (clipToBounds) {
            GL11.glDisable(GlConst.GL_SCISSOR_TEST);
        }
    }

    private void applyScissor(ScreenContext context, float x, float y, float scale) {
        int halfSize = (int) (scale * 1.5f);
        int fullHeight = (int) (scale * 2.0f);
        FloatVector3 transformed = context.stack().transformVector(x, y, 0.0f);
        float screenX = x + transformed.getX();
        float screenY = y + transformed.getY();
        float x0 = screenX - halfSize;
        float y0 = screenY - fullHeight;
        float x1 = screenX + halfSize;
        Window window = Laby.labyAPI().minecraft().minecraftWindow();
        float guiScale = window.getScale();
        GL11.glEnable(GlConst.GL_SCISSOR_TEST);
        GL11.glScissor((int) (x0 * guiScale), (int) (window.getRawHeight() - (screenY * guiScale)), (int) ((x1 - x0) * guiScale), (int) ((screenY - y0) * guiScale));
    }

    private void saveRotations(LivingEntity entity) {
        this.yRotBody = entity.getBodyRotationY();
        this.prevYRotBody = entity.getPreviousBodyRotationY();
        this.xRotHead = entity.getHeadRotationX();
        this.prevXRotHead = entity.getPreviousHeadRotationX();
        this.yRotHead = entity.getHeadRotationY();
        this.prevYRotHead = entity.getPreviousHeadRotationY();
    }

    private void restoreRotations(LivingEntity entity) {
        entity.setBodyRotationY(this.yRotBody);
        entity.setPreviousBodyRotationY(this.prevYRotBody);
        entity.setHeadRotationX(this.xRotHead);
        entity.setPreviousHeadRotationX(this.prevXRotHead);
        entity.setHeadRotationY(this.yRotHead);
        entity.setPreviousHeadRotationY(this.prevYRotHead);
    }

    private void applyLockedRotations(Entity entity, float cameraRotation) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            saveRotations(livingEntity);
            float dollRotation = 180.0f - cameraRotation;
            livingEntity.setBodyRotationY(dollRotation);
            livingEntity.setPreviousBodyRotationY(dollRotation);
            livingEntity.setHeadRotationX(0.0f);
            livingEntity.setPreviousHeadRotationX(0.0f);
            livingEntity.setHeadRotationY(dollRotation);
            livingEntity.setPreviousHeadRotationY(dollRotation);
        }
    }

    private void applyHeadRotation(LivingEntity entity, float rotationY, float headRotationStrength, float cameraRotation) {
        saveRotations(entity);
        float headOffsetY = MathHelper.angleDifference(this.yRotBody, this.yRotHead);
        float prevHeadOffsetY = MathHelper.angleDifference(this.prevYRotBody, this.prevYRotHead);
        float dollRotation = (rotationY + 180.0f) - cameraRotation;
        entity.setBodyRotationY(dollRotation);
        entity.setHeadRotationY(dollRotation - (headOffsetY * headRotationStrength));
        entity.setPreviousBodyRotationY(dollRotation);
        entity.setPreviousHeadRotationY(dollRotation - (prevHeadOffsetY * headRotationStrength));
    }

    private void renderEntity(ScreenContext context, Entity entity, Quaternionf cameraOrientation) {
        bkd bkdVar = (axk) entity;
        if (bkdVar instanceof bkb) {
            bkb part = (bkb) bkdVar;
            bkdVar = part.b;
        }
        evi dispatcher = dyr.D().ab();
        ItemUtil.skipFlyingPets();
        dsi.c();
        dispatcher.a(false);
        g minecraftOrientation = new g(0.0f, 0.0f, 0.0f, 1.0f);
        minecraftOrientation.a(cameraOrientation.x(), cameraOrientation.y(), cameraOrientation.z(), cameraOrientation.w());
        dispatcher.a(minecraftOrientation);
        dispatcher.a(bkdVar, 0.0d, 0.0d, 0.0d, 0.0f, context.getTickDelta(), (dtm) context.stack().getProvider().getPoseStack(), dyr.D().aD().b(), RenderEnvironmentContext.FULL_BRIGHT);
        dsi.b();
        ItemUtil.resetSkipFlyingPets();
    }
}
