package net.labymod.v1_21_8.client.entity;

import javax.inject.Singleton;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.EntityVisualizer;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.v1_21_8.client.util.MinecraftUtil;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/entity/VersionedEntityVisualizer.class */
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
        if (headRotationStrength < 0.0f) {
            applyLockedRotations(entity);
        } else if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            applyHeadRotation(livingEntity, rotationY, headRotationStrength);
        }
        FloatVector3 transformed = context.stack().transformVector(x, y, 0.0f);
        submitToGuiGraphics(entity, x + transformed.getX(), y + transformed.getY(), rotationX, rotationZ, scale, renderName, clipToBounds);
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity2 = (LivingEntity) entity;
            restoreRotations(livingEntity2);
        }
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

    private void applyLockedRotations(Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            saveRotations(livingEntity);
            livingEntity.setBodyRotationY(180.0f);
            livingEntity.setPreviousBodyRotationY(180.0f);
            livingEntity.setHeadRotationX(0.0f);
            livingEntity.setPreviousHeadRotationX(0.0f);
            livingEntity.setHeadRotationY(180.0f);
            livingEntity.setPreviousHeadRotationY(180.0f);
        }
    }

    private void applyHeadRotation(LivingEntity entity, float rotationY, float headRotationStrength) {
        saveRotations(entity);
        float headOffsetY = MathHelper.angleDifference(this.yRotBody, this.yRotHead);
        float prevHeadOffsetY = MathHelper.angleDifference(this.prevYRotBody, this.prevYRotHead);
        float dollRotation = rotationY + 180.0f;
        entity.setBodyRotationY(dollRotation);
        entity.setHeadRotationY(dollRotation - (headOffsetY * headRotationStrength));
        entity.setPreviousBodyRotationY(dollRotation);
        entity.setPreviousHeadRotationY(dollRotation - (prevHeadOffsetY * headRotationStrength));
    }

    private void submitToGuiGraphics(Entity entity, float x, float y, float rotationX, float rotationZ, float scale, boolean renderName, boolean clipToBounds) {
        int x0;
        int y0;
        int x1;
        int y1;
        cps cpsVar = (bzm) entity;
        if (cpsVar instanceof cpp) {
            cpp dragonPart = (cpp) cpsVar;
            cpsVar = dragonPart.a;
        }
        hec dispatcher = fue.R().ar();
        hed<bzm, hkn> renderer = dispatcher.a(cpsVar);
        hkn renderState = renderer.d();
        renderer.a(cpsVar, renderState, 1.0f);
        renderState.H = null;
        if (!renderName) {
            renderState.E = null;
        }
        Quaternionf rotation = new Quaternionf().rotateZ(3.1415927f).rotateX(MathHelper.toRadiansFloat(rotationX));
        Quaternionf cameraOrientation = new Quaternionf();
        if (rotationZ != 0.0f) {
            rotation.rotateZ(MathHelper.toRadiansFloat(rotationZ));
        }
        float bbHeight = renderState.x;
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
        fxb graphics = MinecraftUtil.getCurrentGuiGraphics();
        graphics.a(renderState, scale, translation, rotation, cameraOrientation, x0, y0, x1, y1);
    }
}
